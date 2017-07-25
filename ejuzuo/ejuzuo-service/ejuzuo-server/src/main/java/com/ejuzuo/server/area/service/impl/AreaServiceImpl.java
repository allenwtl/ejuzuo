package com.ejuzuo.server.area.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.domain.Area;
import com.ejuzuo.common.service.AreaService;
import com.ejuzuo.server.area.manager.AreaManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */

@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaManager areaManager;


    @Override
    public ModelResult<Boolean> save(Area area) {
        return areaManager.save(area);
    }

    @Override
    public ModelResult<List<Area>> queryAll(Integer id) {
        return areaManager.queryAll(id);
    }

    @Override
    public ModelResult<List<Area>> queryAll() {
        return areaManager.queryAll();
    }

    @Override
    public ModelResult<Area> queryProvince(Integer provinceId) {
        return areaManager.queryProvince(provinceId);
    }

    @Override
    public ModelResult<Area> queryCity(Integer cityId, Integer parentId) {
        return areaManager.queryCity(cityId, parentId);
    }
}
