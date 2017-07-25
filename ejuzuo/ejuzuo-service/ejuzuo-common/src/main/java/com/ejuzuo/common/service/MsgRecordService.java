package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MsgRecord;

import java.util.List;

public interface MsgRecordService {
	
	/**
	 * 新增个人消息
	 */
	public BaseResult addPersonMsg(Integer memberId, String content);
	
	/**
	 * 查询消息列表
	 */
	public ModelResult<List<MsgRecord>> getMsgRecordNormal(Integer memberId,int offset,int limit);
	
	/**
	 * 更新消息reach,当用户点击消息详情的时候，异步触发这个方法
	 */
	public ModelResult<Boolean> updateMsgReach(Long msgid, Integer memberId);

	public ModelResult<MsgRecord> add(MsgRecord msg);
	
	public ModelResult<MsgRecord> update(MsgRecord msg);
	
	public ModelResult<MsgRecord> queryById(Long id);
	
	public PageResult<MsgRecord> queryPage(DataPage<MsgRecord> dataPage, MsgRecord queryVo);

	public ModelResult<Boolean> updateStatus(Long id, Integer status);

	//前端用户查询
	ModelResult<Integer> count();
	
}
