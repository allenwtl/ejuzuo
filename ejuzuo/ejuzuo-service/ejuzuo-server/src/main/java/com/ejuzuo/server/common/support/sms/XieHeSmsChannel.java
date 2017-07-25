/**
 * 
 */
package com.ejuzuo.server.common.support.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aicai.appmodel.domain.result.BaseResult;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.util.MD5;

@Component("xieHeSmsChannel")
public class XieHeSmsChannel  extends SmsChannel {
	@Value("${xiehe.enabled}")
	private boolean enabled;
	@Value("${xiehe.account}")
	private String account;
	@Value("${xiehe.pass}")
	private String pass;
	@Value("${xiehe.url}")
	private String url;
	
	private Map<Integer, String> errorMap = new HashMap<Integer, String>(10);
	
	public XieHeSmsChannel() {
		gateway = SmsChannel.GATEWAY_XIEHE;
		errorMap.put(200, "短信发送成功");
		errorMap.put(301, "账号或密码错误");
		errorMap.put(302, "账号已停用");
		errorMap.put(303, "余额不足");
		errorMap.put(304, "手机号码为空或者超过最大发送数量");
		errorMap.put(305, "数据认证错误");
		errorMap.put(306, "短信内容为空或超长");
		errorMap.put(307, "扩展码不正确");
		errorMap.put(308, "批次号过长");
		errorMap.put(309, "含有敏感词");
		errorMap.put(998, "响应超时");
		errorMap.put(999, "其他未知错误");
	}
	
	
	@Override
    protected void initGateway() {       
    }
	
	@Override
    protected void initSmsPackage() {
    }

	public Integer getGateway() {
		return gateway;
	}

	@Override
	@SuppressWarnings("rawtypes")
    public BaseResult sendMessage(Sms sms) {       
		BaseResult result = new BaseResult(); 
		if (!enabled) {
			logger.info("发送短信到{},通道开关没有打开！", sms.getMobileNo());
			return result;
		}
    	try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpParams params = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);    // 设置连接超时5秒
            HttpConnectionParams.setSoTimeout(params, 10000);   // 请求超时
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("account", account);
            paramMap.put("passwd", pass);
            paramMap.put("mobiles", sms.getMobileNo());
            paramMap.put("content", sms.getContent());
            paramMap.put("01", "");
            paramMap.put("batchno", "");
            paramMap.put("digest", MD5.md5Encode(sms.getMobileNo() + sms.getContent()));

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(paramMap.keySet().size());
            for (String key : paramMap.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, paramMap.get(key) == null ? "" : paramMap.get(key)));
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }           
        
            HttpEntity entity = response.getEntity();
            if (entity == null) {
            	logger.info("【谐和科技】发送短信,号码【{}】无结果返回", sms.getMobileNo());
            	return result.withError("0", "【谐和科技】无结果返回");
            }
            
            JSONObject jsobject = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
            
            String code = jsobject.get("code").toString();
            String batchNo = jsobject.get("batchno").toString();
            
			logger.info("【谐和科技】发送短信,短信内容【{}】号码【{}】发送结果【{}】谐和批次号【{}】", new Object[] {
					sms.getContent(), sms.getMobileNo(), code, batchNo });
            
			if ("200".equals(code)) {
            	//result.setSuccess(true);
            	return result;
			}			
			String errorMsg = errorMap.get(Integer.valueOf(code));
			if (StringUtils.isEmpty(errorMsg)) {
				errorMsg = "未知！";
			}			
			logger.error("谐和科技】" + errorMsg);
			return result.withError(code, errorMsg);            
        } catch (Exception e) {
            logger.error("【谐和科技】发送短信产 生异常", e);
            return result.withError("0", "未知异常:"+e.getMessage());
        }        
       
    }
	
	public static void main(String[] args) throws Exception {
    	String content = "尊敬的用户，您本次操作的验证码：456123";    	
    	XieHeSmsChannel instance =new XieHeSmsChannel();   
    	instance.account = "xiehe2";
    	instance.pass = "iwEbf1br";
    	instance.url = "http://webservice.wjxjt.com:8080/sms/sendsms.jsp";
    	Sms sms1 = new Sms("13428981004", content);
//    	sms1 = new Sms("13632834915", content, null, 0);
//    	sms1 = new Sms("18127096596", content, null, 0);
    	instance.sendMessage(sms1);
	}
	

}
