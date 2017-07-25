package com.ejuzuo.admin.controller.activity;

import java.util.Map;

import javax.annotation.Resource;

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
import com.ejuzuo.common.domain.ActivityEnroll;
import com.ejuzuo.common.option.ActivityEnrollOption;
import com.ejuzuo.common.service.ActivityEnrollService;

/**
 * 用户报名活动信息
 */
@Controller
@RequestMapping("/activity/enroll")
public class ActivityEnrollController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityEnrollController.class);
	@Resource
	private ActivityEnrollService activityEnrollService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap,Integer activityId) {
		if (activityId != null) {
			modelMap.put("activityId", activityId);
		}
		return "activity/enroll/activityEnroll-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<ActivityEnroll> page,
			@ModelAttribute ActivityEnrollOption qVo) {
		DataPage<ActivityEnroll> dataPage = page.toDataPage();
		PageResult<ActivityEnroll> pageResult = null;
		try {
			pageResult = activityEnrollService.queryPage(dataPage,qVo);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = pageResult.getPage();
		return Page.returnPage(dataPage);
	}
}
