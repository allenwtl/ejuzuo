package com.ejuzuo.web.interceptor;

import com.aicai.memcachedclient.MemcachedClient;
import com.aicai.webutil.common.CookieUtils;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tianlun.wu on 2016/4/14 0014.
 */

public class SessionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Resource
    private GlobalParam globalParam ;

    @Resource
    private MemcachedClient memcachedClient;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uuidStr = CookieUtils.getCookieValue(request, globalParam.getUuidCookie());
        if (StringUtils.isNotEmpty(uuidStr)) {
            MemberVo memberInCache = memcachedClient.get(globalParam.getPrefix()+uuidStr+globalParam.getSuffix());

            if(memberInCache == null ){
                request.setAttribute("memberInCache", null);
            } else {
                memberInCache.setPassword("");
                request.setAttribute("memberInCache", memberInCache);
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
