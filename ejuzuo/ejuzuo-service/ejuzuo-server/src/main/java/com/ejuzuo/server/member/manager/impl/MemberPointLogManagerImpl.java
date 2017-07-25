package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.option.MemberPointLogOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.MemberPointLogVO;
import com.ejuzuo.server.member.manager.MemberPointLogManager;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class MemberPointLogManagerImpl implements MemberPointLogManager {

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("MemberPointLogMapper.deleteById", id);
    }

    @Override
    public MemberPointLog save(MemberPointLog record) {
        record.setCreateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("MemberPointLogMapper.save", record);
        return record;
    }

    @Override
    public MemberPointLog selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("MemberPointLogMapper.selectById", param);
    }

    @Override
    public int updateById(MemberPointLog record) {
        return dao.updateByObj("MemberPointLogMapper.updateById", record);
    }

    @Override
    public ModelResult<List<MemberPointLog>> queryList(MemberPointLogOption option) {
        return new ModelResult<>(dao.queryList("MemberPointLogMapper.queryList", JSONUtils.object2MapSpecail(option)));
    }

    @Override
    public ModelResult<DataPage<MemberPointLog>> queryByPage(MemberPointLogOption option, DataPage<MemberPointLog> dataPage) {
        Map<String, Object> map = JSONUtils.object2MapSpecail(option);
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (option != null) {
            Calendar startTime = option.getStartTime();
            Calendar endTime = option.getEndTime();
            if (startTime != null) {
                startTime.set(Calendar.HOUR_OF_DAY, 0);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.SECOND, 0);
                startTime.set(Calendar.MILLISECOND, 0);
                map.put("startTime", startTime);
            }
            if (endTime != null) {
                endTime.set(Calendar.HOUR_OF_DAY, 23);
                endTime.set(Calendar.MINUTE, 59);
                endTime.set(Calendar.SECOND, 59);
                endTime.set(Calendar.MILLISECOND, 999);
                map.put("endTime", endTime);
            }
        }
        return new ModelResult<>(dao.queryPage("MemberPointLogMapper.queryPageCount", "MemberPointLogMapper.queryPage",
                map, dataPage));
    }

    @Override
    public ModelResult<DataPage<MemberPointLogVO>> queryVOByPage(MemberPointLogOption option, DataPage<MemberPointLogVO> dataPage) {
        Map<String, Object> map = JSONUtils.object2MapSpecail(option);
        DataPage<MemberPointLog> memberPointLogDataPage = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        memberPointLogDataPage = dao.queryPage("MemberPointLogMapper.queryPageCount", "MemberPointLogMapper.queryPage",
                map, memberPointLogDataPage);

        List<MemberPointLogVO> pointLogVOList = new ArrayList<>();
        MemberPointLogVO vo = null;
        for (MemberPointLog item : memberPointLogDataPage.getDataList()) {
            vo = new MemberPointLogVO();

            try {
                BeanUtils.copyProperties(vo, item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if (item.getTransType() == MemberPointLogTransType.DOWNLOAD.getIndex()) {
                vo.setShowAmount("<span class=\"num\" style=\"color:green;\">-" + item.getAmount() +"</span>");
            } else {
                vo.setShowAmount("<span class=\"num\" style=\"color:red;\">"+item.getAmount() +"</span>");
            }

            vo.setShowTime(DateUtil.getDateStringByZdGs(item.getCreateTime().getTime(), "yyyy-MM-dd HH:mm") );

            pointLogVOList.add(vo);
        }

        dataPage.setDataList(pointLogVOList);
        dataPage.setTotalCount(memberPointLogDataPage.getTotalCount());
        return new ModelResult<>(dataPage);
    }

    @Override
    public ModelResult<Integer> count(MemberPointLogOption option) {
        return new ModelResult<>(dao.queryCount("MemberPointLogMapper.queryPageCount", JSONUtils.object2MapSpecail(option)));
    }
}
