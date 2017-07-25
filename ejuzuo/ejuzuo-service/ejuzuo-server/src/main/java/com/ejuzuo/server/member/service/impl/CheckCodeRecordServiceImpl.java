package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.CheckCodeRecord;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.CheckCodeRecordStatus;
import com.ejuzuo.common.option.CheckCodeRecordOption;
import com.ejuzuo.common.service.CheckCodeRecordService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.EmailUtils;
import com.ejuzuo.common.util.MobileUtils;
import com.ejuzuo.common.vo.UserOperationParam;
import com.ejuzuo.server.common.support.email.EmailSender;
import com.ejuzuo.server.member.manager.CheckCodeRecordManager;
import com.ejuzuo.server.member.manager.SmsRecordManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("checkCodeRecordService")
public class CheckCodeRecordServiceImpl implements CheckCodeRecordService {
	
	private final static Logger logger = LoggerFactory.getLogger(CheckCodeRecordServiceImpl.class);

	@Autowired
	private CheckCodeRecordManager checkCodeRecordManager;
	
	@Autowired
	private SmsRecordManager smsRecordManager;
	
	@Autowired
	private EmailSender emailSender;
	
	@Override
	public ModelResult<String> sendSmsCheckCode(String mobile, CheckCodeRecordCheckType checkType,	Member member, UserOperationParam userParam) {
		ModelResult<String> result = new ModelResult<String>();
		//检查数据完整性
		if(!MobileUtils.isMobile(mobile)) {
			return result.withError("exception", "手机号错误");
		}		
		Integer memberId = null;
		if (member != null) {
			memberId = member.getId();
		}
		//限制条件, 同一个手机号或同一用户, 1小时内只能5条。通一个member，1小时只能5条。
		int count = checkCodeRecordManager.countCheckCodeByCheckType(mobile,CheckCodeRecordDestType.mobile, memberId, checkType, DateUtil.nowAddMinutes(-60));
		if (count >= 5)  {
			return result.withError("exception", "1小时内只能发5条");
		}
		//60秒内只能发一条。
		count = checkCodeRecordManager.countCheckCodeByCheckType(mobile, CheckCodeRecordDestType.mobile, memberId, checkType, DateUtil.nowAddMinutes(-1));
		if (count >= 1)  {
			return result.withError("exception", "1分钟内只能发1条");
		}
		
		//组装内容，发送手机短信
		String checkCode = this.getSmsCheckCode();
		String content = this.getSmsByCheckType(checkType, checkCode);
		if (StringUtils.isEmpty(content)) {
			return result.withError("exception", "不支持的短信内容！");
		}
		BaseResult r = smsRecordManager.sendSmsRecord(mobile, memberId, content);
		if (!r.isSuccess()) {
			logger.error(r.toString());
			return result.withError("exception","发送校验码短信失败");
		}
		
		//插入checkcode记录
		CheckCodeRecord record = new CheckCodeRecord();	
		record.setCheckCode(checkCode);
		record.setDestType(CheckCodeRecordDestType.mobile);
		record.setDestNo(mobile);
		record.setCheckType(checkType);
		record.setExpireTime(DateUtil.calendarAddMinutes(30));//30分钟内有效
		if (member != null) {
			record.setMemberId(member.getId()); 
		}
		checkCodeRecordManager.insert(record);
		logger.info("校验码记录保存成功:" + record);		
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelResult<String> checkSmsCheckCode(String checkCode, String mobile,
			CheckCodeRecordCheckType checkType, Member member) {
		ModelResult<String> result = new ModelResult<String>();
		if(!MobileUtils.isMobile(mobile)) {
			return result.withError("exception", "手机号错误");
		}	
		//检查校验码
		List<CheckCodeRecord> checkCodeList = checkCodeRecordManager.queryCheckCodeListByCheckType(mobile, CheckCodeRecordDestType.mobile, checkType);
		if (checkCodeList.isEmpty()) {
			return result.withError("exception", "校验码不存在");
		}
		CheckCodeRecord record = checkCodeList.get(0);
		if (record.getCheckCode().equals(checkCode)){			
			result.withModel(getCheckedToken());	
			record.setStatus(CheckCodeRecordStatus.used);
		} else {			
			result.withError("exception", "验证码不正确");
		}
		record.setVerifyCount(record.getVerifyCount()+1);
		record.setUpdateTime(Calendar.getInstance());
		checkCodeRecordManager.updateVerifyStatus(record);
		
		return result;
	}

	@Override
	public ModelResult<String> sendEmailCheckCode(String email, CheckCodeRecordCheckType checkType,
			Member member, UserOperationParam userParam) {
		//检查数据完整性
		if(!EmailUtils.isValidEmail(email)) {
			return new ModelResult<>().withError(null, "邮件地址错误");
		}		
		Integer memberId = null;
		if (member != null) {
			memberId = member.getId();
		}
		//限制条件, 同一个手机号或同一用户, 1小时内只能5条。通一个member，1小时只能5条。
		int count = checkCodeRecordManager.countCheckCodeByCheckType(email, CheckCodeRecordDestType.email, memberId, checkType, DateUtil.nowAddMinutes(-60));
		if (count >= 5)  {
			logger.info("给用户:{}:邮箱:{}:1小时内只能发送5条", memberId, email);
			return new ModelResult<>().withError("exception", "1小时内只能发5条");
		}
		//60秒内只能发一条。
		count = checkCodeRecordManager.countCheckCodeByCheckType(email, CheckCodeRecordDestType.email , memberId, checkType, DateUtil.nowAddMinutes(-1));
		if (count >= 1)  {
			logger.info("给用户:{}:邮箱:{}:1分钟内只能发1条", memberId, email);
			return new ModelResult<>().withError("exception", "1分钟内只能发1条");
		}
		
		//组装内容，发送手机短信
		String checkCode = this.getEmailCheckCode();	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("checkCode", checkCode);
		boolean r = emailSender.sendEmail(email,checkType, map);
		if (!r) {
			logger.error("发送邮件失败！！！");
			return new ModelResult<>().withError("exception", "发送邮件失败");
		}
		
		//插入checkcode记录
		CheckCodeRecord record = new CheckCodeRecord();	
		record.setCheckCode(checkCode);
		record.setDestType(CheckCodeRecordDestType.email);
		record.setDestNo(email);
		record.setCheckType(checkType);
		record.setExpireTime(DateUtil.calendarAddDays(3));//3天内有效
		if (member != null) {
			record.setMemberId(member.getId()); 
		}
		checkCodeRecordManager.insert(record);
		logger.info("校验码记录保存成功:" + record);		
		
		return new ModelResult();
	
	}

	@Override
	public ModelResult<String> checkEmailCheckCode(String checkCode, String email, CheckCodeRecordCheckType checkType,
			Member member) {
		ModelResult<String> result = new ModelResult<String>();
		if(!EmailUtils.isValidEmail(email)) {
			return result.withError("exception", "邮件地址错误");
		}	
		//检查校验码
		List<CheckCodeRecord> checkCodeList = checkCodeRecordManager.queryCheckCodeListByCheckType(email, CheckCodeRecordDestType.email, checkType);
		if (checkCodeList.isEmpty()) {
			return result.withError("exception", "校验码不存在");
		}
		CheckCodeRecord record = checkCodeList.get(0);
		if (record.getCheckCode().equals(checkCode)){			
			result.withModel(getCheckedToken());	
			record.setStatus(CheckCodeRecordStatus.used);
		} else {			
			result.withError("exception", "验证码不正确");
		}
		record.setVerifyCount(record.getVerifyCount()+1);
		record.setUpdateTime(Calendar.getInstance());
		checkCodeRecordManager.updateVerifyStatus(record);
		
		return result;
	}

	
	/**获取验证码字串
	 * @return
	 */
	private String getSmsCheckCode() {
		return org.apache.commons.lang3.RandomStringUtils.randomNumeric(6);
	}
	
	
	/**获取邮箱的校验码
	 * @return
	 */
	private String getEmailCheckCode() {
		return org.apache.commons.lang3.RandomStringUtils.randomNumeric(6);
	}
	
	
	/**获取验证成功的授权串
	 * @return
	 */
	private String getCheckedToken() {
		return org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(10);
	}
	
	
	/**获取内容获取手机验证码内容
	 * @param checkType
	 * @param checkCode
	 * @return
	 */
//	@SuppressWarnings("unused")
	private String getSmsByCheckType(CheckCodeRecordCheckType checkType, String checkCode) {
//		if (true) {
//			//暂时使用者，以后短信模板通过后，可以用正式的。
//			return "尊敬的用户，您本次操作的验证码："+checkCode;
//		} else {
			if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {			
				return "亲，你申请的注册验证码是：" + checkCode;
			} else if (checkType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
				return "亲，你申请的找回密码验证码是：" + checkCode; 
			} else if (checkType.getIndex() == CheckCodeRecordCheckType.updatePwd.getIndex()) {
					return "亲，你申请的修改密码验证码是：" + checkCode;
			} else if (checkType.getIndex() == CheckCodeRecordCheckType.authMobile.getIndex()) {
				return "亲，你申请的验证手机验证码是：" + checkCode;
			} else {
				return null;
			}
//		}
	}

	@Override
	public PageResult<CheckCodeRecord> queryPage(DataPage<CheckCodeRecord> dataPage, CheckCodeRecordOption qVo) {
		PageResult<CheckCodeRecord> result = new PageResult<CheckCodeRecord>();
		dataPage = checkCodeRecordManager.queryPage(dataPage, qVo);
		result.setPage(dataPage);
		return result;
	}
}
