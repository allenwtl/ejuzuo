package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Brand;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public interface BrandService {

    ModelResult<List<Brand>> queryList();

    ModelResult<Brand> save(Brand brand);

    ModelResult<Boolean> updateById(Brand brand);

    ModelResult<Brand> selectById(Integer id);

	PageResult<Brand> queryPage(DataPage<Brand> dataPage, Brand qVo);

	ModelResult<Integer> delete(Integer id);

	ModelResult<List<Brand>> queryAllNomal();

	ModelResult<Boolean> updateStatus(Integer id, Integer status);

	void updateCorporation(Integer id, String corporation);

}
