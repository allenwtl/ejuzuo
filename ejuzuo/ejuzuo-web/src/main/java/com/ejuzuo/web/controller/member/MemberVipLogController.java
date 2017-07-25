package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberVip;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.service.MemberVipLogService;
import com.ejuzuo.common.service.MemberVipService;
import com.ejuzuo.common.vo.MemberVipLogVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Controller
@RequestMapping("memberVipLog")
public class MemberVipLogController extends BaseController {

    @Resource
    private MemberVipService memberVipService ;

    @Resource
    private MemberVipLogService memberVipLogService ;

    @Login
    @RequestMapping("/index")
    public String list(HttpServletRequest request, Model model){
        MemberVip memberVip = memberVipService.queryByMemberId(getMemberId(request)).getModel();
        MemberVipLog memberVipLog = new MemberVipLog();
        memberVipLog.setMemberId(memberVip.getMemberId());
        int totalCount = memberVipLogService.count(memberVipLog).getModel();
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("vip", memberVip);
        model.addAttribute("menu", "vip");
        return "/member/vip/vipList";
    }

    @Login
    @RequestMapping("/ajaxList")
    @ResponseBody
    public List<MemberVipLogVO> ajaxList(HttpServletRequest request, DataPage<MemberVipLog> dataPage){
        MemberVipLog memberVipLog = new MemberVipLog();
        memberVipLog.setMemberId(getMemberId(request));
        dataPage.setNeedTotalCount(false);
        dataPage.setPageSize(5);
        return memberVipLogService.queryVOPage(dataPage, memberVipLog).getPage().getDataList();
    }



}
