package com.ejuzuo.admin.support.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.constants.AdminUserStatusConstant;
import com.ejuzuo.common.domain.AdminRole;
import com.ejuzuo.common.domain.AdminRolePerm;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.common.service.AdminRolePermService;
import com.ejuzuo.common.service.AdminRoleService;
import com.ejuzuo.common.service.AdminUserService;

public class ShiroRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AdminRoleService adminRoleServcie;
	@Autowired
	private AdminRolePermService adminRolePermServcie;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String account = String.valueOf(principals.getPrimaryPrincipal());
        
        ModelResult<AdminUser> model = adminUserService.query(account);
        final AdminUser user = model.getModel();
//        logger.info(user.toString());
        
        List<Long> roleIds = null;
        if (StringUtils.isNotBlank(user.getRoles())) {
        	roleIds = new ArrayList<Long>();
        	
        	String[] roleIdStrs = StringUtils.split(user.getRoles(), ",");
        	for (String id : roleIdStrs) {
        		try {
					long tmp = Long.parseUnsignedLong(id);
					roleIds.add(tmp);
				} catch (NumberFormatException e) {
					logger.error("角色ID["+id+"] 数值转换异常!", e);
				}
        	}
        }
        
        if (CollectionUtils.isEmpty(roleIds)) {
        	return authorizationInfo;
        }
        
        ModelResult<List<AdminRole>> modelResult = adminRoleServcie.query(roleIds);
        if (!modelResult.isSuccessAndLogError()) {
        	return authorizationInfo;
        }
        
        final List<AdminRole> roles = modelResult.getModel();
        if (CollectionUtils.isEmpty(roles)) {
        	return authorizationInfo;
        }
        
        for (AdminRole role : roles) {
        	
        	if (AdminRole.STATUS_DISABLED == role.getStatus()) {
        		continue;
        	}
        	
            // 添加角色
//        	logger.info(role.getRoleCode());
            authorizationInfo.addRole(role.getRoleCode());
            
            ModelResult<List<AdminRolePerm>> permResult = adminRolePermServcie.query(role.getId());
            if (!permResult.isSuccessAndLogError()) {
            	continue;
            }
            if (CollectionUtils.isEmpty(permResult.getModel())) {
            	continue;
            }
            
            final List<AdminRolePerm> perms = permResult.getModel();
            for (AdminRolePerm perm : perms) {
            	// 添加权限
//            	logger.info(perm.getPermCode());
                authorizationInfo.addStringPermission(perm.getPermCode());
            }
        }
        
        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		String username = String.valueOf(token.getPrincipal());
		String password = new String((char[]) token.getCredentials());
		
		// 通过数据库进行验证
		ModelResult<AdminUser> model;
		try {
			model = adminUserService.query(username, password);
		} catch (Exception e) {
			logger.error("账号校验异常", e);
			throw new AuthenticationException("账号校验异常, 请联系管理员.");
		}
		final AdminUser user = model.getModel();
		
		if (user == null) {
			throw new AuthenticationException("用户名或密码错误.");
		}
		
//		logger.info("test : " + user.toString());
		
		if (AdminUserStatusConstant.NORMAL != user.getStatus()) {
			
			if (AdminUserStatusConstant.DISABLED == user.getStatus()) {
				throw new AuthenticationException("账号已被禁用, 请联系管理员.");
			}else {
				throw new AuthenticationException("账号状态异常, 请联系管理员.");
			}
			
		}
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				username, password, getName());
		
		return authenticationInfo;
	}

}
