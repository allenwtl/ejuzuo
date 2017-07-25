package com.ejuzuo.common.exception.common;

/**
 * Created by tianlun.wu on 2016/5/10 0010.
 */
public class PayException extends RuntimeException {

    private static final long serialVersionUID = -8373313673016976465L;

    private MsgCode msgCode ;

    public PayException(MsgCode msgCode){
        super(msgCode.getMsg());
        this.msgCode = msgCode;
    }

    public PayException(String msg) {
        super(msg);
        this.msgCode = new MsgCode("pay.business.error", msg);
    }
}
