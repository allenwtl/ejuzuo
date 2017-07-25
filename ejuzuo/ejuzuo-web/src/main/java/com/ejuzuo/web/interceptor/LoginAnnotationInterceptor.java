package com.ejuzuo.web.interceptor;


import com.aicai.memcachedclient.MemcachedClient;
import com.aicai.webutil.common.CookieUtils;
import com.aicai.webutil.common.UUIDUtils;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.common.GlobalParam;
import com.ejuzuo.web.util.ResultTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tianlun.wu on 2016/3/26 0026.
 */
public class LoginAnnotationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginAnnotationInterceptor.class);

    private final String loginUrl = "/login/toLogin";

    @Resource
    private GlobalParam globalParam ;

    @Resource
    private MemcachedClient memcachedClient ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Login loginMethod = handlerMethod.getMethodAnnotation(Login.class);
        if(loginMethod == null){
            //logger.info(handlerMethod.getMethod() + ":没有申明权限，放过");
            return true ;
        }

        logger.info(handlerMethod.getMethod()+":有申明权限，不能放过");

        String uuidStr = CookieUtils.getCookieValue(request, globalParam.getUuidCookie());
        if(StringUtils.isBlank(uuidStr)){
            logger.info("从cookie中 拿不到uuid的值 ");
            logger.info("uri:{}", request.getRequestURI());
            String uri = request.getRequestURI();
            if( uri.contains("/file/upload")){
                uuidStr = request.getParameter("uuid");
                if(!StringUtils.isBlank(uuidStr)){
                    CookieUtils.setCookie(request, response , globalParam.getUuidCookie(), uuidStr);
                    return true ;
                }
            }

            if(isAjax(request) || ResultTypeEnum.json.equals(loginMethod.value())){
                logger.info(handlerMethod.getMethod()+"是ajax请求");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(
                        "{\"code\":500,\"msg\":\"请重新登录！\"}");
            } else {

                String uuidURI = UUIDUtils.getUuidStr();
                if(!uri.contains("login")){
                    memcachedClient.set(uuidURI, 2*60, uri);
                }
                CookieUtils.setCookie(request, response, GlobalParam.uri, uuidURI, -1);
                response.sendRedirect(loginUrl);
            }
            return false;
        }

        String key = globalParam.getPrefix()+uuidStr+globalParam.getSuffix() ;
        //每次只要用到登录信息，就延长对象生命周期
        Member member = memcachedClient.getAndTouch(key, globalParam.getMemberLoginExpireTime());
        if(member == null){
            logger.info("缓存中没有用户的登录信息");

            if(isAjax(request) || ResultTypeEnum.json.equals(loginMethod.value())){
                logger.info(handlerMethod.getMethod()+"是ajax请求");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(
                        "{\"code\":500,\"msg\":\"请重新登录！\"}");
            } else {
                String uri = request.getRequestURI() ;
                if(!uri.contains("login")){
                    String uuidURI = UUIDUtils.getUuidStr();
                    CookieUtils.setCookie(request, response, GlobalParam.uri, uuidURI, -1);
                    memcachedClient.set(uuidURI, 2*60, uri);
                }
                response.sendRedirect(loginUrl);
            }

            return false;
        }

        memcachedClient.set(key, globalParam.getMemberLoginExpireTime() , member);
        logger.debug("account:{};password:{}", member.getAccount(), member.getPassword());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("LoginAnnotationInterceptor.postHandle()");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("LoginAnnotationInterceptor.afterCompletion()");
    }


    private boolean isAjax(HttpServletRequest request){
        String xRequestWith = request.getHeader("X-Requested-With");
        if(xRequestWith != null && xRequestWith.equals("XMLHttpRequest")){
            return true ;
        }
        return false ;
    }
}
