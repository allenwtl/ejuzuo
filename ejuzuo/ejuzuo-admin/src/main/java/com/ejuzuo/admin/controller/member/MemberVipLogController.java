package com.ejuzuo.admin.controller.member;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.service.MemberVipLogService;

@Controller
@RequestMapping("/member/vip/log")
public class MemberVipLogController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberVipLogController.class);
	@Resource
	private MemberVipLogService memberVipLogService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "member/member-vip-log-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberVipLog> page,
			@ModelAttribute MemberVipLog qVo) {
		DataPage<MemberVipLog> dataPage = page.toDataPage();
		PageResult<MemberVipLog> pageResult = null;
		try {
			pageResult = memberVipLogService.queryPage(dataPage,qVo);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = pageResult.getPage();
		return Page.returnPage(dataPage);
	}
	
}
