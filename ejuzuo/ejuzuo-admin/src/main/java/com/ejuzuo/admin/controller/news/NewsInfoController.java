package com.ejuzuo.admin.controller.news;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.service.NewsInfoService;

@Controller
@RequestMapping("/news")
public class NewsInfoController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NewsInfoController.class);
	
	@Resource
	private NewsInfoService newsInfoService;
	@Resource(name = "newsDomainRes")
	private  String NEWS_DOMAIN_RES;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "news/news-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<NewsInfo> page,
			@ModelAttribute NewsInfoOption qVo) {
		DataPage<NewsInfo> dataPage = page.toDataPage();
		PageResult<NewsInfo> pageResult = null;
		try {
			pageResult = newsInfoService.queryPage(dataPage,qVo);
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
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String add(ModelMap modelMap) {
		modelMap.put("domainNewsImage", NEWS_DOMAIN_RES);
		return "news/news-contents-add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, 
			@ModelAttribute NewsInfo obj, 
			@RequestParam(required = false) String content){
		Map<String, Object> map = new HashMap<String, Object>();
		obj.setEditStatus(EditStatus.SAVE.getIndex());
		obj.setPublisher(this.getUserName());
		obj.setPublishTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		ModelResult<NewsInfo> result = newsInfoService.add(obj, content);
		if (result.isSuccess() && result.getModel() != null) {
			map.put("success", true);
			// 操作 LOG
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", result.getModel().getId());
			this.logOperation(AdminOperType.NEWS_ADD, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "新增失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/{option:detail|edit}", method = RequestMethod.GET)
	public String detailOrEdit(@PathVariable Integer id,@PathVariable String option, ModelMap modelMap) {
		ModelResult<NewsInfo> newsInfoResult = newsInfoService.queryWithContent(id);
		modelMap.put("newsInfo", newsInfoResult.getModel());
		modelMap.put("domainNewsImage", NEWS_DOMAIN_RES);
		return "news/news-contents-"+option;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request, 
			@ModelAttribute NewsInfo obj, 
			@RequestParam(required = false) String content){
		obj.setPublisher(this.getUserName());
		obj.setPublishTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		BaseResult result = newsInfoService.updateWithContent(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess()) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", obj.getId());
			this.logOperation(AdminOperType.NEWS_EDIT, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "编辑失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> release(HttpServletRequest request, 
			@ModelAttribute NewsInfo obj){
		obj.setEditStatus(EditStatus.PUBLISHED.getIndex());
		obj.setPublisher(this.getUserName());
		obj.setPublishTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		BaseResult result = newsInfoService.updateWithContent(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess()) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", obj.getId());
			this.logOperation(AdminOperType.NEWS_RELEASE, joRemark);
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
		
		ModelResult<Boolean> modelResult = newsInfoService.updateStatus(id, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.NEWS_DISABLE, joRemark);
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
		
		ModelResult<Boolean> modelResult = newsInfoService.updateStatus(id, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.NEWS_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		
		return map;
	}
	
}
