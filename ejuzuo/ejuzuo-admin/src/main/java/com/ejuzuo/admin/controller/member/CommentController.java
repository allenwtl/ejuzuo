package com.ejuzuo.admin.controller.member;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Comment;
import com.ejuzuo.common.domain.type.CommentMask;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Resource
	private CommentService commentService;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index() {
		return "member/comment-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<Comment> page,
			@ModelAttribute CommentOption qVo) {
		DataPage<Comment> dataPage = page.toDataPage();
		ModelResult<DataPage<Comment>> pageResult = null;
		try {
			pageResult = commentService.queryPage(qVo,dataPage);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (!pageResult.isSuccess()) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = pageResult.getModel();
		return Page.returnPage(dataPage);
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/masked", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> lock(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = commentService.update(id, CommentMask.pingbi.getIndex(), null);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.COMMENT_MASKED, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "屏蔽失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/unmasked", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unlock(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = commentService.update(id, CommentMask.un_pingbi.getIndex(), null);
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.COMMENT_UNMASKED, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "解屏失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = commentService.update(id, null, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.COMMENT_DISABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "禁用失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enable(@PathVariable Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = commentService.update(id, null, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.COMMENT_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		return map;
	}
	
}
