package com.ejuzuo.admin.controller.brand;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.service.BrandService;
import com.ejuzuo.common.service.DigitalFurnitureService;

@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
	@Resource
	private BrandService brandService;
	@Resource
	private DigitalFurnitureService digitalFurnitureService;
	@Resource(name = "newsDomainRes")
	private  String NEWS_DOMAIN_RES;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("domainImage", NEWS_DOMAIN_RES);
		return "brand/brand-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<Brand> page,
			@ModelAttribute Brand qVo) {
		DataPage<Brand> dataPage = page.toDataPage();
		PageResult<Brand> pageResult = null;
		try {
			pageResult = brandService.queryPage(dataPage,qVo);
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
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Brand brand){
		if (brand.getPreferred() == null) {
			brand.setPreferred(0);
		}
		brand.setUpdateTime(Calendar.getInstance());
		ModelResult<Brand> modelResult = brandService.save(brand);
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", modelResult.getModel().getId());
			this.logOperation(AdminOperType.BRAND_ADD, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "新增失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Brand brand){
		brand.setUpdateTime(Calendar.getInstance());
		ModelResult<Boolean> modelResult = brandService.updateById(brand);
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() == true) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", brand.getId());
			this.logOperation(AdminOperType.BRAND_EDIT, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "更新失败!");
		}
		return map;
	}
	
	@RequestMapping("/viewJson")
	@ResponseBody
	public Object viewJson(Integer id){
		ModelResult<Brand> modelResult = brandService.selectById(id);
		return modelResult.getModel();
	}
	
//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> delete(Integer id){
//		ModelResult<Integer> modelResult = brandService.delete(id);
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (modelResult.isSuccess() && modelResult.getModel() != null) {
//			map.put("success", true);
//			map.put("success", true);
//			JSONObject joRemark = new JSONObject();
//			joRemark.put("id", id);
//			this.logOperation(AdminOperType.BRAND_DELETE, joRemark);
//		}else {
//			map.put("success", false);
//			map.put("msg", "删除失败!");
//		}
//		return map;
//	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = brandService.updateStatus(id, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.BRAND_DISABLE, joRemark);
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
		
		ModelResult<Boolean> modelResult = brandService.updateStatus(id, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.BRAND_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value = "/changePreferred", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changePreferred(Integer id, Integer value){
		Map<String, Object> map = new HashMap<String, Object>();
		Brand brand = new Brand();
		brand.setId(id);
		brand.setPreferred(value);
		ModelResult<Boolean> modelResult = brandService.updateById(brand);
		if (modelResult.isSuccess()) {
			digitalFurnitureService.deleteMemcachedLastTimeAndPayBrand(id);
			map.put("success", true);
		}else {
			map.put("success", false);
			map.put("msg", "设置失败!");
		}
		return map;
	}
	
}
