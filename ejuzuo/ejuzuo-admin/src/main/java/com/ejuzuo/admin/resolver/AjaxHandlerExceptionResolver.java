package com.ejuzuo.admin.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class AjaxHandlerExceptionResolver implements HandlerExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxHandlerExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		ModelAndView mav = new ModelAndView();
		
		String requestType = request.getHeader("X-Requested-With");
		boolean isAjax = (requestType != null && requestType.equals("XMLHttpRequest"));
		
		String exName = ClassUtils.getShortName(ex.getClass());
		
		if ("UnauthorizedException".equalsIgnoreCase(exName)) {
			if (isAjax) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				
				MappingJackson2JsonView view = new MappingJackson2JsonView();
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("success", false);
				attributes.put("code", "FORBIDDEN");
				attributes.put("msg", "没有相应的权限");
				view.setAttributesMap(attributes);
		        mav.setView(view);
			} else {
				mav.setViewName("error/unauth");
			}
		} else {
			
			logger.error("程序发生内部错误", ex);
			
			if (isAjax) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				
				MappingJackson2JsonView view = new MappingJackson2JsonView();
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("success", false);
				attributes.put("code", "INTERNAL_SERVER_ERROR");
				attributes.put("msg", "程序发生内部错误");
				view.setAttributesMap(attributes);
		        mav.setView(view);
			} else {
				mav.setViewName("error/500");
			}
		}
		
		return mav;
	}

}
