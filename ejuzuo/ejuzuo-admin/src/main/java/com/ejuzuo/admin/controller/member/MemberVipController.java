package com.ejuzuo.admin.controller.member;

import java.util.Calendar;
import java.util.HashMap;
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

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.domain.MemberVip;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.service.MemberVipLogService;
import com.ejuzuo.common.service.MemberVipService;

@Controller
@RequestMapping("/member/vip")
public class MemberVipController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberVipController.class);
	@Resource
	private MemberVipService memberVipService;
	@Resource
	private MemberVipLogService memberVipLogService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "member/member-vip-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberVip> page,
			@ModelAttribute MemberVip qVo) {
		DataPage<MemberVip> dataPage = page.toDataPage();
		PageResult<MemberVip> pageResult = null;
		try {
			pageResult = memberVipService.queryPage(dataPage,qVo);
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
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@ModelAttribute MemberVipLog obj){
		Map<String, Object> map = new HashMap<String, Object>();
		BaseResult result = memberVipService.update(obj.getId(),obj.getPeriod(),this.getUserAccount());
		if (result.isSuccess()) { // 写入VIP日志
			obj.setId(null);
			obj.setTransType(4);
			obj.setCreator(getUserAccount());
			obj.setCreateTime(Calendar.getInstance());
			obj.setRelatedType(0);
			taskExecutor.execute(() -> memberVipLogService.save(obj));
			map.put("success", true);
			return map;
		}
		map.put("success", true);
		map.put("msg", "调整积分失败");
		return map;
	}
	
}
