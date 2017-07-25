package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.service.MemberCareService;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.ResultTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tianlun.wu on 2016/4/26 0026.
 */
@Controller
@RequestMapping("care")
public class MemberCareController extends BaseController {


    @Resource
    private MemberCareService memberCareService;


    @Login(ResultTypeEnum.json)
    @RequestMapping("/follow/{objectId}/{objectType}")
    @ResponseBody
    public JSONObject follow(@PathVariable Integer objectId, @PathVariable Integer objectType, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ObjectType tempObjectType = ObjectType.findByIndex(objectType);
        if(tempObjectType == null ){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "参数错误");
            return jsonObject;
        }

        MemberCare memberCare = new MemberCare();
        memberCare.setMemberId(getMemberId(request));
        memberCare.setObjectType(objectType);
        memberCare.setObjectId(objectId);
        ModelResult<Integer> modelResult = memberCareService.follow(memberCare);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "关注成功");
        jsonObject.put("model", modelResult.getModel());
        return jsonObject;
    }

}
