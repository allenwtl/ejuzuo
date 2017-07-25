package com.ejuzuo.server.promoRecord.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.PromoRecord;
import com.ejuzuo.common.option.PromoRecordOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.promoRecord.manager.PromoRecordManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/5/6 0006.
 */

@Repository
public class PromoRecordManagerImpl implements PromoRecordManager {

    private static final Logger logger = LoggerFactory.getLogger(PromoRecordManagerImpl.class);

    @Autowired
    private GenericDao dao;

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        int size = dao.update("PromoRecordMapper.deleteById", param );
        if(size == 1){
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public ModelResult<PromoRecord> save(PromoRecord record) {
        record.setUpdateTime(Calendar.getInstance());
        record.setCreateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("PromoRecordMapper.save", record);
        return new ModelResult<>(record);
    }

    @Override
    public ModelResult<PromoRecord> queryById(Integer id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("id", id);
        return new ModelResult<>(dao.queryUnique("PromoRecordMapper.selectById", map));
    }

    @Override
    public ModelResult<Boolean> updateById(PromoRecord record) {
        record.setUpdateTime(Calendar.getInstance());
        int size = dao.updateByObj("PromoRecordMapper.updateById", record);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public ModelResult<PromoRecord> checkCode(PromoRecord record) {
        PromoRecord promoRecordFromDB = queryByCode(record.getPromoCode()).getModel();
        if(promoRecordFromDB == null ){
            logger.info("code:[{}], 不存在", record.getPromoCode());
            return new ModelResult<PromoRecord>().withError("exception", "code不存在");
        }

        if(promoRecordFromDB.getStatus() == 1){
            PromoRecord temp = new PromoRecord();
            temp.setId(promoRecordFromDB.getId());
            temp.setViewCount(promoRecordFromDB.getViewCount()+1);
            temp.setUpdateTime(Calendar.getInstance());
            this.updateById(temp);
            logger.info("code:[{}], 已经使用过了", record.getPromoCode());
            return new ModelResult<PromoRecord>(promoRecordFromDB).withError("exception", "code已经使用过了！");
        }

        //应福星要求，改成每天5次机会得积分。
        int result = this.count(record).getModel();
        if(result >= 5 ){
            logger.info("分享业务：用户:[{}] 今天获取的次数超过了5次", record.getMemberId());
            return new ModelResult<PromoRecord>(promoRecordFromDB).withError("exception", "分享已经超过5次！");
        }


        JSONObject jsonObjectDB = JSONObject.parseObject(promoRecordFromDB.getPromoInfo());
        JSONObject jsonObject = JSONObject.parseObject(record.getCallbackInfo());
        //目前IP的获取方法有误，导致所有的都是一样的ip。
        if(jsonObjectDB.getString("ip").equalsIgnoreCase(jsonObject.getString("ip"))){
            logger.info("分享人与点击人IP相同，有作弊可能！");
            return new ModelResult<PromoRecord>(promoRecordFromDB).withError("exception", "分享人与点击人IP相同，有作弊可能！");
        }

        Map<String,Object> param = JSONUtils.object2MapSpecail(record);
        param.put("updateTime", Calendar.getInstance());
        int size = dao.update("PromoRecordMapper.checkCode", param);
        if(size == 1){
        	//本次分享成功！
            return new ModelResult<PromoRecord>(promoRecordFromDB);
        }
        return new ModelResult<PromoRecord>(promoRecordFromDB).withError("exception", "其他错误！");
    }

    @Override
    public ModelResult<PromoRecord> queryByCode(String code) {
        Map<String,Object> param = new HashMap<>();
        param.put("promoCode", code);
        List<PromoRecord> list = dao.queryList("PromoRecordMapper.queryList", param);
        if(list == null || list.isEmpty()){
            return new ModelResult<>(null);
        }
        return new ModelResult<>(list.get(0));
    }

    @Override
    public ModelResult<Integer> count(PromoRecord record) {
        Map<String,Object> param = new HashMap<>();
        param.put("memberId", record.getMemberId());
        param.put("status", 1);
        param.put("startTime", DateUtil.getTheDayZero());
        param.put("endTime", DateUtil.getTheDayMidnight());
        return new ModelResult<>(dao.queryCount("PromoRecordMapper.count", param));
    }

	@Override
	public PageResult<PromoRecord> queryPage(DataPage<PromoRecord> dataPage, PromoRecordOption qVo) {
		Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
		if (qVo != null) {
			Calendar beginDate = qVo.getBeginDate();
			Calendar endDate = qVo.getEndDate();
			if (beginDate != null) {
				beginDate.set(Calendar.HOUR_OF_DAY, 0);
				beginDate.set(Calendar.MINUTE, 0);
				beginDate.set(Calendar.SECOND, 0);
				beginDate.set(Calendar.MILLISECOND, 0);
				map.put("beginDate", beginDate);
			}
			if (endDate != null) {
				endDate.set(Calendar.HOUR_OF_DAY, 23);
				endDate.set(Calendar.MINUTE, 59);
				endDate.set(Calendar.SECOND, 59);
				endDate.set(Calendar.MILLISECOND, 999);
				map.put("endDate", endDate);
			}
		}
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		dataPage = dao.queryPage(
				"PromoRecordMapper.countPage", 
				"PromoRecordMapper.queryPage", map, dataPage);
		PageResult<PromoRecord> result = new PageResult<PromoRecord>();
		result.setPage(dataPage);
		return result;
	}
}
