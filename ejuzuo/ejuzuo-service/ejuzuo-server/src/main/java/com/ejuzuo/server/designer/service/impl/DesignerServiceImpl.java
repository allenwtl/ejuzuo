package com.ejuzuo.server.designer.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.DesignerOption;
import com.ejuzuo.common.service.DesignerService;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MsgRecordService;
import com.ejuzuo.common.service.SmsRecordService;
import com.ejuzuo.common.vo.DesignerVO;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;

/**
 * Created by allen on 2016/4/4.
 */

@Service("designerService")
public class DesignerServiceImpl implements DesignerService {

    @Resource
    private DesignerManager designerManager ;
    @Resource
	private MemberPointLogService memberPointLogService;
    @Resource
	private SmsRecordService smsRecordService;
    @Resource
    private MsgRecordService msgRecordService;
    @Autowired
	private TaskExecutor taskExecutor;
	@Resource
	private BaseIndexManager<Designer> baseIndexManager;
	private static final Logger logger = LoggerFactory.getLogger(DesignerServiceImpl.class);

    @Override
    public ModelResult<Designer> save(Designer designer) {
    	if (designer.getStatus() != null && designer.getStatus() == 1) {
    		taskExecutor.execute(()->{
    			designer.setVerifior("系统自动审核");
    			designer.setVerifyTime(Calendar.getInstance());
        		designer.setUpdateTime(Calendar.getInstance());
        		designer.setStatus(DesignerStatus.shenhetongguo.getIndex());
    			logger.info("设计师审核通过，发送短信通知，赠送50积分。memberId:{}",designer.getMemberId());
    			MemberPointLog memberPointLog = new MemberPointLog();
    			memberPointLog.setMemberId(designer.getMemberId());
    			memberPointLog.setTransType(MemberPointLogTransType.APPLY_DESIGNER.getIndex());
    			memberPointLog.setRelatedType(ObjectType.shejishi.getIndex());
    			memberPointLog.setRelatedId(designer.getId());
    			memberPointLogService.sendPoint(memberPointLog);
    			smsRecordService.sendSmsRecord(designer.getMemberId(), "尊敬的用户，你的设计师认证已经通过，请登录平台查看。");
                //发送个人消息
    			logger.info("memberId [{}] 申请的设计师审批成功，发送个人消息", designer.getMemberId());
                String msgContent = "您好！您已完善资料，恭喜您升级为巨作网的高级会员，并且设计师认证已经审批通过，平台已赠送您50积分，请注意查看后台积分记录！";
                msgRecordService.addPersonMsg(designer.getMemberId(), msgContent); 					
    		});
    	}
        return new ModelResult<>(designerManager.save(designer));
    }

    @Override
    public ModelResult<Boolean> updateById(Designer designer) {
    	if (designer.getStatus() != null && designer.getStatus() == 1) {
    		taskExecutor.execute(()->{
    			designer.setVerifior("系统自动审核");
    			designer.setVerifyTime(Calendar.getInstance());
        		designer.setUpdateTime(Calendar.getInstance());
        		designer.setStatus(DesignerStatus.shenhetongguo.getIndex());
    			logger.info("设计师审核通过，发送短信通知，赠送50积分。memberId:{}",designer.getMemberId());
    			MemberPointLog memberPointLog = new MemberPointLog();
    			memberPointLog.setMemberId(designer.getMemberId());
    			memberPointLog.setTransType(MemberPointLogTransType.APPLY_DESIGNER.getIndex());
    			memberPointLog.setRelatedType(ObjectType.shejishi.getIndex());
    			memberPointLog.setRelatedId(designer.getId());
    			memberPointLogService.sendPoint(memberPointLog);
    			smsRecordService.sendSmsRecord(designer.getMemberId(), "尊敬的用户，你的设计师认证已经通过，请登录平台查看。");
                //发送个人消息
    			logger.info("memberId [{}] 申请的设计师审批成功，发送个人消息", designer.getMemberId());
                String msgContent = "您好！您已完善资料，恭喜您升级为巨作网的高级会员，并且设计师认证已经审批通过，平台已赠送您50积分，请注意查看后台积分记录！";
                msgRecordService.addPersonMsg(designer.getMemberId(), msgContent); 					
    		});
    	}
        ModelResult<Boolean> result = designerManager.updateById(designer);
        if (result.isSuccess() && result.getModel() == true) {
        	this.updateESIndex(designer.getId());
		}
        return result;
    }

    @Override
    public ModelResult<Designer> queryById(Integer id) {
        return new ModelResult<>(designerManager.selectById(id));
    }

    @Override
    public ModelResult<Designer> queryByMemberId(Integer memberId) {
        return designerManager.queryByMemberId(memberId);
    }

	@Override
	public ModelResult<DesignerVO> queryVOByMemberId(Integer memberId) {
		return designerManager.queryVOByMemberId(memberId);
	}
    
    @Override
    public ModelResult<DesignerVO> queryVOByDesignerId(Integer designerId) {
        return designerManager.queryVOByDesignerId(designerId);
    }

    @Override
    public ModelResult<List<Designer>> queryList(DesignerOption option) {
        return designerManager.queryList(option);
    }

    @Override
    public ModelResult<DataPage<Designer>> adminQueryPage(DesignerOption option, DataPage<Designer> dataPage) {
        return designerManager.adminQueryPage(option, dataPage);
    }

    @Override
    public PageResult<Designer> queryPage(DesignerOption option, DataPage<Designer> dataPage) {
        return designerManager.queryPage(option, dataPage);
    }

    @Override
    public ModelResult<DataPage<Designer>> queryHomeList(DesignerOption option, DataPage<Designer> dataPage) {
        return designerManager.queryHomePage(option, dataPage);
    }

    @Override
    public ModelResult<Integer> count(String area, String style, Integer type) {

        return designerManager.count(area, style, type);
    }

    @Override
    public ModelResult<DataPage<DesignerVO>> queryDesigner(String area, String style, Integer type, DataPage<DesignerVO> dataPage) {
        return designerManager.queryDesigner(area, style, type, dataPage);
    }

    private void updateESIndex(Integer id) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				Designer obj = designerManager.selectById(id);
				if (obj.getStatus().equals(DesignerStatus.shenhetongguo.getIndex())) {
					baseIndexManager.createIndex(obj);
				}else {
					baseIndexManager.deleteById(id);
				}
			}
		});
	}

	@Override
	public BaseResult updateHomed(Integer id, Integer homed) {
		return designerManager.updateHomed(id,homed);
	}





}
