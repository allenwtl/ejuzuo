package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.CodeValue;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/5 0005.
 */
public interface CodeValueService {

	PageResult<CodeValue> queryPage(DataPage<CodeValue> dataPage, CodeValue qVo);
	
	ModelResult<CodeValue> query(String collectionCode, String valueCode);
	
	ModelResult<Boolean> save(CodeValue obj);
	
	ModelResult<Boolean> update(CodeValue obj);
	
    ModelResult<List<CodeValue>> queryList(CollectionCode code, String valueCode, String parentCode);


    ModelResult<CodeValue> queryOne(CollectionCode code, String valueCode, String parentCode);

	ModelResult<Boolean> updateStatus(String collectionCode, String valueCode, Integer status);
}
