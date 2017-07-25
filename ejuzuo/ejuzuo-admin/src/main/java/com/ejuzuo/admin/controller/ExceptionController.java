package com.ejuzuo.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ControllerAdvice
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ModelAndView handleUnauthorizedException(HttpServletRequest request, UnauthorizedException e) {
		
		ModelAndView mav = new ModelAndView();
		
		String requestType = request.getHeader("X-Requested-With");
		boolean isAjax = (requestType != null && requestType.equals("XMLHttpRequest"));
		
		if (isAjax) {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("success", false);
			attributes.put("code", "NOPERMISSION");
			attributes.put("msg", "没有相应的权限");
			view.setAttributesMap(attributes);
	        mav.setView(view);
		} else {
			mav.setViewName("error/unauth");
		}
		
		return mav;
	}
	
//	@ResponseStatus(value = HttpStatus.NOT_FOUND)
//	public ModelAndView handle404(HttpServletRequest request) {
//		
//		ModelAndView mav = new ModelAndView();
//		
//		String requestType = request.getHeader("X-Requested-With");
//		boolean isAjax = (requestType != null && requestType.equals("XMLHttpRequest"));
//		
//		if (isAjax) {
//			MappingJackson2JsonView view = new MappingJackson2JsonView();
//			Map<String, Object> attributes = new HashMap<String, Object>();
//			attributes.put("success", false);
//			attributes.put("code", "Not Found");
//			attributes.put("msg", "没有相应的服务");
//			view.setAttributesMap(attributes);
//	        mav.setView(view);
//		} else {
//			mav.setViewName("error/404");
//		}
//		
//		return mav;
//	}
	
	/**
	 * 不起作用
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handle500(HttpServletRequest request, Exception e) {
		
		logger.error(e.getMessage(), e);
		
		ModelAndView mav = new ModelAndView();
		
		String requestType = request.getHeader("X-Requested-With");
		boolean isAjax = (requestType != null && requestType.equals("XMLHttpRequest"));
		
		if (isAjax) {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("success", false);
			attributes.put("code", "Internal Server Error");
			attributes.put("msg", "内部服务异常");
			view.setAttributesMap(attributes);
	        mav.setView(view);
		} else {
			mav.setViewName("error/500");
		}
		
		return mav;
	}
	
}
