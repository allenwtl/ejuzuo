package com.ejuzuo.admin.controller.system;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.aop.UserLog;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.AdminUserStatusConstant;
import com.ejuzuo.common.domain.AdminRole;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.common.service.AdminRoleService;
import com.ejuzuo.common.service.AdminUserService;

@Controller
@RequestMapping("/system/user")
public class AdminUserController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AdminRoleService adminRoleService;
	
	@RequiresPermissions("SYSTEM:USER:INFO")
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "system/user/user-index";
	}
	
	@RequiresPermissions("SYSTEM:USER:INFO")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@RequestParam(defaultValue = "15") Integer pageSize, 
			@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String account) {
		
		DataPage<AdminUser> dataPage = new DataPage<AdminUser>();
		dataPage.setPageSize(pageSize);
		dataPage.setPageNo(pageNo);
		
		AdminUser qObj = new AdminUser();
		qObj.setStatus(status);
		qObj.setName(name);
		qObj.setAccount(account);
		
		PageResult<AdminUser> pageResult = null;
		try {
			pageResult = adminUserService.queryPage(dataPage, qObj);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		
		return this.assemblePageResultMap(pageResult, pageSize, pageNo);
	}
	
	@RequiresPermissions(value = {"SYSTEM:USER:ADD", "SYSTEM:USER:EDIT"}, logical = Logical.OR)
	@RequestMapping(value = "role-all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> all(ModelMap modelMap) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<List<AdminRole>> modelResult = adminRoleService.queryAll();
		
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			
			List<AdminRole> roles = modelResult.getModel();
			int size = roles.size();
			
			// 角色分配中去除 超级管理员
			Subject subject = SecurityUtils.getSubject();
			if (!subject.hasRole("ADMIN:SUPER")) {
				for (int i = 0; i < size; i++) {
					String roleCode = roles.get(i).getRoleCode();
					if ("ADMIN:SUPER".equalsIgnoreCase(roleCode)) {
						roles.remove(i);
						break;
					}
				}
			}
			
			map.put("success", true);
			map.put("data", roles);
		} else {
			map.put("success", false);
			map.put("msg", "获取角色列表失败");
		}
		
		return map;
	}
	
	@RequiresPermissions("SYSTEM:USER:ADD")
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String add(ModelMap modelMap) {
		
		ModelResult<List<AdminRole>> modelResult = adminRoleService.queryAll();
		if (modelResult.getModel() != null) {
			modelMap.put("roles", modelResult.getModel());
		}
		
		return "system/user/user-add";
	}
	
	@RequiresPermissions("SYSTEM:USER:INFO")
	@RequestMapping(value="{id:[1-9][0-9]*}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> user(@PathVariable Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<AdminUser> modelResult = adminUserService.query(id);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			map.put("data", modelResult.getModel());
		} else {
			map.put("success", false);
			map.put("msg", "获取对应用户失败!");
		}
		
		return map;
	}
	
	@RequiresPermissions(value = {"SYSTEM:USER:ADD", "SYSTEM:USER:EDIT"}, logical = Logical.OR)
	@RequestMapping(value="account-conflict", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkAccountConflict(
			@RequestParam String account,
			@RequestParam(required = false) String defaultAccount) {
		
		ModelResult<AdminUser> modelResult = adminUserService.query(account);
		if (modelResult.getModel() != null) {
			if (StringUtils.isNotBlank(defaultAccount)) {
				if (StringUtils.equalsIgnoreCase(account, defaultAccount)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}
	
	@UserLog("系统管理-用户管理-新增")
	@RequiresPermissions("SYSTEM:USER:ADD")
	@RequestMapping(value="add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(AdminUser user, boolean addUserSeat) {

		String password = "";
		if (StringUtils.isNotBlank(user.getPassword())) {
			password = new Md5Hash(user.getPassword(), "bull", 3).toString();
		} else {
			password = new Md5Hash("111111", "bull", 3).toString();
		}
		user.setPassword(password);
		
		String userName = this.getUserName();
		user.setCreatedUser(userName);
		user.setUpdatedUser(userName);
		user.setCreatedDate(Calendar.getInstance());
		user.setUpdatedDate(Calendar.getInstance());
		ModelResult<AdminUser> modelResult = adminUserService.insert(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			
			// 操作 LOG
			AdminUser dbUser = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbUser.getId());
			joRemark.put("account", dbUser.getAccount());
			joRemark.put("name", dbUser.getName());
			this.logOperation(AdminOperType.SYSTEM_USER_ADD, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "新增用户异常!");
		}
		
		return map;
	}
	
	@RequiresPermissions("SYSTEM:USER:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/edit", method = RequestMethod.GET)
	public String edit(ModelMap modelMap, @PathVariable Long id) {
		
		ModelResult<AdminUser> modelResult = adminUserService.query(id);
		modelMap.put("user", modelResult.getModel());
		
		ModelResult<List<AdminRole>> roleResult = adminRoleService.queryAll();
		if (roleResult.getModel() != null) {
			modelMap.put("roles", roleResult.getModel());
		}
		
		return "system/user/user-edit";
	}
	
	@UserLog("系统管理-用户管理-编辑")
	@RequiresPermissions("SYSTEM:USER:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(AdminUser user, boolean addUserSeat) {
		
		String userName = this.getUserName();
		user.setCreatedUser(userName);
		user.setUpdatedUser(userName);
		user.setUpdatedDate(Calendar.getInstance());
		
		ModelResult<AdminUser> modelResult = adminUserService.update(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			
			// 操作 LOG
			AdminUser dbUser = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbUser.getId());
			joRemark.put("account", dbUser.getAccount());
			joRemark.put("name", dbUser.getName());
			this.logOperation(AdminOperType.SYSTEM_USER_EDIT, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "更新用户异常!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-用户管理-修改密码")
	@RequiresPermissions("SYSTEM:USER:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/change-password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changePassword(
			@PathVariable Long id,
			@RequestParam String password) {
		
		String hashedPwd = new Md5Hash(password, "bull", 3).toString();
		
		AdminUser user = new AdminUser();
		user.setId(id);
		user.setPassword(hashedPwd);
		
		String userName = this.getUserName();
		user.setUpdatedUser(userName);
		user.setUpdatedDate(Calendar.getInstance());
		
		ModelResult<AdminUser> modelResult = adminUserService.update(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			
			// 操作 LOG
			AdminUser dbUser = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbUser.getId());
			joRemark.put("account", dbUser.getAccount());
			joRemark.put("name", dbUser.getName());
			this.logOperation(AdminOperType.SYSTEM_USER_EDIT_PWD, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "更新用户密码异常!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-用户管理-禁用")
	@RequiresPermissions("SYSTEM:USER:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/disabled", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> diabled(@PathVariable Long id) {
		
		AdminUser user = new AdminUser();
		user.setId(id);
		user.setStatus(AdminUserStatusConstant.DISABLED);
		
		String userName = this.getUserName();
		user.setUpdatedUser(userName);
		user.setUpdatedDate(Calendar.getInstance());
		
		ModelResult<AdminUser> modelResult = adminUserService.update(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			
			// 操作 LOG
			AdminUser dbUser = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbUser.getId());
			joRemark.put("account", dbUser.getAccount());
			joRemark.put("name", dbUser.getName());
			this.logOperation(AdminOperType.SYSTEM_USER_DISABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "禁用用户异常!");
		}
		
		return map;
	}
	
	@UserLog("系统管理-用户管理-启用")
	@RequiresPermissions("SYSTEM:USER:EDIT")
	@RequestMapping(value="{id:[1-9][0-9]*}/enabled", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enabled(@PathVariable Long id) {
		
		AdminUser user = new AdminUser();
		user.setId(id);
		user.setStatus(AdminUserStatusConstant.NORMAL);
		
		String userName = this.getUserName();
		user.setUpdatedUser(userName);
		user.setUpdatedDate(Calendar.getInstance());
		
		ModelResult<AdminUser> modelResult = adminUserService.update(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			
			// 操作 LOG
			AdminUser dbUser = modelResult.getModel();
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", dbUser.getId());
			joRemark.put("account", dbUser.getAccount());
			joRemark.put("name", dbUser.getName());
			this.logOperation(AdminOperType.SYSTEM_USER_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用用户异常!");
		}
		
		return map;
	}
	
}
