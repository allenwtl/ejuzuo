package com.ejuzuo.admin.controller.member;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.service.MemberCareService;

@Controller
@RequestMapping("/member/care")
public class MemberCareController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MemberCareController.class);
	@Resource
	private MemberCareService memberCareService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "member/member-care-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberCare> page,
			@ModelAttribute MemberCare qVo) {
		DataPage<MemberCare> dataPage = page.toDataPage();
		PageResult<MemberCare> pageResult = null;
		try {
			pageResult = memberCareService.queryPage(qVo,dataPage);
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
