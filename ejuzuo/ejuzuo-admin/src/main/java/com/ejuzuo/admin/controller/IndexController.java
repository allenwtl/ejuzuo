package com.ejuzuo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController extends BaseController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "error/404", method = RequestMethod.GET)
	public String error404() {
		return "error/404";
	}
	
	@RequestMapping(value = "error/500", method = RequestMethod.GET)
	public String error500() {
		return "error/500";
	}
	
}
