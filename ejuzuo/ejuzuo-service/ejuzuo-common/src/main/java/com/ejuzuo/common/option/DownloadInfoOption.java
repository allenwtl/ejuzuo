package com.ejuzuo.common.option;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by allen on 2016/4/2.
 */
public class DownloadInfoOption implements Serializable {

	private static final long serialVersionUID = 5484900082450468303L;

	private Integer id;

	private Long memberId;

	private Integer fileId;

	private Integer goodsId;

	private Integer status;

	private Integer payStatus;

	private Calendar nowTime;

	private Calendar payBeginTime;

	private Calendar payEndTime;

	public Calendar getPayBeginTime() {
		return payBeginTime;
	}

	public void setPayBeginTime(Calendar payBeginTime) {
		this.payBeginTime = payBeginTime;
	}

	public Calendar getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Calendar payEndTime) {
		this.payEndTime = payEndTime;
	}

	public Calendar getNowTime() {
		return nowTime;
	}

	public void setNowTime(Calendar nowTime) {
		this.nowTime = nowTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
}
