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
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.option.MemberSponsorLogOption;
import com.ejuzuo.common.service.MemberSponsorLogService;

@Controller
@RequestMapping("/member/sponsor/log")
public class MemberSponsorLogController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberSponsorLogController.class);
	@Resource
	private MemberSponsorLogService memberSponsorLogService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "member/member-sponsor-log-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberSponsorLog> page,
			@ModelAttribute MemberSponsorLogOption qVo) {
		DataPage<MemberSponsorLog> dataPage = page.toDataPage();
		PageResult<MemberSponsorLog> pageResult = null;
		try {
			pageResult = memberSponsorLogService.queryPage(qVo,dataPage);
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
