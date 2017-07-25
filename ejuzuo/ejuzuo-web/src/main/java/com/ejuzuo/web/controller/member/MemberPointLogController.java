package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MemberPointService;
import com.ejuzuo.common.vo.MemberPointLogVO;
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
@RequestMapping("pointLog")
public class MemberPointLogController extends BaseController {

    @Resource
    private MemberPointLogService memberPointLogService ;

    @Resource
    private MemberPointService memberPointService;

    @Login
    @RequestMapping("/index")
    public String pointList(HttpServletRequest request, Model model) {
        Integer memberId = getMemberId(request);
        MemberPointLogOption memberPointLogOption = new MemberPointLogOption();
        memberPointLogOption.setMemberId(memberId);
        int totalCount = memberPointLogService.count(memberPointLogOption).getModel();
        model.addAttribute("data", memberPointService.selectByMemberId(memberId).getModel());
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("menu", "point");
        return "/member/memberPointLog";
    }


    @RequestMapping("/list")
    @ResponseBody
    public JSONObject ajaxList(HttpServletRequest request, DataPage<MemberPointLogVO> dataPage) {
        JSONObject jsonObject = new JSONObject();
        MemberPointLogOption memberPointLogOption = new MemberPointLogOption();
        memberPointLogOption.setMemberId(getMemberId(request));
        dataPage.setNeedTotalCount(false);
        dataPage.setOrderBy("id");
        dataPage.setOrder("desc");

        dataPage = memberPointLogService.queryVOByPage(memberPointLogOption, dataPage).getModel();
        jsonObject.put("data", dataPage.getDataList());

        return jsonObject;
    }


}
