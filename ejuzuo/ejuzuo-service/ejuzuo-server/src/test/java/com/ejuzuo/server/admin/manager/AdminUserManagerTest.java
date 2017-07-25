package com.ejuzuo.server.admin.manager;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aicai.dao.GenericDao;
import com.alibaba.fastjson.JSON;
import com.ejuzuo.common.domain.Member;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-context/spring-config.xml"})
public class AdminUserManagerTest {

	@Autowired
	private GenericDao dao;
	@Resource
	private AdminUserManager adminUserManager;
	
	@Test
	public void testInsert() throws InterruptedException {
		System.out.println("hhhhhhhhhhhhh");
		System.out.println(dao);
		Member member = dao.queryUnique("MemberMapper.selectByPrimaryKey", 28L);
		System.out.println(JSON.toJSONString(member));
		Thread.sleep(1000000L);
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryLong() throws InterruptedException {
		System.out.println(JSON.toJSONString(adminUserManager.query(57L)));
		Thread.sleep(1000000L);
	}

	@Test
	public void testQueryString() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryPage() {
		fail("Not yet implemented");
	}

}
