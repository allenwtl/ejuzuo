package com.ejuzuo.server.common.support.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by tianlun.wu on 2016/3/24 0024.
 */
@Service
public class MailSenderConfig  {
    // 发送邮件的服务器的IP和端口
	@Value("${mail_server_host}")
    private String mailServerHost;
	@Value("${mail_server_port}")
	private String mailServerPort = "25";
    // 邮件发送者的地址
	@Value("${mail_from_address}")
    private String fromAddress;
    // 登陆邮件发送服务器的用户名和密码
	@Value("${mail_user_name}")
    private String userName;
	@Value("${mail_password}")
    private String password;
    // 是否需要身份验证
	@Value("${mail_validate}")
    private boolean validate = false;
    //注册激活的访问的链接
    @Value("${mail_url}")
    private String mailUrl ;

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties(){
        Properties p = new Properties();
        p.put("mail.debug", "true");
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }

    public String getMailUrl() {
        return mailUrl;
    }

    public void setMailUrl(String mailUrl) {
        this.mailUrl = mailUrl;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }
    public String getMailServerPort() {
        return mailServerPort;
    }
    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }
    public boolean isValidate() {
        return validate;
    }
    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
