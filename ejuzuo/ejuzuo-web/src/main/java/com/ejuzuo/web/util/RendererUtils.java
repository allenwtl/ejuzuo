package com.ejuzuo.web.util;


import com.ejuzuo.web.common.SystemConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * 直接渲染的工具类
 * 
 * @author xiemingmei
 * @date 2013/10/16
 */
public class RendererUtils {
	 /**
     * 直接输出字符串.
     */
     public static void renderText(String text, HttpServletResponse response) {
         render(text, "text/plain;charset="+ SystemConstants.GOBAL_ENCODE, response);
    }
    /**
     * 输出JSON字符串
     * @param obj
     * @
     */
     public static void   renderJson(Object obj,HttpServletResponse response) {
         renderJson(obj, null,response);
    }
    /**
     * 直接输出XML.
     */
      public static void   renderHtml(String html, HttpServletResponse response) {
         render(html, "text/html;charset="+SystemConstants.GOBAL_ENCODE,response);
    }
    /**
     * 直接输出XML.
     */
      public static void renderXML(String xml,HttpServletResponse response) {
         render(xml, "text/xml;charset="+SystemConstants.GOBAL_ENCODE,response);
    }
    
    /**
     * 绕过Template,直接输出内容的简便函数.
     */
    private static void render(String text, String contentType, HttpServletResponse response) {
        PrintWriter printWriter = null;
        try {          
            response.setContentType(contentType);
            printWriter = response.getWriter();
            printWriter.write(text);
            printWriter.flush();
        } catch (IOException e) {
        } finally {
            if(printWriter != null){
                printWriter.close();
            }            
        } 
    }
    
    /**
     * 绕过Template,直接输出JSON的简便函数.
     */
    private static void renderJson(Object obj, JsonConfig jsonConfig, HttpServletResponse response) {
        if (obj instanceof Object[] || obj instanceof Collection) {
            if (jsonConfig != null) {
                 renderText(JSONArray.fromObject(obj, jsonConfig).toString(), response);
            } else {
                 renderText(JSONArray.fromObject(obj).toString(),response);
            }
        } else {
            if (jsonConfig != null) {
                 renderText(JSONObject.fromObject(obj, jsonConfig).toString(),response);
            } else {
                 renderText(JSONObject.fromObject(obj).toString(),response);
            }
        }
    }    
}
