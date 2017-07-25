package com.ejuzuo.admin.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.aop.UserLog;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.domain.AdminRole;
import com.ejuzuo.common.domain.AdminRolePerm;
import com.ejuzuo.common.service.AdminRolePermService;
import com.ejuzuo.common.service.AdminRoleService;

@Controller
@RequestMapping("/system/role")
public class AdminRoleController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminRoleController.class);
	
	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private AdminRolePermService adminRolePermService;
	
	@RequiresPermissions("SYSTEM:ROLE:INFO")
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "system/role/role-index";
	}
	
	@RequiresPermissions("SYSTEM:ROLE:INFO")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@RequestParam(defaultValue = "15") Integer pageSize, 
			@RequestParam(defaultValue = "1") Integer pageNo,
			@ModelAttribute AdminRole qObj) {
		
		DataPage<AdminRole> dataPage = new DataPage<AdminRole>();
		dataPage.setPageSize(pageSize);
		dataPage.setPageNo(pageNo);
		
		PageResult<AdminRole> pageResult = null;
		try {
			pageResult = adminRoleService.queryPage(dataPage, qObj);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		
		return this.assemblePageResultMap(pageResult, pageSize, pageNo);
	}
	
	@RequiresPermissions(value = {"SYSTEM:ROLE:ADD", "SYSTEM:ROLE:EDIT"}, logical = Logical.OR)
	@RequestMapping(value="name-conflict", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkRoleNameConflict(
			@RequestParam String roleName,
			@RequestParam(required = false) String defaultRoleName) {
		
		ModelResult<AdminRole> modelResult = adminRoleService.query(roleName);
		if (modelResult.getModel() != null) {
			if (StringUtils.isNotBlank(defaultRoleName)) {
				if (StringUtils.equalsIgnoreCase(roleName, defaultRoleName)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}
	
	@UserLog("系统管理-角色管理-新增")
	@RequiresPermissions("SYSTEM:ROLE:ADD")
	@RequestMapping(value="add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(
			@ModelAttribute AdminRole role) {
		
		ModelResult<AdminRole> modelResult = adminRoleService.insert(role);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			
			// 操作 LOG
			AdminRole dbRole = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbRole.getId());
			joRemark.put("name", dbRole.getRoleName());
			this.logOperation(AdminOperType.SYSTEM_ROLE_ADD, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "新增角色异常!");
		}
		
		return map;
	}
	
	@RequiresPermissions("SYSTEM:ROLE:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> role(@PathVariable Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<AdminRole> modelResult = adminRoleService.query(id);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			map.put("data", modelResult.getModel());
		} else {
			map.put("success", false);
			map.put("msg", "获取角色信息失败!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-角色管理-编辑")
	@RequiresPermissions("SYSTEM:ROLE:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(
			@ModelAttribute AdminRole role) {
		
		ModelResult<AdminRole> modelResult = adminRoleService.update(role);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccessAndLogError()) {
			map.put("success", true);
			
			// 操作 LOG
			AdminRole dbRole = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbRole.getId());
			joRemark.put("name", dbRole.getRoleName());
			this.logOperation(AdminOperType.SYSTEM_ROLE_EDIT, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "更新角色信息异常!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-角色管理-禁用")
	@RequiresPermissions("SYSTEM:ROLE:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/disabled", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> diabled(@PathVariable Long id) {
		
		AdminRole role = new AdminRole();
		role.setId(id);
		role.setStatus(AdminRole.STATUS_DISABLED);
		
		ModelResult<AdminRole> modelResult = adminRoleService.update(role);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccessAndLogError()) {
			map.put("success", true);
			
			// 操作 LOG
			AdminRole dbRole = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbRole.getId());
			joRemark.put("name", dbRole.getRoleName());
			this.logOperation(AdminOperType.SYSTEM_ROLE_DISABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "禁用角色异常!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-用户管理-启用")
	@RequiresPermissions("SYSTEM:ROLE:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/enabled", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enabled(@PathVariable Long id) {
		
		AdminRole role = new AdminRole();
		role.setId(id);
		role.setStatus(AdminRole.STATUS_ENABLED);
		
		ModelResult<AdminRole> modelResult = adminRoleService.update(role);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccessAndLogError()) {
			map.put("success", true);
			
			// 操作 LOG
			AdminRole dbRole = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbRole.getId());
			joRemark.put("name", dbRole.getRoleName());
			this.logOperation(AdminOperType.SYSTEM_ROLE_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用角色异常!");
		}
		
		return map;
	}
	
	@RequiresPermissions("SYSTEM:ROLE:AUTH")
	@RequestMapping(value="{id:[1-9][0-9]*}/auth", method = RequestMethod.GET)
	public String auth(
			ModelMap modelMap, 
			@PathVariable Long id) {
		
		ModelResult<AdminRole> roleResult = adminRoleService.query(id);
		AdminRole role = roleResult.getModel();
		modelMap.put("role", role);
		
		return "system/role/role-auth";
	}
	
	@RequiresPermissions("SYSTEM:ROLE:AUTH")
	@RequestMapping(value="{id:[1-9][0-9]*}/auth-perms", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> authPerms(@PathVariable Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<List<AdminRolePerm>> modelResult = adminRolePermService.query(id);
		
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			map.put("data", modelResult.getModel());
		} else {
			map.put("success", false);
			map.put("msg", "获取角色权限失败!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-用户管理-授权")
	@RequiresPermissions("SYSTEM:ROLE:AUTH")
	@RequestMapping(value="{id:[1-9][0-9]*}/auth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auth(
			@PathVariable Long id,
			@RequestParam("perms[]") List<String> perms) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		BaseResult result = adminRolePermService.auth(id, perms);
		
		if (result.isSuccess()) {
			map.put("success", true);
			
			// 操作 LOG
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.SYSTEM_ROLE_AUTH, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "更新角色权限失败!");
		}
		
		return map;
	}
	
}
