package com.ejuzuo.server.area.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.Area;
import com.ejuzuo.server.area.manager.AreaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */

@Component
public class AreaManagerImpl implements AreaManager {

    private static final Logger logger = LoggerFactory.getLogger(AreaManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public ModelResult<Boolean> save(Area area) {
        dao.insertAndReturnAffectedCount("AreaMapper.save", area);
        return null;
    }

    @Override
    public ModelResult<List<Area>> queryAll(Integer id) {

        if (id == null) {
            return new ModelResult<>(dao.queryList("AreaMapper.selectProvice"));
        }
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", id);
        return new ModelResult<>(dao.queryList("AreaMapper.selectCity", param));
    }

    @Override
    public ModelResult<List<Area>> queryAll() {
        return new ModelResult<>(dao.queryList("AreaMapper.selectAll"));
    }

    @Override
    public ModelResult<Area> queryProvince(Integer provinceId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", provinceId);
        return new ModelResult<>(dao.queryUnique("AreaMapper.selectProvice", param));
    }

    @Override
    public ModelResult<Area> queryCity(Integer cityId,Integer parentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", cityId);
        param.put("parentId", parentId);
        return new ModelResult<>(dao.queryUnique("AreaMapper.selectCity", param));
    }
}
