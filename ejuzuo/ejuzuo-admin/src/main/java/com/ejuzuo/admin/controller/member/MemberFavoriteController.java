package com.ejuzuo.admin.controller.member;

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
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.service.MemberFavoriteService;

@Controller
@RequestMapping("/member/favorite")
public class MemberFavoriteController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberFavoriteController.class);
	@Resource
	private MemberFavoriteService memberFavoriteService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "member/member-favorite-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberFavorite> page,
			@ModelAttribute MemberFavoriteOption qVo) {
		ModelResult<DataPage<MemberFavorite>> result = null;
		DataPage<MemberFavorite> dataPage = page.toDataPage();
		try {
			result = memberFavoriteService.queryByPage(qVo,dataPage);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (result == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = result.getModel();
		return Page.returnPage(dataPage);
	}
}
