package com.ejuzuo.server.admin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.domain.AdminRolePerm;
import com.ejuzuo.common.service.AdminRolePermService;
import com.ejuzuo.server.admin.manager.AdminRolePermManager;

@Service("adminRolePermService")
public class AdminRolePermServiceImpl implements AdminRolePermService {

	private final static Logger logger = LoggerFactory
			.getLogger(AdminRolePermServiceImpl.class);

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AdminRolePermManager adminRolePermManager;

	@Override
	public ModelResult<List<AdminRolePerm>> query(Long roleId) {

		List<AdminRolePerm> list = adminRolePermManager.query(roleId);

		ModelResult<List<AdminRolePerm>> result = new ModelResult<List<AdminRolePerm>>();
		result.setModel(list);

		return result;
	}

	@Override
	public BaseResult auth(final Long roleId, final List<String> perms) {

		logger.info("角色权限 授权 [{}] 开始", roleId);

		BaseResult result = new BaseResult();

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(
						TransactionStatus status) {

					adminRolePermManager.delete(roleId);

					if (CollectionUtils.isNotEmpty(perms)) {
						for (String obj : perms) {
							AdminRolePerm arp = new AdminRolePerm();
							arp.setRoleId(roleId);
							arp.setPermCode(obj);
							adminRolePermManager.insert(arp);
						}
					}
				}

			});
		} catch (TransactionException e) {
			logger.error("角色权限 授权 [" + roleId + "] 异常", e);
			result.withError("UPDATE", "角色权限 授权 异常", "" + roleId);
			return result;
		}

		logger.info("角色权限 授权 [{}] 成功", roleId);
		return result;
	}

}
