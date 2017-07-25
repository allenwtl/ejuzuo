package com.ejuzuo.web.controller.security;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.MemberOperLog;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.service.CheckCodeRecordService;
import com.ejuzuo.common.service.MemberOperLogService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.util.EmailUtils;
import com.ejuzuo.common.util.MD5;
import com.ejuzuo.common.util.MobileUtils;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by allen on 2016/4/17.
 */


@Controller
@RequestMapping("security")
public class SecurityController extends BaseController {


    @Resource
    private MemberService memberService;

    @Resource
    private CheckCodeRecordService checkCodeRecordService;

    @Resource
    private MemberOperLogService memberOperLogService;


    @Login
    @RequestMapping("/index/{type}")
    public String index(HttpServletRequest request, Model model, @PathVariable String type) {
        model.addAttribute("menu", "security");

        Member member = null;
        if (type != null && type.equals("mobile")) {
            member = memberService.queryByMemberId(getMemberId(request)).getModel();
            if (StringUtils.isNotBlank(member.getMobile())) {
                model.addAttribute("mobile", true);
            } else {
                model.addAttribute("mobile", false);
            }

            return "/securitycenter/securityMobile";
        } else if (type != null && type.equals("email")) {
            member = memberService.queryByMemberId(getMemberId(request)).getModel();
            if (StringUtils.isNotBlank(member.getEmail())) {
                model.addAttribute("email", true);
            } else {
                model.addAttribute("email", false);
            }
        }

        return "/securitycenter/securityEmail";
    }


    @Login
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(Model model, HttpServletRequest request) {
        MemberVo memberVo = getMember(request);
        if (memberVo.isThird()) {
            model.addAttribute("isThird", true);
        } else {
            if (StringUtils.isNotBlank(memberVo.getMobile())) {
                model.addAttribute("mobile", MobileUtils.closedMobile(memberVo.getMobile()));
                model.addAttribute("type", "mobile");
            }

            if (StringUtils.isNotBlank(memberVo.getEmail())) {
                model.addAttribute("email", EmailUtils.closedEmail(memberVo.getEmail()));
                model.addAttribute("type", "email");
            }

            model.addAttribute("isThird", false);
        }
        model.addAttribute("menu", "security");
        return "/securitycenter/updatePassword";
    }

    //修改密码
    @Login
    @RequestMapping("/updatePassword")
    @ResponseBody
    public JSONObject updatePassword(String type, String code, String oldPwd, String firPwd, String secPwd, HttpServletRequest request) {
        String key = CheckCodeRecordCheckType.updatePwd.getIndex() + "-" + getMemberId(request);
        Member member = memcachedClient.get(key);

        JSONObject jsonObject = new JSONObject();

        if (member == null) {
            logger.info("用户:[{}]:更新密码 拿不到上一部的缓存，超时");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "验证超时，请重新验证");
            return jsonObject;
        }

        if (!MD5.md5Encode(oldPwd).equals(member.getPassword())) {
            logger.info("用户:[{}]:更新密码操作", member.getAccount());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "老密码输入错误");
            return jsonObject;
        }

        if (!secPwd.equals(firPwd)) {
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
            memberToDB.setPassword(MD5.md5Encode(firPwd));
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

}
