package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.webutil.common.CookieUtils;
import com.aicai.webutil.member.MobileUtils;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.MemberVipType;
import com.ejuzuo.common.constants.ThirdType;
import com.ejuzuo.common.domain.*;
import com.ejuzuo.common.domain.type.*;
import com.ejuzuo.common.option.MemberOption;
import com.ejuzuo.common.option.MemberThirdLoginOption;
import com.ejuzuo.common.service.*;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.EmailUtils;
import com.ejuzuo.common.util.MD5;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.annotation.AvoidDuplicateSubmitAnnotation;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.ExecutorUtil;
import com.ejuzuo.web.vo.MemberVo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户类
 * Created by tianlun.wu on 2016/3/26 0026.
 */

@Controller
@RequestMapping("member")
public class MemberController extends BaseController {

    private static final String findPwd = "findPwd";

    @Resource
    private MemberService memberService;

    @Resource
    private CheckCodeRecordService checkCodeRecordService;

    @Resource
    private MemberOperLogService memberOperLogService;

    @Resource
    private MemberPointLogService memberPointLogService;

    @Resource
    private MemberVipService memberVipService;

    @Resource
    private MemberThirdLoginService memberThirdLoginService;
    
    @Resource
    private MsgRecordService msgRecordService;

    @Resource
    private DesignerService designerService;

    @Resource
    private ShoppingCarService shoppingCarService;

    @Resource
    private MemberPointService memberPointService;

    @Login
    @RequestMapping("/center")
    public String centerIndex(Model model) {
        model.addAttribute("menu", "center");
        return "/member/centerIndex";
    }


    //查看个人信息
    @Login
    @RequestMapping(value = "/showInfo")
    public String showInfo(HttpServletRequest request, Model model) {
        Member member = memberService.queryByMemberId(getMemberId(request)).getModel();
        model.addAttribute("member", member);
        model.addAttribute("menu", "memberinfo");
        return "/member/memberinfo/memberInfo";
    }

    //修改手机号码
    @Login
    @RequestMapping("/toUpdateME/{type}")
    public String updateMobileAndEmail(@PathVariable String type, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("menu", "memberinfo");
        if (type != null && type.equals("mobile")) {
            return "/member/memberinfo/updateMobile";
        }

        return "/member/memberinfo/updateEmail";
    }

