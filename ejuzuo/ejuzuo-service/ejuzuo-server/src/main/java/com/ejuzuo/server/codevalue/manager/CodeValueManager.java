package com.ejuzuo.server.codevalue.manager;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.CodeValue;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/5 0005.
 */
public interface CodeValueManager {

	ModelResult<Boolean> save(CodeValue codeValue) ;

	DataPage<CodeValue> queryPage(DataPage<CodeValue> dataPage, CodeValue qVo);

	ModelResult<CodeValue> query(String collectionCode, String valueCode);

	ModelResult<Boolean> update(CodeValue obj);
	
    /**
     * 直接查询符合条件的所有
     * @param code
     * @param valueCode
     * @param parentCode
     * @return
     */
    ModelResult<List<CodeValue>> queryList(CollectionCode code, String valueCode, String parentCode);

    ModelResult<CodeValue> queryOne(CollectionCode code, String valueCode, String parentCode);

	ModelResult<Boolean> updateStatus(String collectionCode, String valueCode, Integer status);

}
