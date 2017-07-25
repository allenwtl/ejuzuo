package com.ejuzuo.admin.support.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class AjaxUserFilter extends UserFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest req = WebUtils.toHttp(request);
		String xmlHttpRequest = req.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(xmlHttpRequest)) {
			
			JSONObject jo = new JSONObject();
			jo.put("success", false);
			jo.put("code", "UNAUTHORIZED");
			jo.put("msg", "未登录/登录过期");
			
			HttpServletResponse res = WebUtils.toHttp(response);
//			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			PrintWriter pw = res.getWriter();
			pw.print(jo.toJSONString());
			pw.flush();
			return false;
		}

		return super.onAccessDenied(request, response);
	}

}