    /**
     * @param type    类型
     * @param oldStr  旧数据
     * @param newStr  新数据
     * @param code    验证码
     * @param request
     * @return
     */
    @Login
    @RequestMapping("/updateME/{type}")
    @ResponseBody
    public JSONObject updateMobileAndEmail(@PathVariable String type, String oldStr, String newStr, String code, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        MemberVo memberInCache = getMember(request);

        Member tempMember = new Member();
        tempMember.setId(memberInCache.getId());
        if (code == null || StringUtils.isBlank(code)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请输入验证码");
            return jsonObject;
        }
        ModelResult<String> result = null;

        if (type != null && type.equals("mobile")) {
            if (oldStr == null || !MobileUtils.validateMobile(oldStr) || newStr == null || !MobileUtils.validateMobile(newStr)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "请输入正确格式的电话号码");
                return jsonObject;
            }
            if (!oldStr.equals(memberInCache.getMobile())) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "旧手机号码输入不正确");
                return jsonObject;
            }

            tempMember.setMobile(newStr);
            memberInCache.setMobile(newStr);
            result = checkCodeRecordService.checkSmsCheckCode(code, newStr, CheckCodeRecordCheckType.authMobile, null);
        } else if (type != null && type.equals("email")) {
            if (oldStr == null || !EmailUtils.isValidEmail(oldStr) || newStr == null || !MobileUtils.validateMobile(newStr)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "请输入正确格式的邮箱号码");
                return jsonObject;
            }

            if (!oldStr.equals(memberInCache.getEmail())) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "旧邮箱输入不正确");
                return jsonObject;
            }

            tempMember.setEmail(newStr);
            memberInCache.setEmail(newStr);
            result = checkCodeRecordService.checkEmailCheckCode(code, newStr, CheckCodeRecordCheckType.authEmail, null);
        } else {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "非正常请求");
            return jsonObject;
        }

        if (!result.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", result.getErrorMsg());
            return jsonObject;
        }

        ModelResult<Boolean> modelResult = memberService.updateById(tempMember);

        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", result.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "更新成功");

        String uuidStr = CookieUtils.getCookieValue(request, globalParam.getUuidCookie());
        updateMemberInfoInCache(uuidStr, memberInCache);
        return jsonObject;
    }


    //更新用户信息
    @Login
    @RequestMapping("/updateMemberInfo")
    @ResponseBody
    public JSONObject updateMemberInfo(Member member, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Member memberFromDB = memberService.queryByMemberId(getMemberId(request)).getModel();

        if (memberFromDB == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "查不到这个用户");
            return jsonObject;
        }

        MemberVo memberInCache = getMember(request);
        Member tempMember = new Member();
        tempMember.setId(memberFromDB.getId());
        if (member.getProfileImg() != null) {
            tempMember.setProfileImg(member.getProfileImg());
            memberInCache.setProfileImg(member.getProfileImg());
        }

        if (member.getNickName() != null) {
            tempMember.setNickName(member.getNickName());
            memberInCache.setNickName(member.getNickName());
        }

        if (member.getMobile() != null) {
            tempMember.setMobile(member.getMobile());
            memberInCache.setMobile(member.getMobile());
        }

        if (member.getEmail() != null) {
            tempMember.setEmail(member.getEmail());
            memberInCache.setEmail(member.getEmail());
        }

        ModelResult<Boolean> result = memberService.updateById(tempMember);

        if (!result.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", result.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "更新成功");

        String uuidStr = CookieUtils.getCookieValue(request, globalParam.getUuidCookie());
        updateMemberInfoInCache(uuidStr, memberInCache);
        return jsonObject;
    }


    @Login
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(HttpServletRequest request) {


        return "/member/pwd/updatePassword";
    }

    //修改密码
    @Login
    @RequestMapping("/updatePassword")
    @ResponseBody
    public JSONObject updatePassword(String type, String code, String oldPassword, String newPassword, String secPassword, HttpServletRequest request) {
        String key = CheckCodeRecordCheckType.updatePwd.getIndex() + "-" + getMemberId(request);
        Member member = memcachedClient.get(key);

        JSONObject jsonObject = new JSONObject();

        if (member == null) {
            logger.info("用户:[{}]:更新密码 拿不到上一部的缓存，超时");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "验证超时，请重新验证");
            return jsonObject;
        }

        if (!MD5.md5Encode(oldPassword).equals(member.getPassword())) {
            logger.info("用户:[{}]:更新密码操作", member.getAccount());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "老密码输入错误");
            return jsonObject;
        }

        if (!secPassword.equals(newPassword)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "两次输入的新密码不一致");
            return jsonObject;
        }

        ModelResult<String> modelResult = null;
        if (type != null && type.equals("email")) {
            modelResult = checkCodeRecordService.checkEmailCheckCode(code, member.getEmail(), CheckCodeRecordCheckType.updatePwd, member);
        } else if (type != null && type.equals("mobile")) {
            modelResult = checkCodeRecordService.checkSmsCheckCode(code, member.getMobile(), CheckCodeRecordCheckType.updatePwd, member);
        }
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        try {
            Member memberToDB = new Member();
            memberToDB.setId(member.getId());
            memberToDB.setPassword(MD5.md5Encode(newPassword));
            memberService.updateById(memberToDB);
        } catch (Exception e) {
            logger.error("更新密码报错", e);
            jsonObject.put("code", 444);
            jsonObject.put("msg", "更新密码错误");
            return jsonObject;
        }

        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(member.getId());
        memberOperLog.setOperType(MemberOperLogType.UPDATE_PASSWORD.getIndex());
        memberOperLog.setRemark("更新密码");
        memberOperLogService.save(memberOperLog);

        memcachedClient.delete(key);

        jsonObject.put("code", 222);
        jsonObject.put("msg", "更新密码成功");
        //需要重新登录
        deleteMemberInCache(request);
        return jsonObject;
    }


    //设置手机号码
    @Login
    @RequestMapping("/updateMobile/{code}")
    @ResponseBody
    public JSONObject updateMobile(@PathVariable String code, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(code)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请输入验证码");
            return jsonObject;
        }

        String mobileFromCache = memcachedClient.get(code + "-" + CheckCodeRecordCheckType.authMobile.getIndex());

        ModelResult<String> modelResult = checkCodeRecordService.checkSmsCheckCode(code, mobileFromCache, CheckCodeRecordCheckType.authMobile, null);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        Member member = getMember(request);
        Member memberDB = new Member();
        memberDB.setId(member.getId());
        memberDB.setMobile(mobileFromCache);

        ModelResult<Boolean> booleanModelResult = memberService.updateById(memberDB);
        if (!booleanModelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", booleanModelResult.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "更新成功");
        return jsonObject;
    }


    //设置邮箱
    @Login
    @RequestMapping("/updateEmail/{code}")
    @ResponseBody
    public JSONObject updateEmail(@PathVariable String code, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(code)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请输入验证码");
            return jsonObject;
        }

        String emailFromCache = memcachedClient.get(code + "-" + CheckCodeRecordCheckType.authEmail.getIndex());

        ModelResult<String> modelResult = checkCodeRecordService.checkSmsCheckCode(code, emailFromCache, CheckCodeRecordCheckType.authEmail, null);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        Member member = getMember(request);
        Member memberDB = new Member();
        memberDB.setId(member.getId());
        memberDB.setEmail(emailFromCache);

        ModelResult<Boolean> booleanModelResult = memberService.updateById(memberDB);
        if (!booleanModelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", booleanModelResult.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "更新成功");
        return jsonObject;
    }

    //找回密码
    @RequestMapping("/forgetPwd/index")
    public String forgetPwdIndex() {
        return "/member/pwd/forgetPwd";
    }

    //找回密码-验证用户名是否存在-验证验证码是否正确-给用户发送验证码
    @RequestMapping(value = "/forgetPwd/validate/{type}/{yzm}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject forgetPwdValidate(@PathVariable String type, @PathVariable String yzm, String account, HttpServletRequest request) {
        logger.info("forgetPwdValidate :type:{}, yzm:{}, account:{}", type, yzm, account);
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(type) || StringUtils.isBlank(yzm)) {
            logger.info("account:[{}], type:[{}], yzm:[{}]  其中有异常", account, type, yzm);
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数异常");
            return jsonObject;
        }

        if (type.equals("email")) {
            if (!EmailUtils.isValidEmail(account)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "参数异常");
                logger.info("destNo:[{}] 有异常", account);
                return jsonObject;
            }
        } else {
            if (!MobileUtils.validateMobile(account)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "参数异常");
                logger.info("destNo:[{}] 有异常", account);
                return jsonObject;
            }
        }

        Member member = memberService.queryByAccount(account).getModel();
        if (member == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "用户名不存在");
            return jsonObject;
        }

        MemberThirdLoginOption memberThirdLoginOption = new MemberThirdLoginOption();
        memberThirdLoginOption.setMemberId(member.getId());
        ModelResult<MemberThirdLogin> memberThirdLoginModelResult = memberThirdLoginService.queryByOption(memberThirdLoginOption);
        if (memberThirdLoginModelResult.getModel() != null) {
            logger.info("找回密码 memberId:[{}] 是第三方:[{}] 登录的用户", memberThirdLoginModelResult.getModel().getMemberId(),
                    ThirdType.findByIndex(memberThirdLoginModelResult.getModel().getThirdType()).getDescription());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "第三方登录的用户不用找回密码");
            return jsonObject;
        }

        String codeUUID = CookieUtils.getCookieValue(request, GlobalParam.validateCode);
        String code = memcachedClient.get(codeUUID);
        if (code == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "验证码已经超时");
            return jsonObject;
        }

        if (!code.equals(yzm)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "输入的验证码不正确");
            return jsonObject;
        }

        ModelResult<String> modelResult = null;
        if (type.equals("email")) {
            modelResult = checkCodeRecordService.sendEmailCheckCode(account, CheckCodeRecordCheckType.findPwd, null, null);
        } else {
            modelResult = checkCodeRecordService.sendSmsCheckCode(account, CheckCodeRecordCheckType.findPwd, null, null);
        }

        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "发送成功");

        memcachedClient.set(modelResult.getModel() + "-" + CheckCodeRecordCheckType.findPwd.getIndex(), 2 * 60, account);
        String key = codeUUID + "-" + findPwd;
        memcachedClient.set(key, 4 * 60, account);
        return jsonObject;
    }

    //找回密码-验证系统发送的验证码
    @RequestMapping("/toValidateCode")
    public String toValidateCode(HttpServletRequest request, Model model) {
        String codeUUID = CookieUtils.getCookieValue(request, GlobalParam.validateCode);
        String key = codeUUID + "-" + findPwd;
        String destNo = memcachedClient.get(key);
        if (destNo == null) {
            logger.info("缓存中 key：{}:没有值", key);
            return "redirect:/exception/timeOut";
        }
        model.addAttribute("account", destNo);
        model.addAttribute("type", EmailUtils.isValidEmail(destNo) ? "email" : "mobile");
        return "/member/pwd/validateCode";
    }

    @RequestMapping("/toresetpwd/{token}")
    public String toresetpwd(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        return "/member/pwd/resetpwd";
    }

    //找回密码-重置密码
    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject resetpwd(HttpServletRequest request, String firPwd, String secPwd, String token) {
        JSONObject jsonObject = new JSONObject();
        String value = memcachedClient.get(token);
        if (StringUtils.isBlank(value)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "找回密码超时");
            return jsonObject;
        }

        String[] strArrays = value.split("_");
        String destNo = "";
        if (strArrays.length > 1) {
            destNo = strArrays[0];
        }

        if (!firPwd.equals(secPwd)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "两次密码输入不正确");
            return jsonObject;
        }

        Member member = memberService.queryByAccount(destNo).getModel();
        Member memberToDB = new Member();
        memberToDB.setId(member.getId());
        memberToDB.setPassword(MD5.md5Encode(firPwd));
        ModelResult<Boolean> modelResult = memberService.updateById(memberToDB);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(member.getId());
        memberOperLog.setOperType(MemberOperLogType.FIND_PASSWORD.getIndex());
        memberOperLog.setRemark("找回密码");
        memberOperLogService.save(memberOperLog);

        memcachedClient.delete(token);
        jsonObject.put("code", 222);
        jsonObject.put("msg", "更改密码成功");
        return jsonObject;
    }

    @AvoidDuplicateSubmitAnnotation(needSaveToken = true)
    @RequestMapping("/toRegister/{type}")
    public String toRegister(@PathVariable String type, Model model) {
        model.addAttribute("menu", "register");
        if (type != null && type.equals("mobile")) {

            return "/member/mobileRegister";
        }
        return "/member/emailRegister";
    }


    //手机注册
    @AvoidDuplicateSubmitAnnotation(needRemoveToken = true)
    @RequestMapping("/mobileRegister")
    @ResponseBody
    public JSONObject mobileRegister(Member member, String secPassword, String code, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();

        String mobile = member.getMobile();
/*         String key = MD5.md5Encode(mobile);
        String cacheVaue = memcachedClient.get(MD5.md5Encode(key));

        if (StringUtils.isBlank(cacheVaue) || !cacheVaue.equals(mobile+"_"+ CheckCodeRecordCheckType.register.getIndex()+"_1")) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请重新发送验证码");

            logger.info("缓存里面没有值");
            return jsonObject;
        }*/

        MemberOption option = new MemberOption();
        option.setNickName(member.getNickName());
        List<Member> memberList = memberService.queryByOption(option).getModel();
        if (memberList != null && memberList.size() > 0) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "昵称已经被注册了");
            return jsonObject;
        }


        if (member.getMobile() == null || !MobileUtils.validateMobile(member.getMobile()) || !mobile.equals(member.getMobile())) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "电话号码输入错误");
            logger.info("用户输入的电话号码：{}：有问题", member.getMobile());
            return jsonObject;
        }

        if (StringUtils.isBlank(secPassword) || !secPassword.equals(member.getPassword())) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "密码输入有问题");
            logger.info("用户输入的密码有问题：");
            return jsonObject;
        }

        ModelResult<String> modelResult = checkCodeRecordService.checkSmsCheckCode(code, member.getMobile(), CheckCodeRecordCheckType.register, null);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            logger.info("验证手机号码:{}", modelResult.getErrorMsg());
            return jsonObject;
        }

        member.setAccount(member.getMobile());
        member.setPassword(MD5.md5Encode(member.getPassword()));
        member.setActiveStatus(MemberActiveStatus.actived.getIndex());
        member.setVerified(MemberVerified.noVerified.getIndex());
        Member memberFromDB = memberService.saveMember(member).getModel();

        jsonObject.put("code", 222);
        jsonObject.put("msg", "注册成功");

        logger.info("用户id:[{}], 用户昵称:[{}], [手机]注册成功", memberFromDB.getId(), memberFromDB.getAccount());

        ExecutorUtil.fixedThread().execute(new Runnable() {
            @Override
            public void run() {
                MemberVip memberVip = new MemberVip();
                memberVip.setMemberId(memberFromDB.getId());
                memberVip.setUpdator(memberFromDB.getAccount());
                memberVip.setViped(MemberVipType.IS_VIP.getIndex());
                memberVip.setStartTime(DateUtil.getTheDayZero());
                memberVip.setEndTime(DateUtil.getTheDayZero());
                memberVip.setCreator(memberFromDB.getAccount());
                memberVip.setRemark("初始化");
                memberVipService.saveMemberVip(memberVip);

                MemberPointLog memberPointLog = new MemberPointLog();
                memberPointLog.setMemberId(memberFromDB.getId());
                memberPointLog.setTransType(MemberPointLogTransType.REGISTER.getIndex());
                memberPointLogService.sendPoint(memberPointLog);
                
                //发送个人消息
                logger.info("memberId [{}] 普通手机注册成功，发送个人消息", memberFromDB.getId());
                String msgContent = "您好！您已成功注册为巨作网的新会员，平台将赠送您50积分，请注意查看后台积分记录，请尽快完善资料，平台将再次赠送您50积分！";
                msgRecordService.addPersonMsg(memberFromDB.getId(), msgContent);
            }
        });
        
        //注册自动登录begin
        try {
        	logger.info("[{}]注册后自动登录开始：", mobile);
	        final String buyCarUUID = CookieUtils.getCookieValue(request, GlobalParam.buyCar);
	        ExecutorUtil.fixedThread().execute(new Runnable() {
	            @Override
	            public void run() {
	                asnyAfterLogin(memberFromDB, buyCarUUID);
	            }
	        });
	
	        String uuidStr = UUIDUtils.getUuidStr();
	
	        MemberVo memberVo = new MemberVo();
	        try {
	            BeanUtils.copyProperties(memberVo, memberFromDB);
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	
	//        MemberVip memberVip = memberVipService.judgeVipByMemberId(memberFromDB.getId()).getModel();
	//        if (memberVip != null) {
	//            memberVo.setVip(true);
	//        } else {
	            memberVo.setVip(false);
	//        }
	
	//        Designer designer = designerService.queryByMemberId(memberFromDB.getId()).getModel();
	//        if (designer != null /***&& designer.getStatus()==2***/) { //完善资料自动升级为高级会员
	//            memberVo.setDesigner(true);
	//            memberVo.setDesignerId(designer.getId());
	//        } else {
	            memberVo.setDesigner(false);
	//        }
	        memberVo.setThird(false);
//	        MemberPoint memberPoint = memberPointService.selectByMemberId(memberVo.getId()).getModel();
	        memberVo.setBalance(new BigDecimal(MemberPointLogTransType.REGISTER.getPoint()));
	        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
	        CookieUtils.setCookie(request, response, globalParam.getUuidCookie(), uuidStr, globalParam.getMemberLoginCookieTime());
	        logger.info("[{}]注册后自动登录结束", mobile);
	        //注册自动登录end
        } catch (Exception e) {
        	logger.error("自动登录失败："+e.getMessage(),  e);
        }
        
        return jsonObject;
    }

    //邮箱注册
    @AvoidDuplicateSubmitAnnotation(needRemoveToken = true)
    @RequestMapping("/emailRegister")
    @ResponseBody
    public JSONObject emailRegister(String secPassword, String code, Member member, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();

        String email = member.getEmail();
        if ( StringUtils.isBlank(email) || !EmailUtils.isValidEmail(email)){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请输入正确的邮箱地址");
            return jsonObject;
        }

        email = email.toLowerCase();

        MemberOption option = new MemberOption();
        option.setNickName(member.getNickName());
        List<Member> memberList = memberService.queryByOption(option).getModel();
        if (memberList != null && memberList.size() > 0) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "昵称已经被注册了");
            return jsonObject;
        }

        if (!EmailUtils.isValidEmail(email)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "邮箱格式错误");
            return jsonObject;
        }

        //发送邮箱之前 判断这个邮箱是否已经被注册
        ModelResult<Boolean> memberModelResult = memberService.judgeEmailOrMobileIsExist(email, CheckCodeRecordDestType.email);
        if (!memberModelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", memberModelResult.getErrorMsg());
            return jsonObject;
        }

        if (memberModelResult.getModel()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "邮箱已经被注册了");
            return jsonObject;
        }

        if (StringUtils.isBlank(secPassword) || !secPassword.equals(member.getPassword())) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "密码输入有问题");
            logger.info("用户输入的密码有问题：");
            return jsonObject;
        }

        ModelResult<String> result = checkCodeRecordService.checkEmailCheckCode(code, email, CheckCodeRecordCheckType.register, null);
        if (!result.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", result.getErrorMsg());
            return jsonObject;
        }

        member.setPassword(MD5.md5Encode(member.getPassword()));
        member.setAccount(email);
        member.setActiveStatus(MemberActiveStatus.unActive.getIndex());
        member.setVerified(MemberVerified.noVerified.getIndex());
        Member memberFromDB = memberService.saveMember(member).getModel();

        jsonObject.put("code", 222);
        jsonObject.put("msg", "注册成功");
        jsonObject.put("model", EmailUtils.getEmailOwner(email));
        logger.info("用户Id:[{}] 用户昵称:[{}], 本站注册:[邮箱]成功", memberFromDB.getId(), memberFromDB.getAccount());

        ExecutorUtil.fixedThread().execute(new Runnable() {
            @Override
            public void run() {

                MemberVip memberVip = new MemberVip();
                memberVip.setMemberId(memberFromDB.getId());
                memberVip.setUpdator(memberFromDB.getAccount());
                memberVip.setViped(MemberVipType.IS_VIP.getIndex());
                memberVip.setStartTime(DateUtil.getTheDayZero());
                memberVip.setEndTime(DateUtil.getTheDayZero());
                memberVip.setCreator(memberFromDB.getAccount());
                memberVip.setRemark("初始化");
                memberVipService.saveMemberVip(memberVip);

                MemberPointLog memberPointLog = new MemberPointLog();
                memberPointLog.setMemberId(memberFromDB.getId());
                memberPointLog.setTransType(MemberPointLogTransType.REGISTER.getIndex());
                memberPointLogService.sendPoint(memberPointLog);

                //发送个人消息
                logger.info("memberId [{}] 普通邮箱注册成功，发送个人消息", memberFromDB.getId());
                String msgContent = "您好！您已成功注册为巨作网的新会员，平台将赠送您50积分，请注意查看后台积分记录，请尽快完善资料，平台将再次赠送您50积分！";
                msgRecordService.addPersonMsg(memberFromDB.getId(), msgContent);                
            }
        });

        
        //注册自动登录begin
        try {
        	logger.info("[{}]注册后自动登录开始：", email);
	        final String buyCarUUID = CookieUtils.getCookieValue(request, GlobalParam.buyCar);
	        ExecutorUtil.fixedThread().execute(new Runnable() {
	            @Override
	            public void run() {
	                asnyAfterLogin(memberFromDB, buyCarUUID);
	            }
	        });
	
	        String uuidStr = UUIDUtils.getUuidStr();
	
	        MemberVo memberVo = new MemberVo();
	        try {
	            BeanUtils.copyProperties(memberVo, memberFromDB);
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	
	//        MemberVip memberVip = memberVipService.judgeVipByMemberId(memberFromDB.getId()).getModel();
	//        if (memberVip != null) {
	//            memberVo.setVip(true);
	//        } else {
	            memberVo.setVip(false);
	//        }
	
	//        Designer designer = designerService.queryByMemberId(memberFromDB.getId()).getModel();
	//        if (designer != null /***&& designer.getStatus()==2***/) { //完善资料自动升级为高级会员
	//            memberVo.setDesigner(true);
	//            memberVo.setDesignerId(designer.getId());
	//        } else {
	            memberVo.setDesigner(false);
	//        }
	        memberVo.setThird(false);
//	        MemberPoint memberPoint = memberPointService.selectByMemberId(memberVo.getId()).getModel();
	        memberVo.setBalance(new BigDecimal(MemberPointLogTransType.REGISTER.getPoint()));
	        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
	        CookieUtils.setCookie(request, response, globalParam.getUuidCookie(), uuidStr, globalParam.getMemberLoginCookieTime());
	        logger.info("[{}]注册后自动登录结束", email);
	        //注册自动登录end
        } catch (Exception e) {
        	logger.error("自动登录失败："+e.getMessage(),  e);
        }
        
        return jsonObject;
    }
    
    
    private void asnyAfterLogin(Member memberFromDB, String buyCarUUID) {
        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(memberFromDB.getId());
        memberOperLog.setOperType(MemberOperLogType.LOGIN.getIndex());
        memberOperLog.setRemark("用户注册自动登录");
        memberOperLogService.save(memberOperLog);

        /****登录后暂时不送积分
         MemberPointLog memberPointLog = new MemberPointLog();
         memberPointLog.setMemberId(memberFromDB.getId());
         memberPointLog.setTransType(MemberPointLogTransType.LOGIN.getIndex());
         memberPointLogService.sendPoint(memberPointLog);***/

        if (StringUtils.isBlank(buyCarUUID)) {
            logger.info("从cookie中拿不到购物车值");
            return;
        }

        List<Integer> goodsCar = memcachedClient.get(buyCarUUID);
        if (goodsCar == null) {

            logger.info("从缓存中拿值为null");
            return;
        }
        try {
            memcachedClient.delete(buyCarUUID);
            shoppingCarService.saveList(goodsCar, memberFromDB.getId());
        } catch (Exception e) {
            logger.error("保存列表", e);
        }
    }

}
