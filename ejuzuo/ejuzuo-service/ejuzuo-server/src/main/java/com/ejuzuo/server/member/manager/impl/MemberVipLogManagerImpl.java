package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.MemberVipLogTransType;
import com.ejuzuo.common.domain.MemberVipLog;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.MemberVipLogVO;
import com.ejuzuo.server.member.manager.MemberVipLogManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class MemberVipLogManagerImpl implements MemberVipLogManager {

    @Resource(name = "dao")
    private GenericDao dao;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("MemberVipLogMapper.deleteById", id);
    }

    @Override
    public MemberVipLog save(MemberVipLog record) {
        record.setCreateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("MemberVipLogMapper.save", record);
        return record;
    }

    @Override
    public MemberVipLog selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("MemberVipLogMapper.selectById", param);
    }

    @Override
    public int updateById(MemberVipLog record) {
        return dao.updateByObj("MemberVipLogMapper.updateById", record);
    }

    @Override
    public DataPage<MemberVipLog> queryPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo != null) {
            if (qVo.getMemberId() != null) {
                map.put("memberId", qVo.getMemberId());
            }
            if (qVo.getTransType() != null) {
                map.put("transType", qVo.getTransType());
            }
        }
        return dao.queryPage("MemberVipLogMapper.countPage", "MemberVipLogMapper.queryPage", map, dataPage);
    }

    @Override
    public DataPage<MemberVipLogVO> queryVOPage(DataPage<MemberVipLog> dataPage, MemberVipLog qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo != null) {
            if (qVo.getMemberId() != null) {
                map.put("memberId", qVo.getMemberId());
            }
            if (qVo.getTransType() != null) {
                map.put("transType", qVo.getTransType());
            }
        }

        dataPage = dao.queryPage("MemberVipLogMapper.countPage", "MemberVipLogMapper.queryPage", map, dataPage);
        List<MemberVipLog> memberVipLogList = dataPage.getDataList();
        List<MemberVipLogVO> memberVipLogVOList = new ArrayList<MemberVipLogVO>();
        DataPage<MemberVipLogVO> memberVipLogVODataPage = new DataPage<>();
        if (memberVipLogList == null || memberVipLogList.size() == 0) {
            memberVipLogVODataPage.setDataList(memberVipLogVOList);
            return memberVipLogVODataPage;
        }

        MemberVipLogVO vo = null ;
        for (MemberVipLog vipLog : memberVipLogList) {
            vo = new MemberVipLogVO();
            vo.setAddExpireTime(vipLog.getPeriod()+"个月");
            vo.setOperAccount(vipLog.getCreator());
            vo.setRemark(vipLog.getRemark());
            vo.setUpdateTime(sdf.format(vipLog.getCreateTime().getTime()));
            vo.setReason(MemberVipLogTransType.findByIndex(vipLog.getTransType()).getDescription());
            memberVipLogVOList.add(vo);
        }
        memberVipLogVODataPage.setDataList(memberVipLogVOList);
        return memberVipLogVODataPage;
    }

    @Override
    public ModelResult<Integer> count(MemberVipLog qVo) {
        int result = dao.queryCount("MemberVipLogMapper.countPage", JSONUtils.object2MapSpecail(qVo));
        return new ModelResult<>(result);
    }
}
