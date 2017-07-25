package com.ejuzuo.server.common.support.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by tianlun.wu on 2016/3/24 0024.s
 */
public class MyAuthenticator extends Authenticator {

    String userName=null;
    String password=null;

    public MyAuthenticator(){
    }
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}
