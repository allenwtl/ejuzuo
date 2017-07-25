package com.ejuzuo.checkcoderecord;

import javax.annotation.Resource;

import org.junit.Test;

import com.ejuzuo.BaseTest;
import com.ejuzuo.server.member.manager.SmsRecordManager;

public class TestSmsRecord extends BaseTest {
	
    @Resource
	private SmsRecordManager  smsRecordManager;
    
    //@Test
    public void testSendSmsRecord() throws Exception {
    	String content = "尊敬的用户，您本次操作的验证码：456129";
    	//smsRecordManager.sendSmsRecord("13428981004", null, content);
    	//TimeUnit.SECONDS.sleep(30);
    	smsRecordManager.sendSmsRecord("13428981004", 5, content);    	
    }
  }