package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;

public class MsgReach implements Serializable {
	
	private static final long serialVersionUID = -5884425858885386587L;

	private Long id;

    private Long msgId; // 消息ID

    private Integer memberId; // 对应的会员ID
    
    private Boolean read = true; // 是否已读

    private Calendar readTime = Calendar.getInstance(); // 阅读时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}
}