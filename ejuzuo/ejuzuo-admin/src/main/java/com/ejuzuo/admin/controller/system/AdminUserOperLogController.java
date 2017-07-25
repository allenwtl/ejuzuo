package com.ejuzuo.admin.controller.system;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.domain.AdminUserOperLog;
import com.ejuzuo.common.service.AdminUserOperLogService;

@Controller
@RequestMapping("/system/user-oper-log")
public class AdminUserOperLogController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminUserOperLogController.class);
	
	@Autowired
	private AdminUserOperLogService adminUserOperLogService;
	
	@RequiresPermissions("SYSTEM:USEROPERLOG")
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		
		List<AdminOperType> operTypes = AdminOperType.getAll(AdminOperType.class);
		modelMap.put("operTypes", operTypes);
		
		return "system/user-oper-log";
	}
	
	@RequiresPermissions("SYSTEM:USEROPERLOG")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<AdminUserOperLog> page,
			@RequestParam(required = false) AdminOperType operType,
			@RequestParam(required = false) String account,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Calendar beginDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Calendar endDate) {
		
		DataPage<AdminUserOperLog> dataPage = page.toDataPage();
		
		AdminUserOperLog qVo = new AdminUserOperLog();
		qVo.setAccount(account);
		qVo.setOperType(operType);
		
		PageResult<AdminUserOperLog> pageResult = null;
		try {
			pageResult = adminUserOperLogService.queryPage(dataPage, qVo, beginDate, endDate);
		} catch (Exception e) {
			logger.error("Page net error", e);
			return Page.returnEmptyPage();
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		
		dataPage = pageResult.getPage();
		return Page.returnPage(dataPage);
	}
	
}
