package com.ejuzuo.admin.controller.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.AdminUserLog;
import com.ejuzuo.common.service.AdminUserLogService;

@Controller
@RequestMapping("/system/user-log")
public class AdminUserLogController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminUserLogController.class);
	
	@Autowired
	private AdminUserLogService adminUserLogService;
	
	@RequiresPermissions("SYSTEM:USERLOG")
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "system/user-log";
	}
	
	@RequiresPermissions("SYSTEM:USERLOG")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<AdminUserLog> page, 
			@ModelAttribute AdminUserLog qObj,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Calendar beginDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Calendar endDate) {
		
		DataPage<AdminUserLog> dataPage = page.toDataPage();
		
		PageResult<AdminUserLog> pageResult = null;
		try {
			pageResult = adminUserLogService.queryPage(dataPage, qObj, beginDate, endDate);
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
	
	@RequiresPermissions("SYSTEM:USERLOG")
	@RequestMapping(value="{id:[1-9][0-9]*}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> user(@PathVariable Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<AdminUserLog> modelResult = adminUserLogService.query(id);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			map.put("data", modelResult.getModel());
		} else {
			map.put("success", false);
			map.put("msg", "获取LOG详情失败!");
		}
		
		return map;
	}
	
}
