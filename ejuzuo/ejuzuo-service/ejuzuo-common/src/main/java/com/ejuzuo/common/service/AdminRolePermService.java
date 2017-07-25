package com.ejuzuo.common.service;

import java.util.List;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.domain.AdminRolePerm;

public interface AdminRolePermService {

	/**
	 * 获取角色的权限
	 * @param roleId 角色ID
	 * @return
	 */
	ModelResult<List<AdminRolePerm>> query(Long roleId);
	
	/**
	 * 角色授权 , 先 delete 原有权限 , 再重新插入新的权限
	 * @param roleId 角色ID
	 * @param perms 权限
	 * @return
	 */
	BaseResult auth(Long roleId, List<String> perms);
	
}
