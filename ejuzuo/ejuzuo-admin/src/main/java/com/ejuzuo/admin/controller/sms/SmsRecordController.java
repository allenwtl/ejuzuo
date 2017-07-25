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
import com.ejuzuo.common.domain.SmsRecord;
import com.ejuzuo.common.option.SmsRecordOption;
import com.ejuzuo.common.service.SmsRecordService;

@Controller
@RequestMapping("/smsRecord")
public class SmsRecordController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(SmsRecordController.class);
	@Resource
	private SmsRecordService smsRecordService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "sms/smsRecord-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<SmsRecord> page,
			@ModelAttribute SmsRecordOption qVo) {
		DataPage<SmsRecord> dataPage = page.toDataPage();
		PageResult<SmsRecord> pageResult = null;
		try {
			pageResult = smsRecordService.queryPage(dataPage,qVo);
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
