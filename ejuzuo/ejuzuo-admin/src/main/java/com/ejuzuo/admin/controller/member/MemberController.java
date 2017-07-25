package com.ejuzuo.admin.controller.member;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

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
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.MemberLockType;
import com.ejuzuo.common.option.MemberOption;
import com.ejuzuo.common.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Resource
	private MemberService memberService;
	@Resource(name = "newsDomainRes")
	private  String NEWS_DOMAIN_RES;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("domainImage", NEWS_DOMAIN_RES);
		return "member/member-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<Member> page,
			@ModelAttribute MemberOption qVo) {
		DataPage<Member> dataPage = page.toDataPage();
		PageResult<Member> pageResult = null;
		try {
			pageResult = memberService.queryPage(dataPage,qVo);
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
	
	@RequestMapping(value="{id:[1-9][0-9]*}/info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> infoBase(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		ModelResult<Member> modelResult = memberService.queryByMemberId(id);
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			Member member = modelResult.getModel();
			member.setPassword(null); // 不要把密码传到前端
			map.put("success", true);
			map.put("data", member);
		} else {
			map.put("success", false);
			map.put("msg", "获取客户信息失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/lock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> lock(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = memberService.update(id, MemberLockType.locked.getIndex(), null);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
		} else {
			map.put("success", false);
			map.put("msg", "锁定用户失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/unlock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unlock(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = memberService.update(id, MemberLockType.unLock.getIndex(), null);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
		} else {
			map.put("success", false);
			map.put("msg", "解锁用户失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = memberService.update(id, null, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
		} else {
			map.put("success", false);
			map.put("msg", "禁用用户失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = memberService.update(id, null, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
		} else {
			map.put("success", false);
			map.put("msg", "启用用户失败!");
		}
		
		return map;
	}
	
}
