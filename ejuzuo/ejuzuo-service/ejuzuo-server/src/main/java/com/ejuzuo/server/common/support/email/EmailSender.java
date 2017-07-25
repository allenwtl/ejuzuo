package com.ejuzuo.server.common.support.email;

import com.ejuzuo.common.domain.type.CheckCodeRecordCheckType;
import com.ejuzuo.common.util.EmailUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

/**
 * Created by tianlun.wu on 2016/3/24 0024.
 */

@Service
public class EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Resource
    private MailSenderConfig mailSenderConfig;

    @Resource
    private FreeMarkerConfigurer freeMarker;

    public boolean sendEmail(String email, CheckCodeRecordCheckType checkType, Map<String, Object> map) {
        if (!EmailUtils.isValidEmail(email)) {
            logger.info("接受邮箱错误！");
            return false;
        }
        MyAuthenticator authenticator = null;
        Properties pro = mailSenderConfig.getProperties();
        if (mailSenderConfig.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailSenderConfig.getUserName(), mailSenderConfig.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {

            InternetAddress internetAddress = new InternetAddress(email);

            // 根据session创建一个邮件消息
            MimeMessage mailMessage = new MimeMessage(sendMailSession);
            //
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            // 创建邮件发送者地址
            InternetAddress from = new InternetAddress(mailSenderConfig.getFromAddress());
            mimeMessageHelper.setFrom(from);
            //创建邮件的接收者地址，并设置到邮件消息中
            mimeMessageHelper.setTo(internetAddress);
            //设置主题
            mimeMessageHelper.setSubject(getSubjectByType(checkType));
            //设置发送时间
            mimeMessageHelper.setSentDate(new Date());
            //设置内容
            map.put("url", mailSenderConfig.getMailUrl() + "/" + checkType.getIndex());
            mimeMessageHelper.setText(getContentByType(checkType, map), true);
//            //设置附件
//            setAttachment(emailBusinessType, mimeMessageHelper);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private String getContentByType(CheckCodeRecordCheckType checkType, Map<String, Object> map) {

        String ftlFile = null;
        String htmlText = null;
        if (checkType.getIndex() == CheckCodeRecordCheckType.activiate.getIndex()) {
            ftlFile = "activiate.ftl"; //注册激活
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            ftlFile = "activiate.ftl";
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.authEmail.getIndex()) {
            ftlFile = "authEmail.ftl";
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
            ftlFile = "findPwd.ftl";
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.updatePwd.getIndex()) {
            ftlFile = "updatePwd.ftl";
        } else {
            return null;
        }
        try {
            Template tpl = freeMarker.getConfiguration().getTemplate(ftlFile);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return htmlText;
    }

    private String getSubjectByType(CheckCodeRecordCheckType checkType) {
        logger.info("checkType:{}", checkType.getIndex());
        if (checkType.getIndex() == CheckCodeRecordCheckType.register.getIndex()) {
            return "注册验证码[巨作]";
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.authEmail.getIndex()) {
            return "验证邮箱验证码[巨作]";
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.findPwd.getIndex()) {
            return "找回密码验证码[巨作]";
        } else if (checkType.getIndex() == CheckCodeRecordCheckType.updatePwd.getIndex()) {
            return "修改密码验证码[巨作]";
        } else {
            return null;
        }
    }


//    private void setAttachment(EmailBusinessType emailBusinessType, MimeMessageHelper helper) {
//
//        try {
//            if (emailBusinessType.getIndex() == EmailBusinessType.FORGET_PASSWORD.getIndex()) {
//                File file = new File("D:\\www\\hupu\\HUPU_ORDER_20160120.xls");
//                helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
//            }
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }


}
