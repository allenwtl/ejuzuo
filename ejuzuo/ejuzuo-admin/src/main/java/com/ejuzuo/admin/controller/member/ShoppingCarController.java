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
import com.ejuzuo.common.service.ShoppingCarService;
import com.ejuzuo.common.vo.ShoppingCartVO;

@Controller
@RequestMapping("/member/shoppingcar")
public class ShoppingCarController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCarController.class);
	@Resource
	private ShoppingCarService shoppingCarService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "member/shoppingcar-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<ShoppingCartVO> page,Integer memberId) {
		DataPage<ShoppingCartVO> dataPage = page.toDataPage();
		ModelResult<DataPage<ShoppingCartVO>> modelResult = null;
		try {
			modelResult = shoppingCarService.queryPage(memberId, dataPage);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (!modelResult.isSuccess()) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = modelResult.getModel();
		return Page.returnPage(dataPage);
	}
	
}
