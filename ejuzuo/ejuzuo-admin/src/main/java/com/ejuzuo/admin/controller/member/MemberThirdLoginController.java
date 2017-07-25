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
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.MemberThirdLogin;
import com.ejuzuo.common.option.MemberThirdLoginOption;
import com.ejuzuo.common.service.MemberThirdLoginService;

@Controller
@RequestMapping("/member/third")
public class MemberThirdLoginController {

	private static final Logger logger = LoggerFactory.getLogger(MemberPointController.class);
	
	@Resource
	private MemberThirdLoginService memberThirdLoginService;

	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "member/member-third-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberThirdLogin> page,
			@ModelAttribute MemberThirdLoginOption qVo) {
		DataPage<MemberThirdLogin> dataPage = page.toDataPage();
		PageResult<MemberThirdLogin> pageResult = null;
		try {
			pageResult = memberThirdLoginService.queryPage(dataPage,qVo);
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
