package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.webutil.member.MobileUtils;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.service.CheckCodeRecordService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.util.EmailUtils;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by tianlun.wu on 2016/3/30 0030.
 */

@Controller
@RequestMapping("checkCodeRecord")
public class CheckCodeRecordController extends BaseController {

    @Resource
    private CheckCodeRecordService checkCodeRecordService;

    @Resource
    private MemberService memberService;

    @Resource
    private HashMap emailAddress;

    private static String  ejuzuo = "http://www.ejuzuo.com";

    //邮箱验证 同步
    @RequestMapping("/checkEmail/{type}/{code}")
    public String checkEmail(@PathVariable String code, @PathVariable Integer type, Model model, HttpServletRequest request) {
        CheckCodeRecordCheckType checkCodeRecordCheckType = CheckCodeRecordCheckType.findByIndex(type);
        String key = code + "-" + checkCodeRecordCheckType.getIndex();
        String email = memcachedClient.get(key);
        logger.info("邮箱格式:{}", email);
        if (!EmailUtils.isValidEmail(email)) {
            model.addAttribute("msg", "邮箱格式不对");
            return "/member/checkEmail";
        }

        ModelResult<String> modelResult = checkCodeRecordService.checkEmailCheckCode(code, email, checkCodeRecordCheckType, null);

        if (!modelResult.isSuccess()) {
            model.addAttribute("msg", modelResult.getErrorMsg());
            return "/member/checkEmail";
        }

        if (checkCodeRecordCheckType.getIndex() == CheckCodeRecordCheckType.authEmail.getIndex()) {
            Member member = memberService.queryByMemberId(getMemberId(request)).getModel();
            member.setEmail(email);
            memberService.updateById(member);
        }

        memcachedClient.delete(key);
        model.addAttribute("msg", "验证成功");
        return "/member/checkEmail";
    }

    //邮箱验证的
    @RequestMapping("/emailSuccess/{key}")
    public String emailSuccessPage(@PathVariable String key, Model model) {
        if (StringUtils.isBlank(key)) {
            model.addAttribute("email", emailAddress.get("163"));
            return "/member/emailSuccess";
        }
        model.addAttribute("email", emailAddress.get(key));
        return "/member/emailSuccess";
    }

    /**
     * @param mobile
     * @param type
     * @return
     * @function 手机发送验证码
     * 注册,验证手机,忘记密码
     */
    @RequestMapping(value = "/sendSms/{type}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendSms(String mobile, @PathVariable Integer type, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String referer = request.getHeader("referer");
        if(referer == null || !referer.contains(ejuzuo) ){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数错误");
            logger.info("referer:{} 非法请求", referer);
            return jsonObject;
        }

        if (!MobileUtils.validateMobile(mobile)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "手机格式错误");
            logger.info("传入的手机号码为:{}", mobile);
            return jsonObject;
        }

        CheckCodeRecordCheckType checkType = CheckCodeRecordCheckType.findByIndex(type);

        if (type == null || checkType == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数错误");
            logger.info("传入的参数type为：{}", type);
            return jsonObject;
        }
        //boolean result = memberService.judgeEmailOrMobileIsExist(mobile, CheckCodeRecordDestType.mobile).getModel();
        if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex() /***||
                checkType.getIndex() == CheckCodeRecordCheckType.authMobile.getIndex()**/) {
        	Member member = memberService.queryByAccount(mobile).getModel();        	
            //发送手机验证码之前 判断这个手机是否已经被注册
            if (member != null) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "该号码已经被注册了");
                return jsonObject;
            }
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
        	Member member = memberService.queryByAccount(mobile).getModel();
            if (member == null) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "找不到此号码");
                return jsonObject;
            }
        }

        ModelResult<String> modelResult = checkCodeRecordService.sendSmsCheckCode(mobile, checkType, null, null);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

