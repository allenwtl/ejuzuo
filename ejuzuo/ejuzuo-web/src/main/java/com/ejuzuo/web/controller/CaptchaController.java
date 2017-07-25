package com.ejuzuo.web.controller;

import com.aicai.webutil.common.CookieUtils;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.util.UUIDUtils;
import com.ejuzuo.web.common.GlobalParam;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**验证码生成器
 * Created by tianlun.wu on 2016/4/12 0012.
 */
@Controller
@RequestMapping("captcha")
public class CaptchaController extends BaseController {

    @Autowired
    private Producer captchaProducer ;

    @RequestMapping(value = "captcha-image")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeUUid = CookieUtils.getCookieValue(request, GlobalParam.validateCode);
        if(StringUtils.isBlank(codeUUid)){
            codeUUid = UUIDUtils.getUuidStr();
            CookieUtils.setCookie(request, response, GlobalParam.validateCode, codeUUid, -1);
        }

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();

        memcachedClient.set(codeUUid, 2*60, capText);
        logger.info("验证码：{}", capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    @RequestMapping("check-image/{code}")
    @ResponseBody
    public JSONObject checkImage(HttpServletRequest request,  HttpServletResponse response, @PathVariable String code){
        JSONObject jsonObject = new JSONObject();

        if(StringUtils.isBlank(code)){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请输入验证码");
            return jsonObject;
        }

        String codeUUid = CookieUtils.getCookieValue(request, GlobalParam.validateCode);
        if(StringUtils.isBlank(codeUUid)){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "请重新发送验证码");
            return jsonObject;
        }

        String capText = memcachedClient.get(codeUUid);

        if(!capText.equals(code)){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "验证码不正确");
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "输入正确");
        return jsonObject;
    }
}
