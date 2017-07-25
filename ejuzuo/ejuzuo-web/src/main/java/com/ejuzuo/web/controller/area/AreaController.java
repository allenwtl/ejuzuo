package com.ejuzuo.web.controller.area;

import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.Area;
import com.ejuzuo.common.service.AreaService;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */


@Controller
@RequestMapping("area")
public class AreaController extends BaseController {

    @Resource
    private AreaService areaService;

    private static String city = "city";


    @RequestMapping("/city/{province}")
    @ResponseBody
    public JSONObject queryCity(@PathVariable Integer province) {
        JSONObject jsonObject = new JSONObject();
        if (province == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "没有传入参数");
            return jsonObject;
        }

/*        String key = city + "-" + province;
        List<Area> areaList = memcachedClient.get(key);
        if (areaList == null) {
            areaList = areaService.queryAll(province).getModel();
            memcachedClient.set(key, 60 * 60 * 24, areaList);
        }*/
        List<Area> areaList = areaService.queryAll(province).getModel();
        jsonObject.put("code", 222);
        jsonObject.put("model", areaList);
        return jsonObject;
    }

}
