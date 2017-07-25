package com.ejuzuo.admin.controller.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.service.ActivityInfoService;
import com.ejuzuo.common.service.CodeValueService;

@Controller
@RequestMapping("/activity")
public class ActivityInfoController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ActivityInfoController.class);
	@Resource
	private ActivityInfoService activityInfoService;
	@Resource
	private CodeValueService codeValueService;
	@Resource(name = "newsDomainRes")
	private  String NEWS_DOMAIN_RES;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(Model model) {
		ModelResult<List<CodeValue>> result = codeValueService.queryList(CollectionCode.remenchengshi, null, null);
		model.addAttribute("citys", result.getModel());
		return "activity/activity-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<ActivityInfo> page,
			@ModelAttribute ActivityInfoOption option) {
		DataPage<ActivityInfo> dataPage = page.toDataPage();
		PageResult<ActivityInfo> pageResult = null;
		try {
			pageResult = activityInfoService.queryPage(dataPage,option);
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
	
	@RequestMapping(value = "/getActivityCode", method = RequestMethod.GET)
	@ResponseBody
	private List<Map<String, Object>> getActivityCode(){
		ModelResult<List<CodeValue>> result = codeValueService.queryList(CollectionCode.huodongleibei,null,null);
		List<CodeValue> codeValues = result.getModel();
		List<Map<String, Object>> lstTree = new ArrayList<Map<String, Object>>();
		for (CodeValue codeValue : codeValues) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", codeValue.getValueCode());
			map.put("pId", StringUtils.isBlank(codeValue.getParentCode())?0:codeValue.getParentCode());
			map.put("name", codeValue.getValueName());
			lstTree.add(map);
		}
		//插入根目录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 0);
		map.put("name","活动类别");
		map.put("isParent", true);
		map.put("open", true);
		lstTree.add(map);
		return lstTree;
	}
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String add(ModelMap modelMap,String category) {
		ModelResult<List<CodeValue>> result = codeValueService.queryList(CollectionCode.huodongleibei, category, null);
		ModelResult<List<CodeValue>> cityResult = codeValueService.queryList(CollectionCode.remenchengshi, null, null);
		modelMap.put("codeValue", result.getModel().get(0));
		modelMap.put("citys", cityResult.getModel());
		modelMap.put("domainNewsImage", NEWS_DOMAIN_RES);
		return "activity/activity-add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, 
			@ModelAttribute ActivityInfo obj, 
			@RequestParam(required = false) String content){
		obj.setPublisher(this.getUserName());
		obj.setPublishTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		obj.setEditStatus(0);
		ModelResult<ActivityInfo> result = activityInfoService.add(obj, content);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess()) {
			map.put("success", true);
			
			// 操作 LOG
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", result.getModel().getId());
			this.logOperation(AdminOperType.ACTIVITY_ADD, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "新增失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, ModelMap modelMap) {
		ModelResult<ActivityInfo> activityInfoResult = activityInfoService.queryWithContent(id);
		ModelResult<List<CodeValue>> categoryResult = codeValueService.queryList(CollectionCode.huodongleibei,null,null);
		ModelResult<List<CodeValue>> cityResult = codeValueService.queryList(CollectionCode.remenchengshi, null, null);
		
		modelMap.put("activityInfo", activityInfoResult.getModel());
		modelMap.put("domainNewsImage", NEWS_DOMAIN_RES);
		modelMap.put("categorys", categoryResult.getModel());
		modelMap.put("citys", cityResult.getModel());
		return "activity/activity-contents-detail";
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, ModelMap modelMap) {
		ModelResult<ActivityInfo> activityInfoResult = activityInfoService.queryWithContent(id);
		ModelResult<List<CodeValue>> categoryResult = codeValueService.queryList(CollectionCode.huodongleibei,null,null);
		ModelResult<List<CodeValue>> cityResult = codeValueService.queryList(CollectionCode.remenchengshi, null, null);
		modelMap.put("activityInfo", activityInfoResult.getModel());
		modelMap.put("domainImage", NEWS_DOMAIN_RES);
		modelMap.put("categorys", categoryResult.getModel());
		modelMap.put("citys", cityResult.getModel());
		return "activity/activity-contents-edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request, 
			@ModelAttribute ActivityInfo obj){
		obj.setPublisher(this.getUserName());
		obj.setPublishTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		BaseResult result = activityInfoService.updateWithContent(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess()) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", obj.getId());
			this.logOperation(AdminOperType.ACTIVITY_EDIT, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "编辑失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> release(HttpServletRequest request, 
			@ModelAttribute ActivityInfo obj){
		obj.setEditStatus(EditStatus.PUBLISHED.getIndex());
		obj.setPublisher(this.getUserName());
		obj.setPublishTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		BaseResult result = activityInfoService.updateWithContent(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess()) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", obj.getId());
			this.logOperation(AdminOperType.ACTIVITY_RELEASE, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "发布失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = activityInfoService.updateStatus(id, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.ACTIVITY_DISABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "禁用失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = activityInfoService.updateStatus(id, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.ACTIVITY_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		
		return map;
	}
}
