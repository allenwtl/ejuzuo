package com.ejuzuo.common.option;

import java.io.Serializable;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public class MemberFavoriteOption implements Serializable {
    private static final long serialVersionUID = 984242623462849426L;

    private Integer memberId ;

    private Integer objectType;

    private Integer objectId;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

}
