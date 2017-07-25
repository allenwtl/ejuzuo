package com.ejuzuo.server.msg.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.MsgReach;
import com.ejuzuo.server.msg.manager.MsgReachManager;

@Repository
public class MsgReachManagerImpl implements MsgReachManager {

	@Autowired
	private GenericDao dao;
	@Override
	public MsgReach insert(MsgReach msgReach) {
		dao.insertAndReturnAffectedCount("MsgReachMapper.insert", msgReach);
		return msgReach;
	}
	
	@Override
	public List<MsgReach> queryByMsgIds(Integer memberId, List<Long> msgIdList) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (memberId != null) {
			map.put("memberId", memberId);
		}
		if (msgIdList != null && msgIdList.size() > 0) {
			map.put("msgIdList", msgIdList);
		}
		return dao.queryList("MsgReachMapper.queryByMsgIds", map);
	}
}
