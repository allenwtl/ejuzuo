package com.ejuzuo.member;

import com.ejuzuo.BaseTest;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.server.member.manager.MemberManager;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/3/28 0028.
 */

public class TestMemberManager extends BaseTest {

    @Resource
    private MemberManager memberManager ;

    @Resource
    private MemberService memberService;

    @Test
    public void test(){
        Member member = new Member();
        member.setAccount("abcd");
        member.setNickName("nimei123");
        member.setPassword("123456");
        member.setMobile("15365928715");
        member.setLocked(0);
        member.setVerified(0);
        member.setStatus(1);
        member.setActiveStatus(1);
        memberManager.save(member);
    }


    @Test
    public void testSaveOperLogAndGivePoint(){
        memberService.saveOperLogAndGivePoint(65);
    }

}
