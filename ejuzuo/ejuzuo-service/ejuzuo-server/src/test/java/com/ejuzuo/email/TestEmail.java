package com.ejuzuo.email;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by tianlun.wu on 2016/3/24 0024.
 */
public class TestEmail {

    public static void main(String[] args) {
/*
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("smtp.163.com");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName("18673669129@163.com");
        mailSenderInfo.setPassword("allen123");
        mailSenderInfo.setFromAddress("18673669129@163.com");
        mailSenderInfo.setToAddress("ejuzuo@163.com");
        mailSenderInfo.setSubject("subject hello");
        mailSenderInfo.setContent("content nb");*/

/*        SendMailInterfaceImpl sendMailInterfaceImpl = new SendMailInterfaceImpl();
        sendMailInterfaceImpl.sendEmail(mailSenderInfo);*/
    	
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring-context/spring-config.xml");


//		InputStream in = TestEmail.class.getResourceAsStream("/mybatis-config/mybatis-ejuzuo.xml");
//		new SqlSessionFactoryBuilder().build(in);


    }

}
