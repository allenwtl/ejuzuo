package com.ejuzuo.server.promoRecord.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.option.PromoRecordOption;
import com.ejuzuo.common.service.PromoRecordService;
import com.ejuzuo.server.promoRecord.manager.PromoRecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/5/6 0006.
 */

@Service("promoRecordService")
public class PromoRecordServiceImpl implements PromoRecordService {

    @Resource
    private PromoRecordManager promoRecordManager;

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        return promoRecordManager.deleteById(id);
    }

    @Override
    public ModelResult<PromoRecord> save(PromoRecord record) {
    	//增加防重复提交机制
    	PromoRecord pr = promoRecordManager.queryByCode(record.getPromoCode()).getModel();
    	if (pr == null) {
    		return promoRecordManager.save(record);    		
    	} else {    		
    		return new ModelResult<PromoRecord>(pr);
    	}       
    }

    @Override
    public ModelResult<PromoRecord> queryById(Integer id) {
        return promoRecordManager.queryById(id);
    }

    @Override
    public ModelResult<Boolean> updateById(PromoRecord record) {
        return promoRecordManager.updateById(record);
    }

    @Override
    public ModelResult<PromoRecord> checkCode(PromoRecord record) {
    	return promoRecordManager.checkCode(record);
    }

	@Override
	public PageResult<PromoRecord> queryPage(DataPage<PromoRecord> dataPage, PromoRecordOption qVo) {
		return promoRecordManager.queryPage(dataPage,qVo);
	}
}
