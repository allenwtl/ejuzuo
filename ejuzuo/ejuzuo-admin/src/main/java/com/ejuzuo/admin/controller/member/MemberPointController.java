package com.ejuzuo.admin.controller.member;

import java.util.HashMap;
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
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointOption;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MemberPointService;

@Controller
@RequestMapping("/member/point")
public class MemberPointController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberPointController.class);
	@Resource
	private MemberPointService memberPointService;
	@Resource
	private MemberPointLogService memberPointLogService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "member/member-point-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<MemberPoint> page,
			@ModelAttribute MemberPointOption qVo) {
		
		DataPage<MemberPoint> dataPage = page.toDataPage();
		
		PageResult<MemberPoint> pageResult = null;
		try {
			pageResult = memberPointService.queryPage(dataPage, qVo);
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
	public Map<String, Object> update(@ModelAttribute MemberPointLog obj){
		Map<String, Object> map = new HashMap<String, Object>();
		ModelResult<Integer> result = memberPointService.updateBalance(obj.getId(),obj.getMemberId(),obj.getAmount());
		if (result.isSuccess() && result.getModel() >= 1 ) { // 写入积分日志
			obj.setCreator(getUserAccount());
			obj.setTransType(MemberPointLogTransType.DEAL_ERROR.getIndex());
			ModelResult<MemberPoint> pointResult = memberPointService.selectById(obj.getId());
			obj.setPointBalance(pointResult.getModel().getBalance());
			obj.setId(null);
			memberPointLogService.save(obj);
			map.put("success", true);
			return map;
		}
		map.put("success", true);
		map.put("msg", "调整积分失败");
		return map;
	}
	
}
