package com.ejuzuo.checkcoderecord;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.BaseTest;
import com.ejuzuo.common.domain.CheckCodeRecord;
import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.domain.type.CheckCodeRecordStatus;
import com.ejuzuo.common.service.CheckCodeRecordService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.server.member.manager.CheckCodeRecordManager;

/**
 * Created by tianlun.wu on 2016/3/28 0028.
 */
public class TestCheckCodeRecord extends BaseTest {

    @Resource
    private CheckCodeRecordManager checkCodeRecordManager;
    @Resource
    private CheckCodeRecordService  checkCodeRecordService;
    
    @Test
	public void testSendSmsCheckCode(){
		BaseResult result = checkCodeRecordService.sendSmsCheckCode("13428981004", CheckCodeRecordCheckType.register, null, null);
		//Assert.assertTrue(result.isSuccess());	
		System.out.println(result.getErrorMsg());
    }
	

    public void testCheckSmsCheckCode(){    	
    	ModelResult<String> result = checkCodeRecordService.checkSmsCheckCode("946572", "13428981004", CheckCodeRecordCheckType.register, null);
    	Assert.assertNotNull(result.getModel());
    	System.out.println(result.getModel());
    }
    
   
	public void testSendEmailCheckCode(){
		BaseResult result = checkCodeRecordService.sendEmailCheckCode("77953087@qq.com", CheckCodeRecordCheckType.activiate, null, null);
		Assert.assertTrue(result.isSuccess());		
    }
	
	 @Test
    public void testCheckEmailCheckCode(){    	
    	ModelResult<String> result = checkCodeRecordService.checkEmailCheckCode("hkMlTUbXZ1DTfl1L", "77953087@qq.com", CheckCodeRecordCheckType.activiate, null);
    	Assert.assertNotNull(result.getModel());
    	System.out.println(result.getModel());
    }
    
        
	//@Test 
	public void testQuery() {
	
		//test查询数量
		int count = checkCodeRecordManager.countCheckCodeByCheckType("13428981004", CheckCodeRecordDestType.mobile, Integer.valueOf(0), 
				CheckCodeRecordCheckType.activiate, DateUtil.nowAddMinutes(-60));
		System.out.println(count);
		
		//test查询列表
		List list = checkCodeRecordManager.queryCheckCodeListByCheckType("13428981004", CheckCodeRecordDestType.mobile,  
					CheckCodeRecordCheckType.activiate);
		System.out.println(list);
		
		//更新状态
		CheckCodeRecord d = new CheckCodeRecord();
		d.setId(5);
		d.setVerifyCount(3);
		d.setUpdateTime(Calendar.getInstance());
		d.setStatus(CheckCodeRecordStatus.used);
		checkCodeRecordManager.updateVerifyStatus(d);
		
		//重新查询
		list = checkCodeRecordManager.queryCheckCodeListByCheckType("13428981004", CheckCodeRecordDestType.mobile,  
				CheckCodeRecordCheckType.activiate);
		System.out.println(list);
	
	}

	
    public void testInsert() {
        CheckCodeRecord record = new CheckCodeRecord();
        record.setCheckCode("oowerioewoioiere");
        record.setCheckType(CheckCodeRecordCheckType.register);
        record.setDestNo("415522571@qq.com");
        record.setDestType(CheckCodeRecordDestType.email);
        record.setMemberId(29);
        record.setSendTime(Calendar.getInstance());
        record.setExpireTime(DateUtil.getTime(new Date(), 1, 0, 0, 0));
        record.setStatus(CheckCodeRecordStatus.used);
        record.setVerifyCount(2);
        checkCodeRecordManager.insert(record);
    }
}
