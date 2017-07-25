package com.ejuzuo.admin.controller.digitalFurniture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.type.DigitalShelfStatus;
import com.ejuzuo.common.service.BrandService;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.service.DigitalFurnitureService;

@Controller
@RequestMapping("/digital_furniture")
public class DigitalFurnitureController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(DigitalFurnitureController.class);
	@Resource
	private DigitalFurnitureService digitalFurnitureService;
	@Resource
	private BrandService brandService;
	@Resource
	private CodeValueService codeValueService;
	@Resource(name = "newsDomainRes")
	private  String NEWS_DOMAIN_RES;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(Model model) {
		ModelResult<List<CodeValue>> spaceCategoryResult = codeValueService.queryList(CollectionCode.kongjian, null, null);
		ModelResult<List<CodeValue>> spaceSmallCategoryResult = codeValueService.queryList(CollectionCode.kongjianxiaolei,null,null);
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge,null,null);
		ModelResult<List<Brand>> brandResult = brandService.queryAllNomal();
		model.addAttribute("spaceCategorys", JSON.toJSONString(spaceCategoryResult.getModel()));
		model.addAttribute("spaceSmallCategorys", JSON.toJSONString(spaceSmallCategoryResult.getModel()));
		model.addAttribute("styles", JSON.toJSONString(styleResult.getModel()));
		model.addAttribute("brands", JSON.toJSONString(brandResult.getModel()));
		return "digitalFurniture/digitalFurniture-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<DigitalFurniture> page,
			@ModelAttribute DigitalFurniture qVo) {
		DataPage<DigitalFurniture> dataPage = page.toDataPage();
		PageResult<DigitalFurniture> pageResult = null;
		try {
			pageResult = digitalFurnitureService.queryPage1(dataPage,qVo);
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
	
	
	/**获取品牌类别*/
	@RequestMapping(value = "/getBrand", method = RequestMethod.GET)
	@ResponseBody
	private List<Map<String, Object>> getBrand(){
		ModelResult<List<Brand>> result = brandService.queryAllNomal();
		List<Brand> brands = result.getModel();
		List<Map<String, Object>> lstTree = new ArrayList<Map<String, Object>>();
		for (Brand brand : brands) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", brand.getId());
			map.put("pId", 0);
			map.put("name", brand.getName());
			lstTree.add(map);
		}
		//插入根目录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 0);
		map.put("name","品牌");
		map.put("isParent", true);
		map.put("open", true);
		lstTree.add(map);
		return lstTree;
	}
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String add(ModelMap modelMap,Integer brandId) {
		ModelResult<Brand> brandResult = brandService.selectById(brandId);
		ModelResult<List<CodeValue>> spaceCategoryResult = codeValueService.queryList(CollectionCode.kongjian, null, null);
		ModelResult<List<CodeValue>> spaceSmallCategoryResult = codeValueService.queryList(CollectionCode.kongjianxiaolei,null,null);
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge,null,null);
		modelMap.addAttribute("spaceCategorys", spaceCategoryResult.getModel());
		modelMap.addAttribute("spaceSmallCategorys", JSON.toJSONString(spaceSmallCategoryResult.getModel()));
		modelMap.addAttribute("styles", styleResult.getModel());
		modelMap.addAttribute("brand", brandResult.getModel());
		modelMap.put("domainImage", NEWS_DOMAIN_RES);
		return "digitalFurniture/digitalFurniture-add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,@ModelAttribute DigitalFurniture obj){
		obj.setCreator(this.getUserAccount());
		obj.setUpdateTime(Calendar.getInstance());
		obj.setShelfTime(Calendar.getInstance());//新增上架时间
		obj.setStatus(1);
		obj.setShelfStatus(0);
		ModelResult<DigitalFurniture> result = digitalFurnitureService.save(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess() && result.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", result.getModel().getId());
			this.logOperation(AdminOperType.DIGITALFURNITURE_ADD, joRemark);
			taskExecutor.execute(()->brandService.updateCorporation(obj.getBrand(),obj.getCorporation()));
		}else {
			map.put("success", false);
			map.put("msg", "新增失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, ModelMap modelMap) {
		ModelResult<DigitalFurniture> furnitureResult = digitalFurnitureService.queryById(id);
		ModelResult<List<CodeValue>> spaceCategoryResult = codeValueService.queryList(CollectionCode.kongjian,null,null);
		ModelResult<List<CodeValue>> spaceSmallCategoryResult = codeValueService.queryList(CollectionCode.kongjianxiaolei,null,furnitureResult.getModel().getSpaceCategory());
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge,null,null);
		ModelResult<List<Brand>> brandResult = brandService.queryAllNomal();
		modelMap.put("spaceCategorys", spaceCategoryResult.getModel());
		modelMap.put("spaceSmallCategorys", spaceSmallCategoryResult.getModel());
		modelMap.put("styles", styleResult.getModel());
		modelMap.put("brands", brandResult.getModel());
		modelMap.put("furniture", furnitureResult.getModel());
		modelMap.put("domainImage", NEWS_DOMAIN_RES);
		return "digitalFurniture/digitalFurniture-detail";
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/{shelfStatus:[1-9][0-9]*}/approve", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> approve(@PathVariable Integer id, @PathVariable Integer shelfStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		ModelResult<Boolean> result = digitalFurnitureService.updateStatus(id,shelfStatus,null);
		if (result.isSuccess() && result.getModel() == true) {
			map.put("success", true);
			taskExecutor.execute(()->{
				digitalFurnitureService.deleteMemcachedLastTimeAndPayBrand(digitalFurnitureService.queryById(id).getModel().getBrand());
			});
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			if (shelfStatus == DigitalShelfStatus.SALES.getIndex()) {
				this.logOperation(AdminOperType.DIGITALFURNITURE_SHELF, joRemark);
			}else if (shelfStatus == DigitalShelfStatus.DOWN_SALES.getIndex()) {
				this.logOperation(AdminOperType.DIGITALFURNITURE_UNSHELF, joRemark);
			}
		}else {
			map.put("success", false);
			map.put("msg", result.getErrorMsg());
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, ModelMap modelMap) {
		ModelResult<DigitalFurniture> furnitureResult = digitalFurnitureService.queryById(id);
		ModelResult<List<CodeValue>> spaceCategoryResult = codeValueService.queryList(CollectionCode.kongjian,null,null);
		ModelResult<List<CodeValue>> spaceSmallCategoryResult = codeValueService.queryList(CollectionCode.kongjianxiaolei,null,furnitureResult.getModel().getSpaceCategory());
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge,null,null);
		ModelResult<List<Brand>> brandResult = brandService.queryAllNomal();
		modelMap.put("spaceCategorys", spaceCategoryResult.getModel());
		modelMap.put("spaceSmallCategorys", spaceSmallCategoryResult.getModel());
		modelMap.put("styles", styleResult.getModel());
		modelMap.put("brands", brandResult.getModel());
		modelMap.put("furniture", furnitureResult.getModel());
		modelMap.put("domainImage", NEWS_DOMAIN_RES);
		return "digitalFurniture/digitalFurniture-edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request, 
			@ModelAttribute DigitalFurniture obj){
		obj.setUpdateTime(Calendar.getInstance());
		Map<String, Object> map = new HashMap<String, Object>();
		ModelResult<DigitalFurniture> result = digitalFurnitureService.update(obj);
		if (result.isSuccess() && result.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", obj.getId());
			this.logOperation(AdminOperType.DIGITALFURNITURE_EDIT, joRemark);
			taskExecutor.execute(()->brandService.updateCorporation(obj.getBrand(),obj.getCorporation()));
		}else {
			map.put("success", false);
			map.put("msg", "编辑失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = digitalFurnitureService.updateStatus(id,null,Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() == true) {
			map.put("success", true);
			taskExecutor.execute(()->{
				ModelResult<DigitalFurniture> result = digitalFurnitureService.queryById(id);
				DigitalFurniture obj = result.getModel();
				digitalFurnitureService.deleteMemcachedLastTimeAndPayBrand(obj.getBrand());
			});
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.DIGITALFURNITURE_DISABLE, joRemark);
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
		
		ModelResult<Boolean> modelResult = digitalFurnitureService.updateStatus(id,null,Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() == true ) {
			map.put("success", true);
			taskExecutor.execute(()->{
				ModelResult<DigitalFurniture> result = digitalFurnitureService.queryById(id);
				DigitalFurniture obj = result.getModel();
				digitalFurnitureService.deleteMemcachedLastTimeAndPayBrand(obj.getBrand());
			});
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.DIGITALFURNITURE_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		return map;
	}
}
