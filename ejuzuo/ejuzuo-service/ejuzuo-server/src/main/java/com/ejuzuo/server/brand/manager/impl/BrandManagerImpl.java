package com.ejuzuo.server.brand.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.server.brand.manager.BrandManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */

@Component
public class BrandManagerImpl implements BrandManager {

    private static final Logger logger = LoggerFactory.getLogger(BrandManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;


    @Override
    public ModelResult<Brand> save(Brand brand) {
        dao.updateByObj("BrandMapper.save", brand);
        return new ModelResult<>(brand);
    }

    @Override
    public ModelResult<List<Brand>> queryList() {
    	ModelResult<List<Brand>> result = new ModelResult<List<Brand>>();
    	List<Brand> model = dao.queryList("BrandMapper.selectAll");
    	result.setModel(model);
        return result;
    }

    @Override
    public ModelResult<Boolean> updateById(Brand brand) {
        brand.setUpdateTime(Calendar.getInstance());
        try {
            int result = dao.updateByObj("BrandMapper.updateById", brand);
            if (result == 1) {
                return new ModelResult<>(Boolean.TRUE);
            }
            return new ModelResult<>().withError("exception","更新失败");
        } catch (Exception e) {
            logger.error("BrandMapper.updateById 更新报错", e);
            return new ModelResult<>().withError("exception", e.getMessage());
        }
    }

    @Override
    public ModelResult<Brand> selectById(Integer id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("id", id);
    	Brand obj = dao.queryUnique("BrandMapper.selectById", map);
        return new ModelResult<>(obj);
    }

	@Override
	public DataPage<Brand> queryPage(DataPage<Brand> dataPage, Brand qVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		if (qVo != null) {
			if (qVo.getId() != null) {
				map.put("id", qVo.getId());
			}
			if (qVo.getName() != null) {
				map.put("name", "%"+qVo.getName()+"%");
			}
			if (qVo.getStatus() != null) {
				map.put("status", qVo.getStatus());
			}
		}
		return dao.queryPage("BrandMapper.countPage", "BrandMapper.queryPage", map, dataPage);
	}

	@Override
	public Integer delete(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return dao.update("BrandMapper.delete", map);
	}

	@Override
	public ModelResult<List<Brand>> queryAllNomal() {
		ModelResult<List<Brand>> result = new ModelResult<List<Brand>>();
    	List<Brand> model = dao.queryList("BrandMapper.queryAllNomal");
    	result.setModel(model);
        return result;
	}

	@Override
	public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		Boolean updated = false;
		try {
			int affected = dao.update("BrandMapper.updateStatus", map);
			if (affected >= 1) {
				updated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelResult<Boolean>(updated);
	}
}