/*        if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            memcachedClient.set(MD5.md5Encode(mobile), 60, mobile + "_" + CheckCodeRecordCheckType.register.getIndex() + "_" + "1");
        }*/

        jsonObject.put("code", 222);
        jsonObject.put("msg", "发送成功");
        return jsonObject;
    }

    //验证手机号码  异步
    @RequestMapping("/checkSms/{type}")
    @ResponseBody
    public JSONObject checkSms(String code, @PathVariable Integer type, @RequestParam(defaultValue = "null") String mobile,
                               HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CheckCodeRecordCheckType checkType = CheckCodeRecordCheckType.findByIndex(type);
        if (type == null || checkType == null) {
            logger.info("传入参数:type:{}", type);
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请求非法");
            return jsonObject;
        }

        if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            String value = memcachedClient.get(mobile);
            if (StringUtils.isBlank(value)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "验证超时，请重新发送");
                logger.info("缓存中找不到 mobile:{}:为key 的注册业务:", mobile);
                return jsonObject;
            }

            if (!value.equals(mobile + "_" + CheckCodeRecordCheckType.register.getIndex() + "_" + 1)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "系统异常");
                logger.info("缓存中 mobile为:{}:key 的注册业务的:value:{}:异常", mobile, value);
                return jsonObject;
            }
        }


        ModelResult<String> modelResult = checkCodeRecordService.checkSmsCheckCode(code, mobile, checkType, null);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            logger.info("验证手机号码:{}", modelResult.getErrorMsg());
            return jsonObject;
        }

        if (checkType.getIndex() == CheckCodeRecordCheckType.authMobile.getIndex()) {
            Member member = memberService.queryByMemberId(getMemberId(request)).getModel();
            Member memberToDB = new Member();
            memberToDB.setId(member.getId());
            memberToDB.setMobile(mobile);
            memberService.updateById(memberToDB);
        }


        if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            memcachedClient.delete(mobile);
        }

        if (checkType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
            memcachedClient.set(modelResult.getModel(), 5*60, mobile+"_"+CheckCodeRecordCheckType.findPwd.getIndex()+"_1");
            jsonObject.put("token", modelResult.getModel());
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "验证成功");


        return jsonObject;
    }


    @RequestMapping("/mobileRegisterSuccess")
    public String mobileRegisterSuccess() {

        return "/member/mobileRegisterSuccess";
    }

    //邮箱发送验证码
    @RequestMapping(value = "/sendEmail/{type}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendEmail(String email, @PathVariable Integer type, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String referer = request.getHeader("referer");
        if(referer == null || !referer.contains(ejuzuo) ){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数错误");
            logger.info("referer:{} 非法请求", referer);
            return jsonObject;
        }
        
        if (!EmailUtils.isValidEmail(email)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "邮箱格式错误");
            return jsonObject;
        }

        CheckCodeRecordCheckType checkType = CheckCodeRecordCheckType.findByIndex(type);

        if (type == null || checkType == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数错误");
            logger.info("传入的参数type为：{}", type);
            return jsonObject;
        }

        //boolean result = memberService.judgeEmailOrMobileIsExist(email, CheckCodeRecordDestType.email).getModel();
        if (/**checkType.getIndex() == CheckCodeRecordCheckType.authEmail.getIndex()***/ 
        		checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
        	Member member = memberService.queryByAccount(email).getModel();        
            //发送邮箱之前 判断这个邮箱是否已经被注册
            if (member != null) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "邮箱已经被注册了");
                return jsonObject;
            }
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
        	Member member = memberService.queryByAccount(email).getModel(); 
            if (member == null) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "找不到该邮箱");
                return jsonObject;
            }
        }

        ModelResult<String> modelResult = checkCodeRecordService.sendEmailCheckCode(email, checkType, null, null);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

/*        if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            memcachedClient.set(MD5.md5Encode(email),  60, email + "_" + CheckCodeRecordCheckType.register.getIndex() + "_" + "1");
        }*/

        jsonObject.put("code", 222);
        jsonObject.put("msg", "发送成功");
        jsonObject.put("model", EmailUtils.getEmailOwner(email));

        return jsonObject;
    }

    //验证个人邮箱 异步
    @RequestMapping("/validateEmail/{type}/{code}")
    @ResponseBody
    public JSONObject validateEmail(@PathVariable String code, @PathVariable Integer type, @RequestParam(defaultValue = "null") String email,
                                    HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CheckCodeRecordCheckType checkCodeRecordCheckType = CheckCodeRecordCheckType.findByIndex(type);
        if (type == null || checkCodeRecordCheckType == null || StringUtils.isBlank(code)) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请求有问题");
            logger.info("type:{};code:{}: 参数传入报错", type, code);
            return jsonObject;
        }
        if (checkCodeRecordCheckType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            String value = memcachedClient.get(email);
            if (StringUtils.isBlank(value)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "验证超时，请重新发送");
                logger.info("缓存中找不到 email为key 的注册业务:{}", email);
                return jsonObject;
            }

            if (!value.equals(email + "_" + CheckCodeRecordCheckType.register.getIndex() + "_" + 1)) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "系统异常");
                logger.info("缓存中 email为:{}:key 的注册业务的:value:{}:异常", email, value);
                return jsonObject;
            }
        }

        ModelResult<String> modelResult = checkCodeRecordService.checkEmailCheckCode(code, email, checkCodeRecordCheckType, null);
        if (!modelResult.isSuccess()) {
            logger.info("验证邮箱验证码：{}", modelResult.getErrorMsg());
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        if (checkCodeRecordCheckType.getIndex() == CheckCodeRecordCheckType.authEmail.getIndex()) {
            Member member = memberService.queryByMemberId(getMemberId(request)).getModel();
            member.setEmail(email);
            memberService.updateById(member);
        }

        if (checkCodeRecordCheckType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            memcachedClient.delete(email);
        }

        if (checkCodeRecordCheckType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
            memcachedClient.set(modelResult.getModel(), 5*60, email+"_"+CheckCodeRecordCheckType.findPwd.getIndex()+"_1");
            jsonObject.put("token", modelResult.getModel());
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "成功验证");
        return jsonObject;
    }


    //修改密码-发送邮箱验证码或者手机验证码
    @Login
    @RequestMapping("/sendCode/{codeType}")
    @ResponseBody
    public JSONObject sendCode(@PathVariable String codeType, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Member member = memberService.queryByMemberId(getMemberId(request)).getModel();
        ModelResult<String> modelResult = null;
        if (codeType.equals("email")) {
            if (StringUtils.isBlank(member.getEmail())) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "没有认证邮箱");
                return jsonObject;
            }
            modelResult = checkCodeRecordService.sendEmailCheckCode(member.getEmail(), CheckCodeRecordCheckType.updatePwd, member, null);
        } else {
            if (StringUtils.isBlank(member.getMobile())) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", "没有认证手机号码");
                return jsonObject;
            }
            modelResult = checkCodeRecordService.sendSmsCheckCode(member.getMobile(), CheckCodeRecordCheckType.updatePwd, member, null);
        }

        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }
        memcachedClient.add(CheckCodeRecordCheckType.updatePwd.getIndex() + "-" + getMemberId(request), 6 * 60, member);
        jsonObject.put("code", 222);
        jsonObject.put("msg", "发送成功");
        return jsonObject;
    }
}
