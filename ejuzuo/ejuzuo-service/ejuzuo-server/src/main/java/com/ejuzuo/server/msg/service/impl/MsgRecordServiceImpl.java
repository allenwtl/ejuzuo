package com.ejuzuo.server.msg.service.impl;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MsgReach;
import com.ejuzuo.common.domain.MsgRecord;
import com.ejuzuo.common.domain.type.MsgType;
import com.ejuzuo.common.service.MsgRecordService;
import com.ejuzuo.server.msg.manager.MsgReachManager;
import com.ejuzuo.server.msg.manager.MsgRecordManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service("msgRecordService")
public class MsgRecordServiceImpl implements MsgRecordService {
	private static transient Logger logger = LoggerFactory.getLogger(MsgRecordServiceImpl.class);

	@Resource
	private MsgReachManager msgReachManager;
	@Resource
	private MsgRecordManager msgRecordManager;
	
	@Override
	public ModelResult<List<MsgRecord>> getMsgRecordNormal(Integer memberId, int offset, int limit) {
		ModelResult<List<MsgRecord>> result = new ModelResult<List<MsgRecord>>();
		if (memberId == null) {
			result.withError("memberId can not be null","memberId can not be null");
			return result;
		}
		List<MsgRecord> list = msgRecordManager.queryNormal(memberId,offset,limit);
		/** 根据msgid+memberId查询msgReach，判断是否已读*/
		List<Long> msgIdList = new ArrayList<Long>();
		for (MsgRecord msgRecord : list) {
			msgIdList.add(msgRecord.getId());
		}
		List<MsgReach> reachList = msgReachManager.queryByMsgIds(memberId,msgIdList);
		for (MsgReach msgReach : reachList) {
			for (MsgRecord msgRecord : list) {
				if (msgReach.getMsgId().compareTo(msgRecord.getId()) == 0) {
					msgRecord.setRead(true);
				}
			}
		}
		result.setModel(list);
		return result;
	}

	@Override
	public ModelResult<Boolean> updateMsgReach(Long msgid, Integer memberId) {
		ModelResult<Boolean> result = new ModelResult<Boolean>();
		MsgReach msgReach = new MsgReach();
		msgReach.setMemberId(memberId);
		msgReach.setMsgId(msgid);
		MsgRecord msgRecord = msgRecordManager.queryById(msgid);
		if (msgRecord == null) {
			result.withError("msgRecord.empty", "msgRecord.empty");
			return result;
		}
		msgReach.setRead(true);
		try {
			msgReachManager.insert(msgReach);
		} catch (Exception e) {
			logger.info("插入数据库出错:msg:{}",e.getMessage());
		}
		result.setModel(true);
		return result;
	}

	@Override
	public ModelResult<MsgRecord> add(MsgRecord msg) {
		ModelResult<MsgRecord> result = new ModelResult<MsgRecord>();
		MsgRecord obj = msgRecordManager.insert(msg);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<MsgRecord> update(MsgRecord msg) {
		MsgRecord obj =	msgRecordManager.update(msg);
		ModelResult<MsgRecord> result = new ModelResult<MsgRecord>();
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<MsgRecord> queryById(Long id) {
		ModelResult<MsgRecord> result = new ModelResult<MsgRecord>();
		MsgRecord obj = msgRecordManager.queryById(id);
		result.setModel(obj);
		return result;
	}

	@Override
	public PageResult<MsgRecord> queryPage(DataPage<MsgRecord> dataPage, MsgRecord queryVo) {
		PageResult<MsgRecord> result = new PageResult<>();
		dataPage = msgRecordManager.queryPage(dataPage, queryVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<Boolean> updateStatus(Long id, Integer status) {
		return msgRecordManager.updateStatus(id,status);
	}

	@Override
	public ModelResult<Integer> count() {
		return msgRecordManager.count();
	}

	@Override
	public BaseResult addPersonMsg(Integer memberId, String content) {
		BaseResult result = new BaseResult();
		if (memberId == null) {
			return result.withError("memberId is null", "memberId is null");
		}
//		MsgRecord msgRecord = new MsgRecord();
//		msgRecord.setMemberId(memberId);
//		msgRecord.setMsgType(MsgType.geren.getIndex());
//		msgRecord.setContent(content);
//		msgRecord.setStatus(1);
//		msgRecord.setUpdatedDate(Calendar.getInstance());
		msgRecordManager.addPersonMsg(memberId, content);
		return result;
	}

}
