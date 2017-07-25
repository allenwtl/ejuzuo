package com.ejuzuo.server.codevalue.manager.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;

/**
 * Created by tianlun.wu on 2016/4/5 0005.
 */

@Component
public class CodeValueManagerImpl implements CodeValueManager {
    private static final Logger logger = LoggerFactory.getLogger(CodeValueManagerImpl.class);

    @Resource
    private GenericDao dao;

    @Override
	public ModelResult<CodeValue> query(String collectionCode, String valueCode) {
		ModelResult<CodeValue> result = new ModelResult<CodeValue>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collectionCode", collectionCode);
		map.put("valueCode", valueCode);
		CodeValue obj = dao.queryUnique("CodeValueMapper.query", map);
		result.setModel(obj);
		return result;
	}

	@Override
	public ModelResult<Boolean> update(CodeValue obj) {
		try {
			obj.setUpdateTime(Calendar.getInstance());
			dao.updateByObj("CodeValueMapper.update", obj);
		} catch (Exception e) {
			logger.error("CodeValueMapper.update 报错", e);
            return new ModelResult<>().withError("exception", "更新报错");
		}
		return new ModelResult<>(Boolean.TRUE);
	}
	
    @Override
    public ModelResult<List<CodeValue>> queryList(CollectionCode code, String valueCode, String parentCode) {
        Map<String, Object> param = new HashMap<>();
        if (code != null) {
            param.put("collectionCode", code.getIndex());
        }

        if (valueCode != null) {
            param.put("valueCode", valueCode);
        }

        if (parentCode != null) {
            param.put("parentCode", parentCode);
        }
        return new ModelResult<>(dao.queryList("CodeValueMapper.selectList", param));
    }

    @Override
    public ModelResult<Boolean> save(CodeValue codeValue) {
        try {
            codeValue.setCreateTime(Calendar.getInstance());
            codeValue.setUpdateTime(Calendar.getInstance());
            dao.insertAndSetupId("CodeValueMapper.save", codeValue);
        } catch (Exception e) {
            logger.error("CodeValueMapper.save 报错", e);
            return new ModelResult<>().withError("exception", "保存报错");
        }
        return new ModelResult<>(Boolean.TRUE);
    }

	@Override
	public DataPage<CodeValue> queryPage(DataPage<CodeValue> dataPage, CodeValue qVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		if (qVo != null) {
			if (qVo.getCollectionCode() != null) {
				map.put("collectionCode", qVo.getCollectionCode());
			}
			if (qVo.getValueCode() != null) {
				map.put("valueCode", qVo.getValueCode());
			}
			if (qVo.getValueName() != null) {
				map.put("valueName", "%"+qVo.getValueName()+"%");
			}
			if (qVo.getParentCode() != null) {
				map.put("parentCode", qVo.getParentCode());
			}
			if (qVo.getStatus() != null) {
				map.put("status", qVo.getStatus());
			}
		}
		dataPage = dao.queryPage(
				"CodeValueMapper.queryByPageCount",
				"CodeValueMapper.queryByPage", map, dataPage);
		return dataPage;
	}

    @Override
    public ModelResult<CodeValue> queryOne(CollectionCode code, String valueCode, String parentCode) {
        Map<String, Object> param = new HashMap<>();
        if(code !=null){
            param.put("collectionCode", code.getIndex());
        }

        if(valueCode!=null){
            param.put("valueCode", valueCode);
        }

        if(parentCode!=null){
            param.put("parentCode", valueCode);
        }
        List<CodeValue> list = dao.queryList("CodeValueMapper.queryOne", param);
        if(list!=null && !list.isEmpty()){
            return new ModelResult<>(list.get(0));
        }
        return new ModelResult<>(null);
    }

	@Override
	public ModelResult<Boolean> updateStatus(String collectionCode, String valueCode, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collectionCode", collectionCode);
		map.put("valueCode", valueCode);
		map.put("status", status);
		Boolean updated = false;
		try {
			int affected = dao.update("CodeValueMapper.updateStatus", map);
			if (affected >= 1) {
				updated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelResult<Boolean>(updated);
	}
}
