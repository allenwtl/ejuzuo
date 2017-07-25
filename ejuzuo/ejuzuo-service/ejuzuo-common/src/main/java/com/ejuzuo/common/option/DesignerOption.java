package com.ejuzuo.common.option;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/8 0008.
 */
public class DesignerOption implements Serializable {
    private static final long serialVersionUID = -2429943905574514871L;

    private Integer id;
    
    private Integer memberId;
    
    private String name;

    /**
     * @see com.ejuzuo.common.domain.type.DesignerType
     */
    private List<Integer> designerTypeList;

    /**
     * @see com.ejuzuo.common.domain.type.DesignerStatus
     */
    private Integer status;

    //创建时间的开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar startTime ;

    //创建时间的结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endTime ;

    //是否被推到首页
    private Integer homed;

    public Integer getHomed() {
        return homed;
    }

    public void setHomed(Integer homed) {
        this.homed = homed;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

	public List<Integer> getDesignerTypeList() {
		return designerTypeList;
	}

	public void setDesignerTypeList(List<Integer> designerTypeList) {
		this.designerTypeList = designerTypeList;
	}

	public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
