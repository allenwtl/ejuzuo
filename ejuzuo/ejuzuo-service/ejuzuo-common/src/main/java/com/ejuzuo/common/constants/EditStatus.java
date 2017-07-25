package com.ejuzuo.common.constants;

import com.ejuzuo.common.BaseType;

public class EditStatus extends BaseType {
	
	private static final long serialVersionUID = 1923689381411036384L;
	public static EditStatus SAVE = new EditStatus(0, "暂存");
	public static EditStatus PUBLISHED = new EditStatus(1, "发布");	
	

	private EditStatus() {
		super(null, null);
	}

	private EditStatus(int index, String description) {
		super(index, description);
	}

}
