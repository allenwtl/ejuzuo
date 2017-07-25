package com.ejuzuo.server.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.MemberVip;
import com.ejuzuo.common.domain.type.CheckCodeRecordDestType;
import com.ejuzuo.common.option.MemberOption;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.member.manager.MemberManager;
import com.ejuzuo.server.member.manager.MemberOperLogManager;
import com.ejuzuo.server.member.manager.MemberPointManager;
import com.ejuzuo.server.member.manager.MemberVipManager;

/**
 * Created by tianlun.wu on 2016/3/28 0028.
 */

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    private MemberManager memberManager;

    @Resource
    private MemberOperLogManager memberOperLogManager;

    @Resource
    private MemberPointManager memberPointManager;
    
    @Resource
    private DesignerManager designerManager;
    
    @Resource
    private MemberVipManager memberVipManger;

    @Override
    public ModelResult<Member> saveMember(Member member) {
    	// 如果用户图像为空，设置默认的图像
    	if (StringUtils.isBlank(member.getProfileImg())) {
    		JSONObject jo = new JSONObject();
    		jo.put("pic7675", "/img/member/default/default-3144438.jpg");
    		jo.put("picold", "/img/member/default/default.jpg");
    		jo.put("pic4343", "/img/member/default/default-3144286.jpg");
			member.setProfileImg(jo.toJSONString());
		}
        return memberManager.save(member);
    }

    @Override
    public ModelResult<List<Member>> queryByOption(MemberOption option) {
        return memberManager.queryByOption(option);
    }

    @Override
    public ModelResult<Member> queryByAccount(String account) {
        return memberManager.queryByAccount(account);
    }

    @Override
    public ModelResult<Member> queryByMemberId(Integer memberId) {
        return memberManager.queryByMemberId(memberId);
    }

    @Override
    public ModelResult<Boolean> judgeEmailOrMobileIsExist(String code, CheckCodeRecordDestType destType) {
        return memberManager.judgeEmailOrMobileIsExist(code, destType);
    }

    @Override
    public ModelResult<Boolean> updateById(Member member) {
        return memberManager.updateById(member);
    }

    @Override
    public ModelResult<Boolean> saveOperLogAndGivePoint(Integer memberId) {
/*        MemberOperLog memberOperLog = new MemberOperLog();
        memberOperLog.setMemberId(memberId);
        memberOperLog.setOperType(MemberOperLogType.LOGIN.getIndex());
        memberOperLog.setRemark("用户登录");
        memberOperLogManager.save(memberOperLog);

        int size = memberOperLogManager.queryOperLogToday(memberId, MemberOperLogType.LOGIN).getModel();
        if (size > 1) {
            //不赠送积分
            logger.info("用户:[{}],今天登录次数超过1次，不赠送登录积分", memberId);
            return new ModelResult<>(Boolean.FALSE);
        }

        MemberPoint memberPoint = memberPointManager.queryByMemberId(memberId);
        if (memberPoint == null) {
            memberPoint = new MemberPoint();
            memberPoint.setMemberId(memberId);
            memberPoint.setBalance(0);
            memberPointManager.save(memberPoint);
        }

        memberPoint.setBalance(memberPoint.getBalance()+ MemberPointLogTransType.LOGIN.getPoint());

        MemberPointLog memberPointLog = new MemberPointLog();
        memberPointLog.setMemberId(memberId);
        memberPointLog.setAmount(MemberPointLogTransType.LOGIN.getPoint());
        memberPointLog.setTransType(MemberPointLogTransType.LOGIN.getIndex());
        memberPointLog.setRemark("登录");
        memberPointLog.setCreator(memberManager.queryByMemberId(memberId).getModel().getAccount());
        memberPointManager.addPoint(memberPoint, memberPointLog);*/

        return new ModelResult<>(Boolean.TRUE);
    }

	@Override
	public PageResult<Member> queryPage(DataPage<Member> dataPage, MemberOption qVo) {
		return memberManager.queryByPage(qVo, dataPage);
	}

	@Override
	public ModelResult<Boolean> update(Integer id, Integer locked, Integer status) {
		return memberManager.update(id,locked,status);
	}
	
	@Override
	public boolean isVipByMemberId(Integer memberId) {
        MemberVip memberVip = memberVipManger.judgeVipByMemberId(memberId);
        if (memberVip != null ) {
            return true;
        } else {
            return false;
        }
	}
	
	@Override
	public boolean isDesingerByMemberId(Integer memberId) {
        Designer designer = designerManager.queryByMemberId(memberId).getModel();        
        if (designer != null /*&& designer.getStatus()==2 **/) { //完善资料自动升级为高级会员
            return true;
        } else {
            return false;
        }
	}	
	
}
