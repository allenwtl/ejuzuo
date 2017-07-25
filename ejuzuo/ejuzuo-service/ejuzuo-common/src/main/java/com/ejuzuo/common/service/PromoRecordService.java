package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.option.PromoRecordOption;

/**
 * Created by tianlun.wu on 2016/5/6 0006.
 */
public interface PromoRecordService  {

    ModelResult<Boolean> deleteById(Integer id);

    ModelResult<PromoRecord> save(PromoRecord record);

    ModelResult<PromoRecord> queryById(Integer id);

    ModelResult<Boolean> updateById(PromoRecord record);

    ModelResult<PromoRecord> checkCode(PromoRecord record);

	PageResult<PromoRecord> queryPage(DataPage<PromoRecord> dataPage, PromoRecordOption qVo);
    
}
