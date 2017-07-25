package com.ejuzuo.server.promoRecord.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.option.PromoRecordOption;

public interface PromoRecordManager {
    ModelResult<Boolean>  deleteById(Integer id);

    ModelResult<PromoRecord> save(PromoRecord record);

    ModelResult<PromoRecord> queryById(Integer id);

    ModelResult<Boolean> updateById(PromoRecord record);

    ModelResult<PromoRecord> checkCode(PromoRecord record);

    ModelResult<PromoRecord> queryByCode(String code);

    ModelResult<Integer> count(PromoRecord record);

	PageResult<PromoRecord> queryPage(DataPage<PromoRecord> dataPage, PromoRecordOption qVo);
}