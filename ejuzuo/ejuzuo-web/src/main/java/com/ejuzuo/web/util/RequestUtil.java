package com.ejuzuo.web.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tianlun.wu on 2016/5/6 0006.
 */
public class RequestUtil {
    protected static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
    public static String getUserIp(HttpServletRequest request) {
        // 获得用户的代理服务
        String fromIp = request.getHeader("x-forwarded-for");
        logger.info("获取到用户的原始的HTTP_X_FORWARDED_FOR:{}", fromIp);
        if (StringUtils.isBlank(fromIp) || "unknown".equals(fromIp.trim())) {
            fromIp = request.getHeader("X-Real-IP");
            logger.info("获取到用户的原始的X-Real-IP:{}", fromIp);
        }
        if (StringUtils.isBlank(fromIp)) {
            fromIp = request.getRemoteAddr();
        }
        // 取最前面的IP地址
        String[] fromIpArr = fromIp.split(",");
        if (fromIpArr.length > 0) {
            fromIp = fromIpArr[0];
        }
        return fromIp;
    }


    public static String check(String urlvalue) {
        String inputLine = "";

        try {
            URL e = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection)e.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return inputLine;
    }

}
