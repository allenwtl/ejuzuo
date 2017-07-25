package com.ejuzuo.server.area.manager;

import com.aicai.appmodel.domain.result.ModelResult;
import com.ejuzuo.common.domain.Area;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public interface AreaManager {

    ModelResult<Boolean> save(Area area);

    ModelResult<List<Area>> queryAll(Integer id);

    ModelResult<List<Area>> queryAll();

    ModelResult<Area> queryProvince(Integer provinceId);

    ModelResult<Area> queryCity(Integer cityId, Integer parentId);
}
