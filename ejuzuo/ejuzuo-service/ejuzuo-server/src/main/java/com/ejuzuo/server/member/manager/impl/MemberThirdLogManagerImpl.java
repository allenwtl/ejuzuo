package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.ThirdType;
import com.ejuzuo.common.domain.MemberThirdLogin;
import com.ejuzuo.common.option.MemberThirdLoginOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.member.manager.MemberThirdLoginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class MemberThirdLogManagerImpl implements MemberThirdLoginManager {
    private static final Logger logger = LoggerFactory.getLogger(MemberThirdLogManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public ModelResult<MemberThirdLogin> save(MemberThirdLogin record) {
        record.setCreateTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        try {
            int result = dao.insertAndReturnAffectedCount("MemberThirdLoginMapper.save", record);
            if (result == 1) {
                return new ModelResult<>(record);
            }
        } catch (Exception e) {
            logger.info("保存第三方:[{}]信息失败", ThirdType.findByIndex(record.getThirdType()).getDescription(), e);
        }



        return new ModelResult<>().withError("exception", "登录异常");
    }

    @Override
    public PageResult<MemberThirdLogin> queryPage(DataPage<MemberThirdLogin> dataPage, MemberThirdLoginOption qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo.getMemberId() != null) {
            map.put("memberId", qVo.getMemberId());
        }
        if (qVo.getThirdType() != null) {
            map.put("thirdType", qVo.getThirdType());
        }
        if (qVo.getThirdId() != null) {
            map.put("thirdId", qVo.getThirdId());
        }
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
        dataPage = dao.queryPage("MemberThirdLoginMapper.countPage", "MemberThirdLoginMapper.queryPage", map, dataPage);
        PageResult<MemberThirdLogin> result = new PageResult<MemberThirdLogin>();
        result.setPage(dataPage);
        return result;
    }

    @Override
    public ModelResult<MemberThirdLogin> queryByOption(MemberThirdLoginOption option) {

        return new ModelResult<>(dao.queryUnique("MemberThirdLoginMapper.queryList", JSONUtils.object2MapSpecail(option)));
    }
}
