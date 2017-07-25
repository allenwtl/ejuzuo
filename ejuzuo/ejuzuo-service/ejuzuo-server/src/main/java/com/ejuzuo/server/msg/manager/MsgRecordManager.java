package com.ejuzuo.server.msg.manager;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MsgRecord;

import java.util.List;

public interface MsgRecordManager {
	
	public MsgRecord addPersonMsg(Integer memberId, String content);

	public MsgRecord insert(MsgRecord msgRecord);
	
	public MsgRecord update(MsgRecord msgRecord);

	public List<MsgRecord> queryNormal(Integer memberId, int offset, int limit);

	public DataPage<MsgRecord> queryPage(DataPage<MsgRecord> dataPage, MsgRecord queryVo);

	public MsgRecord queryById(Long id);

	public ModelResult<Boolean> updateStatus(Long id, Integer status);

	ModelResult<Integer> count();
	
}
