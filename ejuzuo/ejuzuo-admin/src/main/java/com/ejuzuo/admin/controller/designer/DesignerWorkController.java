package com.ejuzuo.admin.controller.designer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.service.DesignerWorkService;

@Controller
@RequestMapping("/designer/work")
public class DesignerWorkController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(DesignerController.class);
	@Resource
	private DesignerWorkService designerWorkService;
	@Resource
	private CodeValueService codeValueService;
	@Resource(name = "newsDomainRes")
	private  String DOMAIN_RES;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(Model model) {
		ModelResult<List<CodeValue>> result = codeValueService.queryList(CollectionCode.fengge, null, null);
		model.addAttribute("styles", JSON.toJSONString(result.getModel()));
		return "designer/work/designer-work-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<DesignerWork> page,
			@ModelAttribute DesignerWorkOption qVo) {
		DataPage<DesignerWork> dataPage = page.toDataPage();
		PageResult<DesignerWork> pageResult = null;
		try {
			pageResult = designerWorkService.queryPage(dataPage,qVo);
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
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, ModelMap modelMap) {
		ModelResult<DesignerWork> result = designerWorkService.queryById(id,null);
		DesignerWork designerWork = result.getModel();
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge, null, null);
		modelMap.put("styles", JSON.toJSONString(styleResult.getModel()));
		modelMap.put("designerWork", designerWork);
		modelMap.put("domainImage", DOMAIN_RES);
		return "designer/work/designer-work-detail";
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ModelResult<Boolean> modelResult = designerWorkService.updateStatus(id, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.DESIGNERWORK_DISABLE, joRemark);
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
		
		ModelResult<Boolean> modelResult = designerWorkService.updateStatus(id, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.DESIGNERWORK_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		
		return map;
	}
	
}
