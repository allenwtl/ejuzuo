package com.ejuzuo.server.designer.manager.impl;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.DesignerOption;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.DesignerVO;
import com.ejuzuo.server.area.manager.AreaManager;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.designer.manager.DesignerWorkManager;
import com.ejuzuo.server.member.manager.MemberCareManager;
import com.ejuzuo.server.member.manager.MemberFavoriteManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class DesignerManagerImpl implements DesignerManager {

    private static final Logger logger = LoggerFactory.getLogger(DesignerManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Resource
    private MemberFavoriteManager memberFavoriteManager ;

    @Resource
    private DesignerWorkManager designerWorkManager ;

    @Resource
    private AreaManager areaManager ;

    @Resource
    private MemberCareManager memberCareManager ;

    @Resource
    private CodeValueManager codeValueManager;

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("DesignerMapper.deleteById", id);
    }

    @Override
    public Designer save(Designer record) {
        record.setCreateTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("DesignerMapper.save", record);
        return record;
    }

    @Override
    public Designer selectById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("DesignerMapper.selectById", param);
    }

    @Override
    public ModelResult<Boolean> updateById(Designer record) {
        try {
            record.setUpdateTime(Calendar.getInstance());
            int size = dao.updateByObj("DesignerMapper.updateById", record);
            if (size == 1) {
                return new ModelResult<>(Boolean.TRUE);
            }
            return new ModelResult<>().withError("exception", "更新不成功");
        } catch (Exception e) {
            logger.error("DesignerMapper.updateById id:[{}]报错", record.getId(), e);
            return new ModelResult<>().withError("exception", "更新报错");
        }
    }

    @Override
    public ModelResult<Designer> queryByMemberId(Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);

        List<Designer> designerList = dao.queryList("DesignerMapper.selectByMemberId", param);
        if (designerList == null || designerList.isEmpty()) {
            logger.info("根据memberId:[{}], 查询不到数据");
            return new ModelResult<>().withError("exception","查询不到数据");
        }
        return new ModelResult<>(designerList.get(0));
    }

    
    @Override
    public ModelResult<DesignerVO> queryVOByDesignerId(Integer designerId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", designerId);
    	Designer designer = dao.queryUnique("DesignerMapper.selectById", param);
        if (designer == null)  {
            logger.info("根据designerId:[{}], 查询不到数据");
            return new ModelResult<>().withError("exception","查询不到数据");
        }
        DesignerVO designerVO = construnctDesignerVO(designer);  
        return new ModelResult<>(designerVO);
    }
    
    @Override
    public ModelResult<DesignerVO> queryVOByMemberId(Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);

        List<Designer> designerList = dao.queryList("DesignerMapper.selectByMemberId", param);
        if (designerList == null || designerList.isEmpty()) {
            logger.info("根据memberId:[{}], 查询不到数据");
            return new ModelResult<>().withError("exception","查询不到数据");
        }
        Designer designer = designerList.get(0);
        DesignerVO designerVO = construnctDesignerVO(designer);  
        return new ModelResult<>(designerVO);
    }

	private DesignerVO construnctDesignerVO(Designer designer){	
		DesignerVO designerVO = new DesignerVO();		
		try {
		    BeanUtils.copyProperties(designerVO, designer);
		    MemberFavoriteOption option = new MemberFavoriteOption();
		    option.setObjectType(ObjectType.shejishi.getIndex());
		    option.setObjectId(designer.getId());
		    designerVO.setFollower(memberFavoriteManager.count(option).getModel());
		    designerVO.setWorkCount(designerWorkManager.countWork(designer.getMemberId(), EditStatus.PUBLISHED, Status.STATUS).getModel());
		
		    MemberCare memberCareOption = new MemberCare();
		    memberCareOption.setObjectId(designerVO.getMemberId());
		    memberCareOption.setObjectType(ObjectType.shejishi.getIndex());
		    designerVO.setFollower(memberCareManager.count(memberCareOption).getModel());
		
		    StringBuilder sb = new StringBuilder();
		    sb.append(areaManager.queryProvince(designerVO.getProvice()).getModel().getName());
		    if(designerVO.getCity() != null){
		        sb.append(" ").append(areaManager.queryCity(designerVO.getCity(), designerVO.getProvice()).getModel().getName());
		    }
		    designerVO.setArea(sb.toString());
	      } catch (IllegalAccessException e) {
	            e.printStackTrace();
	      } catch (InvocationTargetException e) {
	            e.printStackTrace();
	      }    
		return designerVO;
	}

    @Override
    public ModelResult<List<Designer>> queryList(DesignerOption option) {

        return new ModelResult<>(dao.queryList("DesignerMapper.selectList", JSONUtils.object2MapSpecail(option)));
    }

    @Override
    public ModelResult<DataPage<Designer>> adminQueryPage(DesignerOption option, DataPage<Designer> dataPage) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        if (option != null) {
            if (option.getId() != null) {
                map.put("id", option.getId());
            }
            if (option.getMemberId() != null) {
                map.put("memberId", option.getMemberId());
            }
            if (option.getName() != null) {
                map.put("name", "%" + option.getName() + "%");
            }
            if (option.getDesignerTypeList() != null) {
                map.put("designerTypeList", option.getDesignerTypeList());
            }
            if (option.getStatus() != null) {
                map.put("status", option.getStatus());
            }
            Calendar beginTime = option.getStartTime();
            Calendar endTime = option.getEndTime();
            if (beginTime != null) {
                beginTime.set(Calendar.HOUR_OF_DAY, 0);
                beginTime.set(Calendar.MINUTE, 0);
                beginTime.set(Calendar.SECOND, 0);
                beginTime.set(Calendar.MILLISECOND, 0);
                map.put("beginTime", beginTime);
            }
            if (endTime != null) {
                endTime.set(Calendar.HOUR_OF_DAY, 23);
                endTime.set(Calendar.MINUTE, 59);
                endTime.set(Calendar.SECOND, 59);
                endTime.set(Calendar.MILLISECOND, 999);
                map.put("endTime", endTime);
            }
        }
        return new ModelResult<>(dao.queryPage("DesignerMapper.countByPage", "DesignerMapper.selectByPage",
                map, dataPage));
    }

    @Override
    public PageResult<Designer> queryPage(DesignerOption option, DataPage<Designer> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        dataPage = dao.queryPage("DesignerMapper.countByPage", "DesignerMapper.selectByPage",  param, dataPage);
        PageResult<Designer> result = new PageResult<>();
        result.setPage(dataPage);
        return result;
    }

    @Override
    public ModelResult<DataPage<Designer>> queryHomePage(DesignerOption option, DataPage<Designer> dataPage) {
        dataPage = dao.queryPage("DesignerMapper.countByPage", "DesignerMapper.queryHomePage",  JSONUtils.object2MapSpecail(option), dataPage);
        return new ModelResult<>(dataPage);
    }

    @Override
    public ModelResult<Integer> count(String area, String style, Integer type) {
        Map<String,Object> param = new HashMap<>();
        param.put("area", area);
        param.put("style", style);
        param.put("type", type);
        return new ModelResult<>(dao.queryCount("DesignerMapper.countDesigner", param));
    }

    @Override
    public ModelResult<DataPage<DesignerVO>> queryDesigner(String area, String style, Integer type, DataPage<DesignerVO> dataPageVO) {
        Map<String,Object> param = new HashMap<>();
        param.put("area", area);
        param.put("style", style);
        param.put("type", type);

        DataPage<Designer> datapage = new DataPage<>(dataPageVO.getPageNo(), dataPageVO.getPageSize());
        datapage = dao.queryPage("DesignerMapper.countDesigner", "DesignerMapper.pageDesigner", param, datapage);

        DesignerVO vo = null ;
        List<DesignerVO> designerVOList = new ArrayList<>();
        for(Designer designer : datapage.getDataList()){
            vo = new DesignerVO();
            try {
                BeanUtils.copyProperties(vo, designer);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            StringBuilder sb  = new StringBuilder();
            if(StringUtils.isNotBlank(designer.getAdeptStyles())){
                for (String str : designer.getAdeptStyles().split(",")) {
                    sb.append(codeValueManager.queryOne(CollectionCode.fengge, str, null).getModel().getValueName()).append("，");
                }
            }

            vo.setStyleVo(sb.toString());
            sb = new StringBuilder();
            sb.append(areaManager.queryProvince(designer.getProvice()).getModel().getName());
            if (designer.getCity() != null) {
                sb.append(" ").append(areaManager.queryCity(designer.getCity(), designer.getProvice()).getModel().getName());
            }
            vo.setArea(sb.toString());
            vo.setWorkCount(designerWorkManager.countWork(designer.getMemberId(), EditStatus.PUBLISHED, Status.STATUS).getModel());
            designerVOList.add(vo);
        }

        dataPageVO.setDataList(designerVOList);
        return new ModelResult<>(dataPageVO);
    }

	@Override
	public BaseResult updateHomed(Integer id, Integer homed) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("homed", homed);
		map.put("updateTime", Calendar.getInstance());
		try {
			dao.update("DesignerMapper.updateHomed", map);
			return new BaseResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BaseResult().withError("designer updateHomed error", "designer updateHomed error");
	}


}
