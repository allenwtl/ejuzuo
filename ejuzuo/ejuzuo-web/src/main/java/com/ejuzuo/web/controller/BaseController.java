package com.ejuzuo.web.controller;

import com.aicai.memcachedclient.MemcachedClient;
import com.aicai.webutil.common.CookieUtils;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.vo.MemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户类
 * Created by tianlun.wu on 2016/3/26 0026.
 */

public class BaseController {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    public GlobalParam globalParam;

    @Resource(name = "memcachedClient")
    public MemcachedClient memcachedClient;

    public MemberVo getMember(HttpServletRequest request) {
        MemberVo member = (MemberVo) request.getAttribute("memberInCache");
        return member;
    }

    public String getAccount(HttpServletRequest request) {
        MemberVo temp = getMember(request);
        if (temp == null) {
            return null;
        }
        return temp.getAccount();
    }

    public Integer getMemberId(HttpServletRequest request) {
        MemberVo temp = getMember(request);
        if (temp == null) {
            return null;
        }
        return temp.getId();
    }


    public void deleteMemberInCache(HttpServletRequest request) {
        String key = globalParam.getPrefix() + CookieUtils.getCookieValue(request, globalParam.getUuidCookie()) + globalParam.getSuffix();
        memcachedClient.delete(key);
        request.removeAttribute("memberInCache");
    }

    public void updateMemberInfoInCache(String uuidStr, MemberVo memberVo) {
        memcachedClient.set(globalParam.getPrefix() + uuidStr + globalParam.getSuffix(), globalParam.getMemberLoginExpireTime(), memberVo);
    }


}
