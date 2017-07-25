package com.ejuzuo.server.activity.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.ActivityInfo;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.ActivityInfoVO;
import com.ejuzuo.server.activity.manager.ActivityInfoManager;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ActivityInfoManagerImpl implements ActivityInfoManager {

    @Resource
    private GenericDao dao;

    @Resource
    private CodeValueManager codeValueManager;

    @Override
    public ActivityInfo save(ActivityInfo record) {
        dao.insertAndReturnAffectedCount("ActivityInfoMapper.insert", record);
        return record;
    }

    @Override
    public ActivityInfo queryById(Integer id) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
        return dao.queryUnique("ActivityInfoMapper.queryById", map);
    }

    @Override
    public ActivityInfoVO queryVOById(Integer id) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
        ActivityInfo activityInfo = dao.queryUnique("ActivityInfoMapper.queryById", map);

        ActivityInfoVO vo = new ActivityInfoVO();
        try {
            BeanUtils.copyProperties(vo, activityInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        CodeValue codeValue = codeValueManager.queryOne(CollectionCode.remenchengshi, vo.getCity(), null).getModel();
        if(codeValue != null){
            vo.setCityVO(codeValue.getValueName());
        }
        vo.setTime(DateUtil.getDateStringByZdGs(vo.getEnrollBeginTime().getTime(), "MM月dd日") + "-" +
                DateUtil.getDateStringByZdGs(vo.getEnrollEndTime().getTime(), "MM月dd日"));
        return vo ;
    }

    @Override
    public ActivityInfo update(ActivityInfo record) {
        dao.updateByObj("ActivityInfoMapper.update", record);
        return record;
    }

    @Override
    public DataPage<ActivityInfo> queryPage(DataPage<ActivityInfo> dataPage, ActivityInfoOption option) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (option != null) {
            if (option.getCategory() != null) {
                map.put("category", option.getCategory());
            }
            if (option.getCity() != null) {
                map.put("city", option.getCity());
            }
            if (option.getEditStatus() != null) {
                map.put("editStatus", option.getEditStatus());
            }
            if (option.getHoldBeginDate() != null) {
                map.put("holdBeginDate", option.getHoldBeginDate());
            }
            if (option.getHoldEndDate() != null) {
                map.put("holdEndDate", option.getHoldEndDate());
            }
            if (option.getId() != null) {
                map.put("id", option.getId());
            }
            if (option.getPublisher() != null) {
                map.put("publisher", option.getPublisher());
            }
            if (option.getStatus() != null) {
                map.put("status", option.getStatus());
            }
            if (option.getTitle() != null) {
                map.put("title", "%" + option.getTitle() + "%");
            }
        }
        dataPage = dao.queryPage(
                "ActivityInfoMapper.countPage",
                "ActivityInfoMapper.queryPage", map, dataPage);
        return dataPage;
    }

    @Override
    public DataPage<ActivityInfoVO> queryVOPage(DataPage<ActivityInfoVO> dataPage, ActivityInfoOption option) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (option != null) {
            if (option.getCategory() != null) {
                map.put("category", option.getCategory());
            }
            if (option.getCity() != null) {
                map.put("city", option.getCity());
            }
            if (option.getEditStatus() != null) {
                map.put("editStatus", option.getEditStatus());
            }
            if (option.getHoldBeginDate() != null) {
                map.put("holdBeginDate", option.getHoldBeginDate());
            }
            if (option.getHoldEndDate() != null) {
                map.put("holdEndDate", option.getHoldEndDate());
            }
            if (option.getId() != null) {
                map.put("id", option.getId());
            }
            if (option.getPublisher() != null) {
                map.put("publisher", option.getPublisher());
            }
            if (option.getStatus() != null) {
                map.put("status", option.getStatus());
            }
            if (option.getTitle() != null) {
                map.put("title", "%" + option.getTitle() + "%");
            }
        }

        DataPage<ActivityInfo> activityInfoDataPage = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());
        activityInfoDataPage = dao.queryPage(
                "ActivityInfoMapper.countPage",
                "ActivityInfoMapper.queryPage", map, activityInfoDataPage);

        List<ActivityInfoVO> activityInfoVOList = new ArrayList<>();
        ActivityInfoVO vo = null;
        for (ActivityInfo info : activityInfoDataPage.getDataList()) {
            vo = new ActivityInfoVO();
            try {
                BeanUtils.copyProperties(vo, info);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            CodeValue codeValue = codeValueManager.queryOne(CollectionCode.remenchengshi, vo.getCity(), null).getModel();
            if(codeValue != null){
                vo.setCityVO(codeValue.getValueName());
            }

            vo.setTime(DateUtil.getDateStringByZdGs(vo.getEnrollBeginTime().getTime(), "MM月dd日") + "-" +
                    DateUtil.getDateStringByZdGs(vo.getEnrollEndTime().getTime(), "MM月dd日"));

            activityInfoVOList.add(vo);
        }

        dataPage.setDataList(activityInfoVOList);
        return dataPage;

    }

    @Override
    public ModelResult<Integer> count(ActivityInfoOption option) {
        return new ModelResult<>(dao.queryCount("ActivityInfoMapper.countPage", JSONUtils.object2MapSpecail(option)));
    }

	@Override
	public ActivityInfo queryWithContent(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return dao.queryUnique("ActivityInfoMapper.queryWithContent", map);
	}

	@Override
	public Integer updateViewCount(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return dao.update("ActivityInfoMapper.updateViewCount", map);
	}
	
	@Override
	public Boolean updateStatus(Integer id, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		try {
			Integer affected = dao.update("ActivityInfoMapper.updateStatus", map);
			if (affected >= 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
