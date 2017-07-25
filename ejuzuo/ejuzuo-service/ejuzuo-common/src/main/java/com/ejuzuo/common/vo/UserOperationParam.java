package com.ejuzuo.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ejuzuo.common.constants.SellClient;

/**
 * 用户操作参数:不包含任何业务参数，只传递用户的操作时的IP，应用名称等用户操作相关的参数
 * 
 * @author yingfa.wang
 */
public class UserOperationParam implements Serializable {
	private static final long serialVersionUID = -1504838919617184353L;
	private String userIp = "127.0.0.1";
	private String userAgent;
	private String httpReferer;
	private SellClient sellClient = SellClient.WAP;
	
	// 外部传给opaque的操作参数
	private Map<String, Object> opaque = new HashMap<String, Object>();

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getHttpReferer() {
		return httpReferer;
	}

	public void setHttpReferer(String httpReferer) {
		this.httpReferer = httpReferer;
	}

	public Map<String, Object> getOpaque() {
		if (opaque == null) {
			opaque = new HashMap<String, Object>();
		}
		return opaque;
	}

	public void setOpaque(Map<String, Object> Opaque) {
		this.opaque = Opaque;
	}

	public void putOpaque(String key, Object value) {
		if (opaque == null) {
			opaque = new HashMap<String, Object>();
		}
		opaque.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getOpaque(String key) {
		if (opaque == null) {
			return null;
		}
		return (T) opaque.get(key);
	}

	public SellClient getSellClient() {
		return sellClient;
	}

	public void setSellClient(SellClient sellClient) {
		this.sellClient = sellClient;
	}

	@Override 
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
