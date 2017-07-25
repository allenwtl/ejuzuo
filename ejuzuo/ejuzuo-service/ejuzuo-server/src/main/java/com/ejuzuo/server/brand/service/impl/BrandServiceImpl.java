package com.ejuzuo.server.brand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.memcachedclient.MemcachedClient;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.service.BrandService;
import com.ejuzuo.common.util.MemcachedKeyUtil;
import com.ejuzuo.server.brand.manager.BrandManager;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */

@Service("brandService")
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandManager brandManager ;
    
    @Resource(name = "memcachedClient")
    private MemcachedClient memcachedClient;

    @Override
    public ModelResult<List<Brand>> queryList() {
        return brandManager.queryList();
    }

    @Override
    public ModelResult<Brand> save(Brand brand) {
        ModelResult<Brand> result = brandManager.save(brand);
        if (result.isSuccess() && result.getModel() != null) {
        	memcachedClient.delete(MemcachedKeyUtil.ALL_BRAND_LIST);
		}
        return result;
    }

    @Override
    public ModelResult<Boolean> updateById(Brand brand) {
    	ModelResult<Boolean> result = brandManager.updateById(brand);
        if (result.isSuccess() && result.getModel().equals(Boolean.TRUE)) {
        	memcachedClient.delete(MemcachedKeyUtil.ALL_BRAND_LIST);
		}
        return result;
    }

    @Override
    public ModelResult<Brand> selectById(Integer id) {
        return brandManager.selectById(id);
    }

	@Override
	public PageResult<Brand> queryPage(DataPage<Brand> dataPage, Brand qVo) {
		PageResult<Brand> result = new PageResult<Brand>();
		dataPage = brandManager.queryPage(dataPage,qVo);
		result.setPage(dataPage);
		return result;
	}

	@Override
	public ModelResult<Integer> delete(Integer id) {
		ModelResult<Integer> result = new ModelResult<Integer>();
		Integer model = brandManager.delete(id);
		memcachedClient.delete(MemcachedKeyUtil.ALL_BRAND_LIST);
		result.setModel(model);
		return result;
	}

	@Override
	public ModelResult<List<Brand>> queryAllNomal() {
		return brandManager.queryAllNomal();
	}

	@Override
	public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
		ModelResult<Boolean> result = brandManager.updateStatus(id,status);
        if (result.isSuccess() && result.getModel().equals(Boolean.TRUE)) {
        	memcachedClient.delete(MemcachedKeyUtil.ALL_BRAND_LIST);
		}
        return result;
	}

	@Override
	public void updateCorporation(Integer id, String corporation) {
		Brand obj = new Brand();
		obj.setId(id);
		obj.setCorporation(corporation);
		brandManager.updateById(obj);
	}
}
