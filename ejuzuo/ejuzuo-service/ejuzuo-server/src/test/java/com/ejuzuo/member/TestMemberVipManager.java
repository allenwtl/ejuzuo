package com.ejuzuo.member;

import com.ejuzuo.BaseTest;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.server.member.manager.MemberVipManager;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/5/18 0018.
 */
public class TestMemberVipManager extends BaseTest {

    @Resource
    private MemberVipManager memberVipManager;

    @Test
    public void testUpdateTime (){
        memberVipManager.updateVipTime(69, "415522571@qq.com", MemberPointLogTransType.DONATE_TWO);
    }


}
