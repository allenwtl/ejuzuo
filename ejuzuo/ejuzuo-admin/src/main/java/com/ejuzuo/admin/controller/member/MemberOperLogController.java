package com.ejuzuo.admin.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.ejuzuo.common.domain.MemberOperLog;
import com.ejuzuo.common.domain.type.MemberOperLogType;
import com.ejuzuo.common.option.MemberOperLogOption;
import com.ejuzuo.common.service.MemberOperLogService;

@Controller
@RequestMapping("/member/log")
public class MemberOperLogController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberOperLogController.class);
	@Resource
	private MemberOperLogService memberOperLogService;
	
	@RequiresPermissions("MEMBER:OPER:LOG:INFO")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		List<MemberOperLogType> operTypes = MemberOperLogType.getAll(MemberOperLogType.class);
		modelMap.addAttribute("operTypes", operTypes);
		return "member/member-log-index";
	}
	
	@RequiresPermissions("MEMBER:OPER:LOG:INFO")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberOperLog> page,
			@ModelAttribute MemberOperLogOption qVo) {
		
		DataPage<MemberOperLog> dataPage = page.toDataPage();
		
		PageResult<MemberOperLog> pageResult = null;
		try {
			pageResult = memberOperLogService.queryPage(dataPage, qVo);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		
		DataPage<MemberOperLog> result = pageResult.getPage();
		return Page.returnPage(result);
	}
	
}
