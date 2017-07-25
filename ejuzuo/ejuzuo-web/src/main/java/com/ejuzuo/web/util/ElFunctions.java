package com.ejuzuo.web.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.Set;

/**
 * Created by allen on 2016/4/24.
 */
public class ElFunctions {

    public static String getJSONValue(String obj, String key) {
        return JSONObject.parseObject(obj, Feature.OrderedField).getString(key);
    }

    public static JSONObject toJSONObject(String obj){
        JSONObject jsonObject = JSONObject.parseObject(obj, Feature.OrderedField);
        return jsonObject == null ? new JSONObject():jsonObject;
    }

    public static Set<String> toKeySet(String obj){
        return JSONObject.parseObject(obj, Feature.OrderedField).keySet();
    }

    public static JSONArray getJSONArray(String obj){
        return JSONArray.parseArray(obj);
    }

}
