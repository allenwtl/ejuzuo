package com.ejuzuo.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.memcachedclient.MemcachedClient;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.common.domain.AdminUserOperLog;
import com.ejuzuo.common.service.AdminUserOperLogService;

public class BaseController {

	protected static final String SESSION_KEY_USER = "SESSION_USER";
	
	protected static final String REDIRECT_TO_404 = "redirect:/error/404";
	protected static final String REDIRECT_TO_500 = "redirect:/error/500";
	
	@Autowired
	protected HttpSession session;
	
	@Autowired
	protected TaskExecutor taskExecutor;
	
	@Autowired
    @Qualifier("ejuzuoAdminMemcachedClient")
	protected MemcachedClient memcachedClient;
	
	@Autowired
	protected AdminUserOperLogService adminUserOperLogService;
	
	protected Long getUserId() {
		
		AdminUser user = (AdminUser) session.getAttribute(SESSION_KEY_USER);
		
		if (user == null) {
			return null;
		}
		
		return user.getId();
		
	}
	
	protected String getUserName() {
		
		AdminUser user = (AdminUser) session.getAttribute(SESSION_KEY_USER);
		
		if (user == null) {
			return null;
		}
		
		return user.getName();
		
	}
	
	protected String getUserAccount() {
		
		AdminUser user = (AdminUser) session.getAttribute(SESSION_KEY_USER);
		
		if (user == null) {
			return null;
		}
		
		return user.getAccount();
		
	}
	
	protected DataPage<JSONObject> convertDataPage2JSONPage(DataPage<?> dataPage, List<JSONObject> ja) {
		
		if (dataPage == null || ja == null) {
			throw new IllegalArgumentException();
		}
		
		DataPage<JSONObject> result = new DataPage<JSONObject>();
		result.setDataList(ja);
		result.setPageNo(dataPage.getPageNo());
		result.setPageSize(dataPage.getPageSize());
		result.setTotalCount(dataPage.getTotalCount());
		result.setOrder(dataPage.getOrder());
		result.setOrderBy(dataPage.getOrderBy());
		return result;
	}
	
	/**
	 * 默认的分页组装器
	 * 
	 * @param pageResult
	 * @param pageSize
	 * @param pageNo
	 * @return {@code Map<String, Object>}
	 */
	protected Map<String, Object> assemblePageResultMap(PageResult<?> pageResult, Integer pageSize, Integer pageNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == pageResult || !pageResult.isSuccess()) {
			map.put("success", false);
			map.put("pageSize", pageSize);
			map.put("pageNo", pageNo);
			map.put("total", 0);
			map.put("totalPage", 1);
			map.put("list", null);
			return map;
		}
		
		map.put("success", true);
		map.put("pageSize", pageResult.getPage().getPageSize());
		map.put("pageNo", pageResult.getPage().getPageNo());
		map.put("total", pageResult.getPage().getTotalCount());
		map.put("totalPage", pageResult.getPage().getTotalPages() > 0 ? pageResult.getPage().getTotalPages() : 1);
		map.put("list", pageResult.getPage().getDataList());
		
		return map;
	}
	
	protected void logOperation(AdminOperType operType) {
		this.logOperation(operType, null);
	}
	
	protected void logOperation(AdminOperType operType, JSONObject remark) {
		
		String account = this.getUserAccount();
		
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				AdminUserOperLog obj = new AdminUserOperLog();
				obj.setAccount(account);
				obj.setOperType(operType);
				
				if (remark == null) {
					obj.setRemark(new JSONObject());
				} else {
					obj.setRemark(remark);
				}
				
				adminUserOperLogService.insert(obj);
			}
		});
	}
	
}
