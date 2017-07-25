package com.ejuzuo.admin.controller.promoRecord;

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
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.option.PromoRecordOption;
import com.ejuzuo.common.service.PromoRecordService;

@Controller
@RequestMapping("/member/promoRecord")
public class PromoRecordController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PromoRecordController.class);
	@Resource
	private PromoRecordService promoRecordService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "promoRecord/promoRecord-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<PromoRecord> page,
			@ModelAttribute PromoRecordOption qVo) {
		DataPage<PromoRecord> dataPage = page.toDataPage();
		PageResult<PromoRecord> pageResult = null;
		try {
			pageResult = promoRecordService.queryPage(dataPage,qVo);
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
