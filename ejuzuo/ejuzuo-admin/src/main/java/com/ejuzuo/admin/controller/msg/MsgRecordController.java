package com.ejuzuo.admin.controller.msg;

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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.MsgRecord;
import com.ejuzuo.common.domain.type.MsgType;
import com.ejuzuo.common.service.MsgRecordService;

@Controller
@RequestMapping("/msg/record")
public class MsgRecordController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(MsgRecordController.class);
	@Resource
	private MsgRecordService msgRecordService;
	
	@RequestMapping(value= "", method = RequestMethod.GET)
	public String index() {
		return "msg/msg-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page( @ModelAttribute Page<MsgRecord> page, @ModelAttribute MsgRecord qVo) {
		DataPage<MsgRecord> dataPage = page.toDataPage();
		PageResult<MsgRecord> pageResult = null;
		try {
			pageResult = msgRecordService.queryPage(dataPage, qVo);
		} catch (Exception e) {
			logger.error("Page net error", e);
			return Page.returnEmptyPage();
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = pageResult.getPage();
		return Page.returnPage(dataPage);
	}
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String add() {
		return "msg/msg-add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(MsgRecord msgRecord){
		System.out.println(JSON.toJSONString(msgRecord));
		if (msgRecord.getMsgType() == MsgType.xitong.getIndex()) {
			msgRecord.setMemberId(null);
		}
		ModelResult<MsgRecord> modelResult = msgRecordService.add(msgRecord);
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", modelResult.getModel().getId());
			this.logOperation(AdminOperType.MSG_ADD, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "新增失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/update", method = RequestMethod.GET)
	public String detailOrEdit(@PathVariable Long id, ModelMap modelMap) {
		ModelResult<MsgRecord> modelResult = msgRecordService.queryById(id);
		modelMap.put("msgRecord", modelResult.getModel());
		return "msg/msg-update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(MsgRecord msgRecord){
		logger.info("更新系统消息:{}",JSON.toJSONString(msgRecord));
		ModelResult<MsgRecord> modelResult = msgRecordService.update(msgRecord);
		Map<String, Object> map = new HashMap<String, Object>();
		if (modelResult.isSuccess() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", msgRecord.getId());
			this.logOperation(AdminOperType.MSG_EDIT, joRemark);
		}else {
			map.put("success", false);
			map.put("msg", "更新失败!");
		}
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(@PathVariable Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = msgRecordService.updateStatus(id, Status.UN_STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.MSG_DISABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "禁用失败!");
		}
		
		return map;
	}
	
	@RequestMapping(value="{id:[1-9][0-9]*}/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enable(@PathVariable Long id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModelResult<Boolean> modelResult = msgRecordService.updateStatus(id, Status.STATUS.getIndex());
		if (modelResult.isSuccessAndLogError() && modelResult.getModel() != null) {
			map.put("success", true);
			
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", id);
			this.logOperation(AdminOperType.MSG_ENABLE, joRemark);
		} else {
			map.put("success", false);
			map.put("msg", "启用失败!");
		}
		
		return map;
	}
}
