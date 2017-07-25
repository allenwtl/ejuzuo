package com.ejuzuo.client.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.memcachedclient.MemcachedClient;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.service.BrandService;
import com.ejuzuo.common.util.MemcachedKeyUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/31 0031.
 */
@Service("brandServiceClient")
public class BrandServiceClient implements BrandService {

    @Resource(name = "brandService")
    private BrandService brandService;

    @Resource(name = "memcachedClient")
    private MemcachedClient memcachedClient;

    @Override
    public ModelResult<List<Brand>> queryList() {
        return brandService.queryList();
    }

    @Override
    public ModelResult<Brand> save(Brand brand) {
        return brandService.save(brand);
    }

    @Override
    public ModelResult<Boolean> updateById(Brand brand) {
        return brandService.updateById(brand);
    }

    @Override
    public ModelResult<Brand> selectById(Integer id) {
        return brandService.selectById(id);
    }

    @Override
    public PageResult<Brand> queryPage(DataPage<Brand> dataPage, Brand qVo) {
        return brandService.queryPage(dataPage,qVo);
    }

    @Override
    public ModelResult<Integer> delete(Integer id) {
        return brandService.delete(id);
    }

    @Override
    public ModelResult<List<Brand>> queryAllNomal() {
        List<Brand> brandList = memcachedClient.get(MemcachedKeyUtil.ALL_BRAND_LIST);
        if(brandList == null){
            brandList = brandService.queryAllNomal().getModel();
            memcachedClient.set(MemcachedKeyUtil.ALL_BRAND_LIST, 2 * 60 * 60, brandList);
            return new ModelResult<>(brandList);
        }
        return new ModelResult<>(brandList);
    }

    @Override
    public ModelResult<Boolean> updateStatus(Integer id, Integer status) {
        return brandService.updateStatus(id,status);
    }

	@Override
	public void updateCorporation(Integer id, String corporation) {
		brandService.updateCorporation(id, corporation);
	}
}
