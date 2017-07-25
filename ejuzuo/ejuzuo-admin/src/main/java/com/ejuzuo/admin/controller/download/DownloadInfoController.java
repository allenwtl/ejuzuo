package com.ejuzuo.admin.controller.download;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.service.DownloadInfoService;

@Controller
@RequestMapping("/download")
public class DownloadInfoController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DownloadInfoController.class);
	@Resource
	private DownloadInfoService downloadInfoService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "download/download-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<DownloadInfo> page,
			@ModelAttribute DownloadInfoOption qVo) {
		DataPage<DownloadInfo> dataPage = page.toDataPage();
		ModelResult<DataPage<DownloadInfo>> result = null;
		try {
			result = downloadInfoService.queryPage(qVo,dataPage);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (!result.isSuccess() || result.getModel() == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = result.getModel();
		return Page.returnPage(dataPage);
	}
}
