package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.option.MemberPointOption;
import com.ejuzuo.common.service.MemberPointService;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tianlun.wu on 2016/4/26 0026.
 */

@Controller
@RequestMapping("point")
public class MemberPointController extends BaseController {


    @Resource
    private MemberPointService memberPointService ;

    @Login
    @RequestMapping("/index")
    public String pointList(HttpServletRequest request, Model model, DataPage<MemberPoint> dataPage) {

        MemberPointOption memberPointOption = new MemberPointOption();
        memberPointOption.setMemberId(getMemberId(request));
        int totalCount = memberPointService.count(memberPointOption).getModel();
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("menu", "point");
        return "/member/memberPointLog";
    }


    @RequestMapping("/list")
    @ResponseBody
    public JSONObject ajaxList(HttpServletRequest request, Model model, DataPage<MemberPoint> dataPage) {
        JSONObject jsonObject = new JSONObject();
        MemberPointOption memberPointOption = new MemberPointOption();
        memberPointOption.setMemberId(getMemberId(request));
        dataPage.setNeedTotalCount(false);

        dataPage.setOrderBy("id");
        dataPage.setOrder("desc");

        dataPage = memberPointService.queryPage(dataPage, memberPointOption).getPage();
        jsonObject.put("data", dataPage.getDataList());

        return jsonObject;
    }

}
