package com.ejuzuo.web.controller.share;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.service.MsgRecordService;
import com.ejuzuo.common.service.PromoRecordService;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.RequestUtil;

/**
 * Created by tianlun.wu on 2016/4/21 0021.
 */

@Controller
@RequestMapping("/share")
public class ShareController extends BaseController {

    @Resource
    private PromoRecordService promoRecordService;
    @Resource
    private MemberPointLogService memberPointLogService;
    @Resource
    private MemberService memberService;
    @Resource
    private MsgRecordService msgRecordService;

    @Login
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("menu", "share");

        String code = UUIDUtils.getUuidStr();

        model.addAttribute("code", code);

        return "/share/shareIndex";
    }

    @RequestMapping("/callback/{code}")
    public String callBack(@PathVariable String code, HttpServletRequest request) {
        PromoRecord record = new PromoRecord();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", RequestUtil.getUserIp(request));
        jsonObject.put("referer", request.getHeader("Referer"));
        jsonObject.put("userAgent", request.getHeader("User-Agent"));
        record.setCallbackInfo(jsonObject.toJSONString());
        record.setPromoCode(code);
        ModelResult<PromoRecord> mr= promoRecordService.checkCode(record);
    	//分享回调成功，发积分。
    	if (mr.isSuccess()) {
    		PromoRecord pr = mr.getModel();
            MemberPointLog memberPointLog = new MemberPointLog();
            memberPointLog.setMemberId(pr.getMemberId());
            memberPointLog.setTransType(MemberPointLogTransType.SHARE.getIndex());
            memberPointLog.setRelatedId(pr.getId());
            memberPointLog.setRelatedType(ObjectType.share.getIndex());
            memberPointLogService.sendPoint(memberPointLog);
            
            //发送系统中的个人消息
        	String msgContent = null; //普通会员		
    		if(memberService.isDesingerByMemberId(pr.getMemberId())){
    			msgContent = "您好！您已成功分享！恭喜您获得了50积分奖励，请注意查看后台积分记录！";
    		}
    		if(memberService.isVipByMemberId(pr.getMemberId())) {
    			msgContent = "您好！您已成功分享！恭喜您获得了80积分奖励，请注意查看后台积分记录！";
    		}
    		if(StringUtils.isNotEmpty(msgContent)) { 
    			logger.info("会员分享获得积分, 发送系统系统！分享ID:[{}]", pr.getId());
    			msgRecordService.addPersonMsg(pr.getMemberId(), msgContent);     
    		} else {
    			logger.info("普通会员分享没有积分送！分享ID:[{}]", pr.getId());
    		}
    	}       
        return "redirect:/index";
    }

    @Login
    @RequestMapping(value = "saveCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveCode(String code, HttpServletRequest request) {
        PromoRecord record = new PromoRecord();
        record.setMemberId(getMemberId(request));
        record.setPromoCode(code);
        record.setStatus(0);
        record.setViewCount(0);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", RequestUtil.getUserIp(request));
        jsonObject.put("referer", request.getHeader("Referer"));
        jsonObject.put("userAgent", request.getHeader("User-Agent"));
        record.setPromoInfo(jsonObject.toJSONString());

        ModelResult<PromoRecord> modelResult = promoRecordService.save(record);

        JSONObject resultJSON = new JSONObject();
        if (modelResult.isSuccess()) {
            resultJSON.put("code", 222);
            resultJSON.put("msg", "保存成功");
            return resultJSON;
        } else {
            resultJSON.put("code", 444);
            resultJSON.put("msg", "保存失败");
            return resultJSON ;
        }
    }

}
