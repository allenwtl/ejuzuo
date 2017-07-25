package com.ejuzuo.admin.support;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PaymentInfo implements Serializable {

	private String signKey;
	private String scheme;
	private String host;
	private String pathResubmitOrder;

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPathResubmitOrder() {
		return pathResubmitOrder;
	}

	public void setPathResubmitOrder(String pathResubmitOrder) {
		this.pathResubmitOrder = pathResubmitOrder;
	}

}
