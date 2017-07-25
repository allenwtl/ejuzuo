package com.ejuzuo.server.brand.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Brand;

import java.util.List;

public interface BrandManager {

    ModelResult<Brand> save(Brand brand);

    ModelResult<List<Brand>> queryList();

    ModelResult<Boolean> updateById(Brand brand);

    ModelResult<Brand> selectById(Integer id);

	DataPage<Brand> queryPage(DataPage<Brand> dataPage, Brand qVo);

	Integer delete(Integer id);

	ModelResult<List<Brand>> queryAllNomal();

	ModelResult<Boolean> updateStatus(Integer id, Integer status);

}