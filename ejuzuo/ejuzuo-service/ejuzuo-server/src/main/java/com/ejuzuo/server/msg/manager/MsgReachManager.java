package com.ejuzuo.server.msg.manager;

import java.util.List;

import com.ejuzuo.common.domain.MsgReach;

public interface MsgReachManager {

	public MsgReach insert(MsgReach msgReach);

	public List<MsgReach> queryByMsgIds(Integer memberId, List<Long> msgIdList);
}
