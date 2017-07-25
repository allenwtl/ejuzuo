package com.ejuzuo.server.msg.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.MsgRecord;
import com.ejuzuo.common.domain.type.MsgType;
import com.ejuzuo.server.msg.manager.MsgRecordManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MsgRecordManagerImpl implements MsgRecordManager {

	@Autowired
	private GenericDao dao;
	
	public MsgRecord addPersonMsg(Integer memberId, String content) {
		MsgRecord msgRecord = new MsgRecord();
		msgRecord.setMemberId(memberId);
		msgRecord.setMsgType(MsgType.geren.getIndex());
		msgRecord.setContent(content);
		msgRecord.setStatus(1);
		msgRecord.setUpdatedDate(Calendar.getInstance());
		dao.insertAndReturnAffectedCount("MsgRecordMapper.insert", msgRecord);
		return msgRecord;
	}
		
	@Override
	public MsgRecord insert(MsgRecord msgRecord) {
		dao.insertAndReturnAffectedCount("MsgRecordMapper.insert", msgRecord);
		return msgRecord;
	}

	@Override
	public MsgRecord update(MsgRecord msgRecord) {
		dao.updateByObj("MsgRecordMapper.update", msgRecord);
		return msgRecord;
	}

	@Override
	public List<MsgRecord> queryNormal(Integer memberId,int offset, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("offset", offset);
		map.put("limit", limit);
		return dao.queryList("MsgRecordMapper.queryNormal", map);
	}

	@Override
	public DataPage<MsgRecord> queryPage(DataPage<MsgRecord> dataPage, MsgRecord qVo) {
		if (dataPage == null) {
			dataPage = new DataPage<>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		
		if (qVo != null) {
			if (qVo.getId() != null) {
				map.put("id", qVo.getId());
			}
			if (qVo.getTitle() != null) {
				map.put("title", "%"+qVo.getTitle()+"%");
			}
			if (qVo.getStatus() != null) {
				map.put("status", qVo.getStatus());
			}
		}
		
		return dao.queryPage("MsgRecordMapper.queryCount", "MsgRecordMapper.queryPage", map, dataPage);
	}

	@Override
	public MsgRecord queryById(Long id) {
		return dao.queryUnique("MsgRecordMapper.queryById", id);
	}

	@Override
	public ModelResult<Boolean> updateStatus(Long id, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		try {
			int affect = dao.update("MsgRecordMapper.updateStatus", map);
			if (affect > 0) {
				return new ModelResult<>(Boolean.TRUE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelResult<>().withError("exception", "更新报错");
		}
		return new ModelResult<>(Boolean.FALSE);
	}

	@Override
	public ModelResult<Integer> count() {
		Map<String,Object> param = new HashMap<>();
		return new ModelResult<>(dao.queryCount("MsgRecordMapper.queryCountUnion", param));
	}

}
