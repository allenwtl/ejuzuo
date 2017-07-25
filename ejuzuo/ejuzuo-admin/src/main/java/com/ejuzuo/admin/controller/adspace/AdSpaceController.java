package com.ejuzuo.admin.controller.adspace;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.AdSpace;
import com.ejuzuo.common.domain.type.PageCodeType;
import com.ejuzuo.common.service.AdSpaceService;

@Controller
@RequestMapping("/adspace")
public class AdSpaceController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(AdSpaceController.class);
	
	@Resource(name = "adspacePath")
	private String PATH_IMAGE;	
	@Resource
	private AdSpaceService adSpaceService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "adspace/adspace-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<AdSpace> page,
			@ModelAttribute AdSpace qVo) {
		DataPage<AdSpace> dataPage = page.toDataPage();
		PageResult<AdSpace> pageResult = null;
		try {
			pageResult = adSpaceService.queryPage(dataPage,qVo);
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
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String adAddGet(ModelMap model) {
		List<PageCodeType> list = PageCodeType.getAll(PageCodeType.class);
		model.put("pageCodeTypes", list);
		return "adspace/adspace-add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adAddPost(@ModelAttribute AdSpace obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(obj.getContent())) {
				String filePath = null;
				String fileName = new DateTime().toString("yyyyMMddHHmmssSSS");
				filePath = PATH_IMAGE + File.separator + fileName.trim() + ".html";
				this.create(obj.getContent(), filePath);
				obj.setFilePath(filePath);
			}
			obj.setStatus(Status.STATUS.getIndex());
			ModelResult<Integer> result = adSpaceService.add(obj);
			if (result.isSuccess() && result.getModel() >= 1) {
				map.put("success", true);
			}else {
				map.put("success", false);
				map.put("msg", "新增失败");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "新增异常");
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/{option:detail|edit}", method = RequestMethod.GET)
	public String adDetailEditTask(ModelMap model, @PathVariable Integer id, @PathVariable String option) {
		
		ModelResult<AdSpace> modelResult = null;
		try {
			modelResult = adSpaceService.queryById(id);
		} catch (Exception e) {
			logger.error(String.format("AdSpace get with net error %d", id), e);
		}
		model.put("adSpace", modelResult.getModel());
		List<PageCodeType> list = PageCodeType.getAll(PageCodeType.class);
		model.put("pageCodeTypes", list);
		return "adspace/adspace-"+option;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adEditPost(@ModelAttribute AdSpace obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(obj.getContent())) {
				String filePath = null;
				if (StringUtils.isNotBlank(obj.getFilePath())) {
					filePath = obj.getFilePath();
				}else {
					String fileName = new DateTime().toString("yyyyMMddHHmmssSSS");
					filePath = PATH_IMAGE + File.separator + fileName.trim() + ".html";
				}
				this.create(obj.getContent(), filePath);
				obj.setFilePath(filePath);
			}
			obj.setStatus(Status.STATUS.getIndex());
			ModelResult<Integer> result = adSpaceService.update(obj);
			if (result.isSuccess() && result.getModel() >= 1) {
				map.put("success", true);
			}else {
				map.put("success", false);
				map.put("msg", "更新失败");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "更新异常");
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/conf", method = RequestMethod.GET)
	public String adConfGet(ModelMap model, @PathVariable Integer id) {
		
		ModelResult<AdSpace> modelResult = null;
		try {
			modelResult = adSpaceService.queryById(id);
		} catch (Exception e) {
			logger.error(String.format("AdSpace get with net error %d", id), e);
		}
		model.put("adSpace", modelResult.getModel());
		return "adspace/adspace-config";
	}
	
	
	
	private boolean create(String content, String path) {
		File file = new File(path);
		file.getParentFile().mkdirs();	//创建目录
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			out.write(content);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			logger.error(String.format("生成文件异常!", path), e);
		}
		return false;
	}
}
