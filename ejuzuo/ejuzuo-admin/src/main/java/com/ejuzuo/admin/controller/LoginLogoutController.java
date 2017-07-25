package com.ejuzuo.admin.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.domain.AdminUser;
import com.ejuzuo.common.service.AdminUserService;
import com.google.code.kaptcha.Producer;

@Controller
public class LoginLogoutController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginLogoutController.class);
	
	private static final String SESSION_KEY_CAPTCHA = "SESSION_CAPTCHA";
	private static final String SESSION_KEY_CAPTCHA_TIME = "SESSION_CAPTCHA_TIME";
	
	@Autowired
	@Qualifier("kaptchaProducer")
	private Producer kaptchaProducer;
	
	@Resource
	private AdminUserService adminUserService;
	
	@RequestMapping("/captcha.jpg")
	public void kaptcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Set standard HTTP/1.1 no-cache headers.
		resp.setHeader("Cache-Control", "no-store, no-cache");

		// return a jpeg
		resp.setContentType("image/jpeg");

		// create the text for the image
		String capText = this.kaptchaProducer.createText();

		// store the text in the session
		req.getSession().setAttribute(SESSION_KEY_CAPTCHA, capText);

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		req.getSession().setAttribute(SESSION_KEY_CAPTCHA_TIME, new Date());

		// create the image with the text
		BufferedImage bi = this.kaptchaProducer.createImage(capText);

		ServletOutputStream out = resp.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);

		// fixes issue #69: set the attributes after we write the image in case
		// the image writing fails.

		// store the text in the session
		req.getSession().setAttribute(SESSION_KEY_CAPTCHA, capText);

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		req.getSession().setAttribute(SESSION_KEY_CAPTCHA_TIME, LocalDateTime.now());
	}
	
	private String checkKaptcha(HttpSession session, String code) {
		
		String kaptchaText = (String) session.getAttribute(SESSION_KEY_CAPTCHA);
		LocalDateTime kaptchaTime = (LocalDateTime) session.getAttribute(SESSION_KEY_CAPTCHA_TIME);
		
		// 失效 不应出现
		if (StringUtils.isBlank(kaptchaText) || kaptchaTime == null) {
			return "验证码失效";
		}
		
		LocalDateTime ldt = LocalDateTime.now().minusMinutes(10);
		boolean isExpired = ldt.isAfter(kaptchaTime);
		if (isExpired) {
			return "验证码过期";
		}
		
		
		if (!kaptchaText.equalsIgnoreCase(code)) {
			return "验证码错误";
		}
		
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		// 已登陆则 跳到首页
        if (subject.isAuthenticated()) {
            return "redirect:/";
        }
		
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			HttpSession session,
			ModelMap modelMap,
			@RequestParam String account, 
			@RequestParam String password, 
			@RequestParam String captcha) {
        // 校验 验证码
		String errCaptcha = checkKaptcha(session, captcha);
		if (StringUtils.isNotBlank(errCaptcha)) {
			modelMap.put("error", errCaptcha);
			modelMap.put("account", account);
			return "login";
		}
		
		password = new Md5Hash(password, "bull", 3).toString();
		
		final AdminUser user;
		try {
			Subject subject = SecurityUtils.getSubject();
			// 身份验证
			subject.login(new UsernamePasswordToken(account, password));
			// 验证成功在Session中保存用户信息
			ModelResult<AdminUser> model = adminUserService.query(account, password);
			user = model.getModel();
			session.setAttribute(SESSION_KEY_USER, user);
			
			logger.info("用户登录成功 ["+account+"]");
			
		} catch (AuthenticationException e) {
			logger.warn("用户登录失败 ["+account+":"+password+"] " + e.getMessage());
			modelMap.put("error", e.getMessage());
			modelMap.put("account", account);
			return "login";
		}
		
		// 登录成功日志
		this.logOperation(AdminOperType.LOGIN, null);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		
		// 登出成功日志
		this.logOperation(AdminOperType.LOGOUT, null);
		
		session.removeAttribute(SESSION_KEY_USER);
		
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return "redirect:/login";
	}
	
}
