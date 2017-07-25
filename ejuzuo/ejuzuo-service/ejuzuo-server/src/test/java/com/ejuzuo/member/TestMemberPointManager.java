package com.ejuzuo.member;

import java.util.Calendar;
import java.util.Scanner;

import javax.annotation.Resource;

import org.junit.Test;

import com.aicai.appmodel.domain.result.ModelResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.BaseTest;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.PayStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.MemberSponsorLog;
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.ActivityEnrollOption;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.service.ActivityEnrollService;
import com.ejuzuo.common.service.DownloadInfoService;
import com.ejuzuo.common.service.MemberFavoriteService;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.service.PromoRecordService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.server.member.manager.MemberPointManager;
import com.ejuzuo.server.member.manager.MemberSponsorLogManager;

/**
 * Created by tianlun.wu on 2016/4/8 0008.
 */
public class TestMemberPointManager extends BaseTest {

    @Resource
    private MemberPointManager memberPointManager ;
    @Resource
    private MemberSponsorLogManager memberSponsorLogManager;
    @Resource
    private ActivityEnrollService activityEnrollService;
    @Resource
    private MemberFavoriteService memberFavoriteService;
    @Resource
    private PromoRecordService promoRecordService;
    @Resource
    private MemberPointLogService memberPointLogService;
    @Resource
    private DownloadInfoService downloadInfoService;
    @Resource
    private MemberService memberService;
    

    //@Test
    public void testUpdate(){
        MemberPoint memberPoint = new MemberPoint();
        memberPoint.setBalance(0);
        memberPoint.setMemberId(61);
        memberPoint.setRemark("第一次初始化");
        memberPointManager.updateById(memberPoint);
    }

   // @Test
    public void testSendVipSms() {
    	
        MemberSponsorLog tempLog = new MemberSponsorLog();
        tempLog.setId(21);
        tempLog.setMemberId(69);
        tempLog.setStatus(PayStatus.PAY_SUCCESS.getIndex());
        tempLog.setPayOrderNo("201605301024063325421");
        tempLog.setPayTime(Calendar.getInstance());
        tempLog.setSponsorType(4);
        tempLog.setResponseInfo("reponse");
        ModelResult<MemberSponsorLog> modelResult = memberSponsorLogManager.updateByCharge(tempLog);
    	if (!modelResult.isSuccess()) {
    		System.out.println(modelResult.getErrorMsg());
    	}   
    	
    	new Scanner(System.in).next();
    }
    
    //@Test
    public void testSignup() {    	
    	ActivityEnrollOption a = new ActivityEnrollOption();
    	a.setMemberId(95);
    	a.setActivityId(17);
    	a.setEnrollBeiginTime(DateUtil.parseYYYY_MM_DD("2016-04-10"));
    	a.setEnrollEndTime(DateUtil.parseYYYY_MM_DD("2016-06-15"));
    	ModelResult mr = activityEnrollService.signUp(a);
    	if (!mr.isSuccess()) {
    		System.out.println(mr.getErrorMsg());
    	}

    }
    
    
    //114, 普通
    //106, 高级
    //95, VIP
    //@Test
    public void testFavorite() {
    	MemberFavorite mf = new MemberFavorite();
    	mf.setCreateTime(DateUtil.getCurrentCalendar());
    	mf.setMemberId(106);
    	mf.setObjectId(69);
    	mf.setObjectType(1);
    	ModelResult<Integer> mr = memberFavoriteService.collect(mf);
    	if (!mr.isSuccess()) {
    		System.out.println(mr.getErrorMsg());
    	}
    }
    
   // @Test
    public void testShareCallback() {
    	PromoRecord record = new PromoRecord();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", "127.0.0.1");
        jsonObject.put("referer", "chrome");
        jsonObject.put("userAgent", "ua");
        record.setCallbackInfo(jsonObject.toJSONString());
        record.setPromoCode("1caedccfd2244d698b1f8794834ac0ff");
        ModelResult<PromoRecord> mr= promoRecordService.checkCode(record);
    	//分享回调成功，发积分。
    	if (mr.isSuccess()) {
    		PromoRecord pr = mr.getModel();
            MemberPointLog memberPointLog = new MemberPointLog();
            memberPointLog.setMemberId(pr.getMemberId());
            memberPointLog.setTransType(MemberPointLogTransType.SHARE.getIndex());
            memberPointLog.setRelatedId(pr.getId());
            memberPointLog.setRelatedType(ObjectType.share.getIndex());
            memberPointLogService.sendPoint(memberPointLog);
    	}

    	
    	
    }
    
    
    
    //114, 普通
    //106, 高级
    //95, VIP
    @Test
    public void testDownloadCtrl() {
    	 JSONObject jsonObject = new JSONObject();
    	Integer memberId = 114;
        //普通用户5次/天，高级用户10次/天，vip20次/天。 begin
        DownloadInfoOption query = new DownloadInfoOption();
        query.setMemberId(Long.valueOf(memberId));
        query.setPayStatus(PayStatus.PAY_SUCCESS.getIndex());
        query.setStatus(Status.STATUS.getIndex());
        query.setPayBeginTime(DateUtil.getTheDayZero());
        query.setPayEndTime(DateUtil.getTheDayMidnight());
        Integer count = downloadInfoService.countByPage(query);            
        
        boolean canDownload = false;
        int limit = 5;
      	if(memberService.isDesingerByMemberId(memberId)){
      		limit = 10;
    	}	
        if(memberService.isVipByMemberId(memberId)) { 
        	limit = 20;
        }
        if (count < limit) {
           canDownload = true;
        }
        if (!canDownload) {                  
           System.out.println("下载频次超限！");
           jsonObject.put("code", 444);
           //下载频次超限：普通用户5次/天，高级用户10次/天，vip用户20次/天。
           System.out.println("您好！您已超出今日的下载次数，普通用户5次/天，高级用户10次/天，vip用户20次/天！");
        }            
        //end
		//end	
    	
    	
    }
    
    
}
 