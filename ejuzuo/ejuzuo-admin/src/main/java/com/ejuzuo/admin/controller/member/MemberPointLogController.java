package com.ejuzuo.admin.controller.member;

import java.util.List;
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

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.service.MemberPointLogService;

@Controller
@RequestMapping("/member/point/log")
public class MemberPointLogController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberPointLogController.class);
	
	@Resource
	private MemberPointLogService memberPointLogService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		List<MemberPointLogTransType> transTypes = MemberPointLogTransType.getAllList();
		modelMap.put("transTypes", transTypes);
		return "member/member-point-log-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberPointLog> page,
			@ModelAttribute MemberPointLogOption qVo) {
		DataPage<MemberPointLog> dataPage = page.toDataPage();
		ModelResult<DataPage<MemberPointLog>> modelResult = null;
		try {
			modelResult = memberPointLogService.queryByPage(qVo, dataPage);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (modelResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = modelResult.getModel();
		return Page.returnPage(dataPage);
	}
}
