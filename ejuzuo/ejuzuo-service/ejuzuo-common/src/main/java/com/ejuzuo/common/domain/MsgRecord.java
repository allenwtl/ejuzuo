package com.ejuzuo.common.domain;

import java.io.Serializable;
import java.util.Calendar;
/**
 * 系统消息
 */
public class MsgRecord implements Serializable {
	
	private static final long serialVersionUID = 5931909145971396576L;

	private Long id;
	
	private Integer memberId; // 关联用户ID

	// MsgType
	private Integer msgType; // 消息类型[０:系统消息　１:个人消息]

	private String title; // 标题

	private String content; // 内容

	private String url; // 跳转的url
	
	private Integer status; // 状态[0:无效，1:有效]

	private Calendar createdDate = Calendar.getInstance();
	
	private Calendar updatedDate;

	/**是否已读，返回给客户端，不存入库*/
	private Boolean read = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Calendar getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Calendar updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
}