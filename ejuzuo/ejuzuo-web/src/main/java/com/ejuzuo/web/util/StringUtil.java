package com.ejuzuo.web.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/5/11 0011.
 */
public class StringUtil {

    public static Map<String, String> getGMap(Map<String, String[]> parm) {
        HashMap parmMap = new HashMap();
        Iterator var2 = parm.keySet().iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            String[] values = (String[])parm.get(s);
            parmMap.put(s, values[0]);
        }

        return parmMap;
    }

}
