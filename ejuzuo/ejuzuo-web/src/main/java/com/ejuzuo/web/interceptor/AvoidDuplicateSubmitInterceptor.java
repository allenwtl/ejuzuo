package com.ejuzuo.web.interceptor;

import com.aicai.memcachedclient.MemcachedClient;
import com.aicai.webutil.common.CookieUtils;
import com.aicai.webutil.common.UUIDUtils;
import com.ejuzuo.web.annotation.AvoidDuplicateSubmitAnnotation;
import com.ejuzuo.web.common.GlobalParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by tianlun.wu on 2016/4/13 0013.
 */
public class AvoidDuplicateSubmitInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AvoidDuplicateSubmitInterceptor.class);


    @Resource
    private MemcachedClient memcachedClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AvoidDuplicateSubmitAnnotation annotation = method.getAnnotation(AvoidDuplicateSubmitAnnotation.class);
        if (annotation != null) {
            boolean needSaveCache = annotation.needSaveToken();
            if (needSaveCache) {
                String uuidAvoidSubmit = UUIDUtils.getUuidStr();
/*                request.setAttribute("token", uuidAvoidSubmit);
                memcachedClient.set(GlobalParam.avoidSubmit+uuidAvoidSubmit, 3 * 60, uuidAvoidSubmit);*/
                CookieUtils.setCookie(request, response, GlobalParam.avoidSubmit, uuidAvoidSubmit, -1 );
            }

            boolean needRemoveCache = annotation.needRemoveToken();
            if (needRemoveCache) {
/*                if (isRepeatSubmit(request)) {
                    logger.info("please don't repeat submit,url:"
                            + request.getServletPath() + "]");
                    return false;
                }
                memcachedClient.delete(GlobalParam.avoidSubmit +request.getParameter("token"));*/
                String uuidAvoidSubmit = CookieUtils.getCookieValue(request, GlobalParam.avoidSubmit);
                if(StringUtils.isBlank(uuidAvoidSubmit)){

                    return true;
                }
                String value = memcachedClient.get(uuidAvoidSubmit);
                if(StringUtils.isBlank(value)){
                    memcachedClient.set(uuidAvoidSubmit, 3, uuidAvoidSubmit);
                    return true ;
                }
                logger.info("重复提交了URL:"+request.getServletPath());
                response.getWriter().print("{\"code\":444, \"msg\":\" 请勿重复提交\"}");
                return false ;
            }
        }

        return true;
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String clientToken = request.getParameter("token");
        if (clientToken == null) {
            return true;
        }

        String serverToken = memcachedClient.get(GlobalParam.avoidSubmit+clientToken);
        if (serverToken == null) {
            return true;
        }

        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
    }
}
