package com.ejuzuo.admin.controller.codevalue;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.memcachedclient.MemcachedClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.service.BrandService;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.util.MemcachedKeyUtil;

@Controller
@RequestMapping("/codevalue")
public class CodeValueController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CodeValueController.class);
	
	@Resource 
	private CodeValueService codeValueService;
	@Resource
	private BrandService brandService;
	
	@Resource(name="memcachedClient")
    public MemcachedClient memcachedClient ;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(Model model) {
		ModelResult<List<Brand>> result = brandService.queryAllNomal();
		model.addAttribute("brands", JSON.toJSONString(result.getModel()));
		return "codevalue/codevalue-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<CodeValue> page,
			@ModelAttribute CodeValue qVo) {
		DataPage<CodeValue> dataPage = page.toDataPage();
		PageResult<CodeValue> pageResult = null;
		try {
			pageResult = codeValueService.queryPage(dataPage, qVo);
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
	
	@RequestMapping(value = "/getCollectionCode", method = RequestMethod.GET)
	@ResponseBody
	private List<Map<String, Object>> getActivityCode(){
		List<CollectionCode> collectionCodes = CollectionCode.getAll(CollectionCode.class);
		List<Map<String, Object>> lstTree = new ArrayList<Map<String, Object>>();
		for (CollectionCode collectionCode : collectionCodes) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", collectionCode.getIndex());
			map.put("pId", 0);
			map.put("name", collectionCode.getDescription());
			lstTree.add(map);
		}
		//插入根目录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 0);
		map.put("name","集合类别");
		map.put("isParent", true);
		map.put("open", true);
		lstTree.add(map);
		return lstTree;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(CodeValue obj,HttpServletRequest request){
		if (obj.getCollectionCode().equals("10")) { // 如果是空间大类
			String[] promateBrands = request.getParameterValues("promote-brand");
			List<Map<String, Object>> extensionList = new ArrayList<Map<String,Object>>();
			for (String promateBrand : promateBrands) { // 推广品牌拼接成json
				if (StringUtils.isNotBlank(promateBrand)) {
					String[] strs = promateBrand.split(":");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", Integer.valueOf(strs[0]));
					map.put("img", strs[1]);
					extensionList.add(map);
				}
			}
			obj.setExtension(JSON.toJSONString(extensionList));
		}
		ModelResult<Boolean> modelResult = codeValueService.save(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() == true) {
			map.put("success", true);
			if (obj.getCollectionCode().equals("10") || obj.getCollectionCode().equals("20")) {
				memcachedClient.delete(MemcachedKeyUtil.ALL_GOODS_TYPE_LIST);
			}
			JSONObject joRemark = new JSONObject();
			joRemark.put("collectionCode", obj.getCollectionCode());
			joRemark.put("valueCode", obj.getValueCode());
			this.logOperation(AdminOperType.CODEVALUE_ADD, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "新增失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(CodeValue obj,HttpServletRequest request){
		if (obj.getCollectionCode().equals("10")) { // 如果是空间大类
			String[] promateBrands = request.getParameterValues("promote-brand");
			List<Map<String, Object>> extensionList = new ArrayList<Map<String,Object>>();
			for (String promateBrand : promateBrands) { // 推广品牌拼接成json
				if (StringUtils.isNotBlank(promateBrand)) {
					String[] strs = promateBrand.split(":");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", Integer.valueOf(strs[0]));
					map.put("img", strs[1]);
					extensionList.add(map);
				}
			}
			obj.setExtension(JSON.toJSONString(extensionList));
		}
		ModelResult<Boolean> modelResult = codeValueService.update(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() == true) {
			map.put("success", true);
			if (obj.getCollectionCode().equals("10") || obj.getCollectionCode().equals("20")) {
				memcachedClient.delete(MemcachedKeyUtil.ALL_GOODS_TYPE_LIST);
			}
			JSONObject joRemark = new JSONObject();
			joRemark.put("collectionCode", obj.getCollectionCode());
			joRemark.put("valueCode", obj.getValueCode());
			this.logOperation(AdminOperType.CODEVALUE_EDIT, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "更新失败!");
		}
		return map;
	}
	
	@RequestMapping("/viewJson")
	@ResponseBody
	public Object viewJson(String collectionCode, String valueCode){
		ModelResult<CodeValue> modelResult = codeValueService.query(collectionCode, valueCode);
		return modelResult.getModel();
	}
	
	@RequestMapping(value="{collectionCode}/{valueCode}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable String collectionCode, @PathVariable String valueCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = codeValueService.updateStatus(collectionCode, valueCode, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			if (collectionCode.equals("10") || collectionCode.equals("20")) {
				memcachedClient.delete(MemcachedKeyUtil.ALL_GOODS_TYPE_LIST);
			}
			JSONObject joRemark = new JSONObject();
			joRemark.put("collectionCode", collectionCode);
			joRemark.put("valueCode", valueCode);
			this.logOperation(AdminOperType.CODEVALUE_DISABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "禁用失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{collectionCode}/{valueCode}/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enable(@PathVariable String collectionCode, @PathVariable String valueCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = codeValueService.updateStatus(collectionCode, valueCode, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			if (collectionCode.equals("10") || collectionCode.equals("20")) {
				memcachedClient.delete(MemcachedKeyUtil.ALL_GOODS_TYPE_LIST);
			}
			JSONObject joRemark = new JSONObject();
			joRemark.put("collectionCode", collectionCode);
			joRemark.put("valueCode", valueCode);
			this.logOperation(AdminOperType.CODEVALUE_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		return map;
	}
}
