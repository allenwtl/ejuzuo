package com.ejuzuo.server.common.support.sms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aicai.appmodel.domain.result.BaseResult;


/**
 * 短信通道抽象类
 * 
 * @date 2012-6-11
 * Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights reserved.
 */
public abstract class SmsChannel {
    protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());
    //短信通道服务商ID
    public final static int GATEWAY_XIEHE = 1;
    
    protected String msg_key="XXXX";    
    // 短信通道标识
    protected Integer gateway;

    /**
     * 初始化设置短信通道标识    1:移动    2:联通    3:电信    4:企信通
     * 
     * @create_time 2012-6-11 下午5:08:29
     */
    protected abstract void initGateway();
    
    /**
     * 初始化短信套餐参数及发送上限限制策略
     * 
     * @create_time 2012-8-23 上午10:15:04
     */
    protected abstract void initSmsPackage();

    /**
     * 获取短信通道标识
     * @return
     * @create_time 2012-6-11 下午5:12:15
     */
    public Integer getGateway() {
        return gateway;
    }

    /**
     * 发送短信方法,必需参数: mobileNo msg
     * 
     * @param sms
     * @return
     * @create_time 2012-6-11 上午10:53:05
     */
    public abstract BaseResult sendMessage(Sms sms);
}
