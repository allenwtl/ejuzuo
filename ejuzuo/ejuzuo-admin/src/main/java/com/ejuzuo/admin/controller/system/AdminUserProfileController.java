package com.ejuzuo.admin.controller.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.aop.UserLog;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.common.service.AdminUserService;

@Controller
@RequestMapping("/user-profile")
public class AdminUserProfileController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(AdminUserProfileController.class);
	
	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		
		Long id = this.getUserId();
		
		ModelResult<AdminUser> model = adminUserService.query(id);
		
		modelMap.put("user", model.getModel());
		
		return "system/user/user-profile";
	}
	
	@UserLog("用户-PROFILE-编辑")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(
			ModelMap model, 
			@RequestParam String nickname,
			@RequestParam String remark) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		AdminUser obj = new AdminUser();
		obj.setId(this.getUserId());
		obj.setNickname(nickname);
		obj.setRemark(remark);
		
		ModelResult<AdminUser> modelResult;
		try {
			modelResult = adminUserService.update(obj);
		} catch (Exception e) {
			logger.error("用户Profile 更新 网络异常", e);
			result.put("success", false);
			result.put("msg", "用户Profile 更新 网络异常");
			return result;
		}
		if (!modelResult.isSuccess()) {
			result.put("success", false);
			result.put("msg", modelResult.getErrorMsg());
			return result;
		}
		
		// update session
		session.setAttribute(SESSION_KEY_USER, modelResult.getModel());
		
		// 操作日志
		JSONObject joRemark = new JSONObject();
		joRemark.put("nickname", nickname);
		joRemark.put("remark", remark);
		this.logOperation(AdminOperType.EDIT_PROFILE, joRemark);
		
		result.put("success", true);
		return result;
	}
	
	@UserLog("用户-PROFILE-修改密码")
	@RequestMapping(value="change-password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changePassword(
			@RequestParam String oldPassword,
			@RequestParam String newPassword,
			@RequestParam String rePassword) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long id = this.getUserId();
		
		ModelResult<AdminUser> model = adminUserService.query(id);
		AdminUser dbUser = model.getModel();
		
		String hashedOldPwd = new Md5Hash(oldPassword, "bull", 3).toString();
		if (!StringUtils.endsWithIgnoreCase(hashedOldPwd, dbUser.getPassword())) {
			map.put("success", false);
			map.put("msg", "原密码不正确!");
			return map;
		}
		
		if (!StringUtils.endsWithIgnoreCase(newPassword, rePassword)) {
			map.put("success", false);
			map.put("msg", "确认密码不正确!");
			return map;
		}
		
		String hashedPwd = new Md5Hash(newPassword, "bull", 3).toString();
		
		AdminUser user = new AdminUser();
		user.setId(id);
		user.setPassword(hashedPwd);
		
		String userName = this.getUserName();
		user.setUpdatedUser(userName);
		user.setUpdatedDate(Calendar.getInstance());
		
		ModelResult<AdminUser> modelResult = adminUserService.update(user);
		if (modelResult.isSuccess()) {
			map.put("success", true);
			
			// 操作日志
			this.logOperation(AdminOperType.EDIT_SELF_PWD, null);
		} else {
			map.put("success", false);
			map.put("msg", "修改密码异常!");
		}
		
		return map;
	}
	
}
