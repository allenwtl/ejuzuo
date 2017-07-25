package com.ejuzuo.server.news.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.NewsInfoVO;
import com.ejuzuo.server.news.manager.NewsInfoManager;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Repository
public class NewsInfoManagerImpl implements NewsInfoManager {

    @Autowired
    private GenericDao dao;

    @Override
    public NewsInfo insert(NewsInfo obj) {
        obj.setCreateTime(Calendar.getInstance());
        obj.setUpdateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("NewsInfoMapper.save", obj);
        return obj;
    }

    @Override
    public NewsInfo queryById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return dao.queryUnique("NewsInfoMapper.queryById", map);
    }

    @Override
    public NewsInfo update(NewsInfo obj) {
        obj.setUpdateTime(Calendar.getInstance());
        dao.updateByObj("NewsInfoMapper.update", obj);
        return obj;
    }

    @Override
    public Integer updateViewCount(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return dao.update("NewsInfoMapper.updateViewCount", map);
    }

    @Override
    public DataPage<NewsInfo> queryPage(DataPage<NewsInfo> dataPage, NewsInfoOption qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo != null) {
            if (qVo.getId() != null) {
                map.put("id", qVo.getId());
            }
            if (qVo.getCategory() != null) {
                map.put("category", qVo.getCategory());
            }
            if (qVo.getTitle() != null) {
                map.put("title", "%" + qVo.getTitle() + "%");
            }
            if (qVo.getPublisher() != null) {
                map.put("publisher", qVo.getPublisher());
            }
            if (qVo.getEditStatus() != null) {
                map.put("editStatus", qVo.getEditStatus());
            }
            if (qVo.getStatus() != null) {
                map.put("status", qVo.getStatus());
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
            Calendar publishBeginDate = qVo.getPublishBeginDate();
            Calendar publishEndDate = qVo.getEndDate();
            if (publishBeginDate != null) {
                publishBeginDate.set(Calendar.HOUR_OF_DAY, 0);
                publishBeginDate.set(Calendar.MINUTE, 0);
                publishBeginDate.set(Calendar.SECOND, 0);
                publishBeginDate.set(Calendar.MILLISECOND, 0);
                map.put("publishBeginDate", publishBeginDate);
            }
            if (publishEndDate != null) {
                publishEndDate.set(Calendar.HOUR_OF_DAY, 23);
                publishEndDate.set(Calendar.MINUTE, 59);
                publishEndDate.set(Calendar.SECOND, 59);
                publishEndDate.set(Calendar.MILLISECOND, 999);
                map.put("publishEndDate", publishEndDate);
            }
        }

        dataPage = dao.queryPage(
                "NewsInfoMapper.countPage",
                "NewsInfoMapper.queryPage", map, dataPage);
        return dataPage;
    }

    @Override
    public ModelResult<Integer> count(NewsInfoOption qVo) {
        return new ModelResult<>(dao.queryCount("NewsInfoMapper.countPage",
                JSONUtils.object2MapSpecail(qVo)));
    }

    @Override
    public PageResult<NewsInfoVO> queryVOPage(DataPage<NewsInfoVO> dataPage, NewsInfoOption qVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (qVo != null) {
            if (qVo.getId() != null) {
                map.put("id", qVo.getId());
            }
            if (qVo.getCategory() != null) {
                map.put("category", qVo.getCategory());
            }
            if (qVo.getTitle() != null) {
                map.put("title", "%" + qVo.getTitle() + "%");
            }
            if (qVo.getPublisher() != null) {
                map.put("publisher", qVo.getPublisher());
            }
            if (qVo.getEditStatus() != null) {
                map.put("editStatus", qVo.getEditStatus());
            }
            if (qVo.getStatus() != null) {
                map.put("status", qVo.getStatus());
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
            Calendar publishBeginDate = qVo.getPublishBeginDate();
            Calendar publishEndDate = qVo.getEndDate();
            if (publishBeginDate != null) {
                publishBeginDate.set(Calendar.HOUR_OF_DAY, 0);
                publishBeginDate.set(Calendar.MINUTE, 0);
                publishBeginDate.set(Calendar.SECOND, 0);
                publishBeginDate.set(Calendar.MILLISECOND, 0);
                map.put("publishBeginDate", publishBeginDate);
            }
            if (publishEndDate != null) {
                publishEndDate.set(Calendar.HOUR_OF_DAY, 23);
                publishEndDate.set(Calendar.MINUTE, 59);
                publishEndDate.set(Calendar.SECOND, 59);
                publishEndDate.set(Calendar.MILLISECOND, 999);
                map.put("publishEndDate", publishEndDate);
            }
        }

        DataPage<NewsInfo> dataPageResult = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());
        dataPageResult.setNeedTotalCount(dataPage.isNeedTotalCount());
        dataPageResult = dao.queryPage(
                "NewsInfoMapper.countPage",
                "NewsInfoMapper.queryPage", map, dataPageResult);

        List<NewsInfo> dataList = dataPageResult.getDataList();


        List<NewsInfoVO> voList = new ArrayList<>();
        NewsInfoVO vo = null;
        for (NewsInfo item : dataList) {
            vo = new NewsInfoVO();

            try {
                BeanUtils.copyProperties(vo, item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            vo.setTimeVO(DateUtil.dateToString(item.getUpdateTime().getTime(), "yyyy-MM-dd HH:mm"));
            voList.add(vo);
        }
        dataPage.setDataList(voList);
        PageResult<NewsInfoVO> pageResult = new PageResult<>();
        pageResult.setPage(dataPage);

        return pageResult;
    }

    @Override
    public NewsInfo queryWithContent(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return dao.queryUnique("NewsInfoMapper.queryWithContent", map);
    }

    @Override
    public Boolean updateStatus(Integer id, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("status", status);
        try {
            Integer affected = dao.update("NewsInfoMapper.updateStatus", map);
            if (affected >= 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public ModelResult<List<NewsInfo>> queryList(NewsInfoOption qVo, Integer size) {
        Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
        map.put("size", size);
        return new ModelResult<>(dao.queryList("NewsInfoMapper.selectList", map));
    }

}
