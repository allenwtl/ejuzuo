package com.ejuzuo.server.codevalue.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/5 0005.
 */

@Service("codeValueService")
public class CodeValueServiceImpl implements CodeValueService {

    @Resource
    private CodeValueManager codeValueManager ;

    @Override
	public ModelResult<CodeValue> query(String collectionCode, String valueCode) {
		return codeValueManager.query(collectionCode,valueCode);
	}

	@Override
	public ModelResult<Boolean> save(CodeValue obj) {
		return codeValueManager.save(obj);
	}

	@Override
	public ModelResult<Boolean> update(CodeValue obj) {
		return codeValueManager.update(obj);
	}
	
    @Override
    public ModelResult<List<CodeValue>> queryList(CollectionCode code, String valueCode, String parentCode) {
        return codeValueManager.queryList(code, valueCode, parentCode);
    }

	@Override
	public PageResult<CodeValue> queryPage(DataPage<CodeValue> dataPage, CodeValue qVo) {
		PageResult<CodeValue> result = new PageResult<CodeValue>();
		dataPage = codeValueManager.queryPage(dataPage, qVo);
		result.setPage(dataPage);
		return result;
	}

    @Override
    public ModelResult<CodeValue> queryOne(CollectionCode code, String valueCode, String parentCode) {
        return codeValueManager.queryOne(code, valueCode, parentCode);
    }

	@Override
	public ModelResult<Boolean> updateStatus(String collectionCode, String valueCode, Integer status) {
		return codeValueManager.updateStatus(collectionCode,valueCode,status);
	}
}
