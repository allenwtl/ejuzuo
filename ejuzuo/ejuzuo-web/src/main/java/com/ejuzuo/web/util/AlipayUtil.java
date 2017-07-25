package com.ejuzuo.web.util;

import com.ejuzuo.common.util.MD5;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by tianlun.wu on 2016/5/11 0011.
 */
public class AlipayUtil {

    /**
     * 功能：生成签名结果
     *
     * @param sArray
     *            要签名的数组
     * @param key
     *            安全校验码
     * @return 签名结果字符串
     */
    public static String buildMysign(Map sArray, String key) {
        sArray = paraFilter(sArray);
        String prestr = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + key; // 把拼接后的字符串再与安全校验码直接连接起来
        String mysign = MD5.HPEncode(prestr, "UTF-8");
        return mysign;
    }

    /**
     * 功能：除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map paraFilter(Map sArray) {
        List keys = new ArrayList(sArray.keySet());
        Map sArrayNew = new HashMap();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) sArray.get(key);

            if (value.equals("") || value == null || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            sArrayNew.put(key, value);
        }
        System.out.println(sArrayNew.toString());
        return sArrayNew;
    }

    /**
     * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 功能：用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     *
     * @param partner
     *            合作身份者ID
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */

/*    public static String query_timestamp(String partner) throws MalformedURLException, DocumentException, IOException {
        String strUrl = "https://mapi.alipay.com/gateway.do?service=query_timestamp&partner=" + partner;
        StringBuffer buf1 = new StringBuffer();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());
        List<Node> nodeList = doc.selectNodes("//alipay*//*");
        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp*//*");
                for (Node node1 : nodeList1) {
                    buf1.append(node1.getText());
                }
            }
        }
        return buf1.toString();
    }*/

    public static String sign(Map params, String privateKey) {
        Properties properties = new Properties();
        for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            Object value = params.get(name);

            if ((name != null) && (!name.equalsIgnoreCase("sign"))) {
                if (name.equalsIgnoreCase("sign_type")) {
                    continue;
                }

                properties.setProperty(name, value.toString());
            }
        }
        String content = getSignatureContent(properties);
        return sign(content, privateKey);
    }

    @SuppressWarnings("unchecked")
    public static String getSignatureContent(Properties properties) {
        StringBuffer content = new StringBuffer();
        List keys = new ArrayList(properties.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); ++i) {
            String key = (String) keys.get(i);
            String value = properties.getProperty(key);
            content.append(((i == 0) ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static String sign(String content, String privateKey) {
        if (privateKey == null) {
            return null;
        }
        String signBefore = content + privateKey;
        return MD5.HPEncode(signBefore, "UTF-8");
    }



}
