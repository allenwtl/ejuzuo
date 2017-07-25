package com.ejuzuo.admin.controller.sms;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.CheckCodeRecord;
import com.ejuzuo.common.option.CheckCodeRecordOption;
import com.ejuzuo.common.service.CheckCodeRecordService;

@Controller
@RequestMapping("/checkCode")
public class CheckCodeRecordController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CheckCodeRecordController.class);
	@Resource
	private CheckCodeRecordService checkCodeRecordService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "sms/checkCode-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<CheckCodeRecord> page,
			@ModelAttribute CheckCodeRecordOption qVo) {
		DataPage<CheckCodeRecord> dataPage = page.toDataPage();
		PageResult<CheckCodeRecord> pageResult = null;
		try {
			pageResult = checkCodeRecordService.queryPage(dataPage,qVo);
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
