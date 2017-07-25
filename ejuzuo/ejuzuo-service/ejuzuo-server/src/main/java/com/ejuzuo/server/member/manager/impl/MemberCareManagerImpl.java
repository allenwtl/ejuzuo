package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.member.manager.MemberCareManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allen on 2016/4/3.
 */
@Component
public class MemberCareManagerImpl implements MemberCareManager {

    private static final Logger logger = LoggerFactory.getLogger(MemberCareManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        int size = dao.updateByObj("MemberCareMapper.deleteById", id);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public ModelResult<MemberCare> save(MemberCare record) {
        record.setCreateTime(Calendar.getInstance());
        int size = dao.insertAndReturnAffectedCount("MemberCareMapper.save", record);
        if (size == 1) {
            return new ModelResult<>(record);
        }
        return new ModelResult<>().withError("exception", "保存失败");
    }

    @Override
    public ModelResult<MemberCare> selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("MemberCareMapper.selectById", param);
    }

    @Override
    public ModelResult<Integer> follow(MemberCare memberCare) {

        if (memberCare.getObjectType() == ObjectType.shejishi.getIndex()) {
            if (memberCare.getMemberId() == memberCare.getObjectId()) {
                return new ModelResult<>().withError("exception", "不能自己关注自己");
            }
        }

        List<MemberCare> list = queryList(memberCare).getModel();
        if (list != null && !list.isEmpty()) {
            deleteById(list.get(0).getId());
        } else {
            save(memberCare);
        }
        memberCare.setMemberId(null);
        return new ModelResult<>(this.count(memberCare).getModel());
    }

    @Override
    public ModelResult<List<MemberCare>> queryList(MemberCare memberCare) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(memberCare);

        return new ModelResult<>(dao.queryList("MemberCareMapper.queryList", param));
    }

    @Override
    public ModelResult<Boolean> updateById(MemberCare record) {
        int size = dao.updateByObj("MemberCareMapper.updateById", record);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public PageResult<MemberCare> queryPage(MemberCare memberCare, DataPage<MemberCare> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(memberCare);
        if (dataPage.getOrderBy() != null) {
            param.put("orderBy", dataPage.getOrderBy());
        }

        if (dataPage.getOrder() != null) {
            param.put("order", dataPage.getOrder());
        }

        dataPage = dao.queryPage("MemberCareMapper.countPage", "MemberCareMapper.queryPage", param, dataPage);
        PageResult<MemberCare> result = new PageResult<>();
        result.setPage(dataPage);
        return result;
    }

    @Override
    public ModelResult<Integer> count(MemberCare memberCare) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(memberCare);
        int result = dao.queryCount("MemberCareMapper.countPage", param);
        return new ModelResult<>(result);
    }
}
