package com.ejuzuo.server.designer.manager.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.domain.type.DesignerType;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.DesignerWorkVO;
import com.ejuzuo.server.area.manager.AreaManager;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.designer.manager.DesignerWorkManager;
import com.ejuzuo.server.member.manager.CommentManager;
import com.ejuzuo.server.member.manager.MemberCareManager;
import com.ejuzuo.server.member.manager.MemberFavoriteManager;

/**
 * Created by allen on 2016/4/4.
 */

@Component
public class DesignerWorkManagerImpl implements DesignerWorkManager {

    @Resource(name = "dao")
    private GenericDao dao;

    @Resource
    private DesignerManager designerManager;

    @Resource
    private AreaManager areaManager;

    @Resource
    private CodeValueManager codeValueManager;

    @Resource
    private MemberFavoriteManager memberFavoriteManager;

    @Resource
    private MemberCareManager memberCareManager;


    @Resource
    private CommentManager commentManager;

    @Override
    public ModelResult<Boolean> deleteById(Integer id, Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        if (memberId != null) {
            param.put("memberId", memberId);
        }

        int size = dao.updateByObj("DesignerWorkMapper.deleteById", param);
        memberFavoriteManager.deleteByObjectIdAndType(id, ObjectType.shejizuopin);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public DesignerWork save(DesignerWork record) {
        record.setUploadTime(Calendar.getInstance());
        record.setCreateTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        record.setStatus(Status.STATUS.getIndex());
        record.setEditStatus(EditStatus.SAVE.getIndex());
        record.setViewCount(0);
        dao.insertAndReturnAffectedCount("DesignerWorkMapper.save", record);
        return record;
    }

    @Override
    public DesignerWork selectById(Integer id, DesignerWorkOption option) {
        Map<String, Object> param = null;
        if (option == null) {
            param = new HashMap<>();
        } else {
            param = JSONUtils.object2MapSpecail(option);
        }
        param.put("id", id);
        return dao.queryUnique("DesignerWorkMapper.selectById", param);
    }

    @Override
    public DesignerWorkVO queryVOById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        DesignerWork designerWork = dao.queryUnique("DesignerWorkMapper.selectById", param);
        DesignerWorkVO vo = new DesignerWorkVO();

        try {
            BeanUtils.copyProperties(vo, designerWork);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Designer designer = designerManager.queryByMemberId(designerWork.getMemberId()).getModel();
        vo.setDesignerName(designer.getName());
        if (StringUtils.isNotBlank(designer.getCoverImg())) {
            vo.setDesignerImg(JSONObject.parseObject(designer.getCoverImg()).getString("pic4343"));
        }

        vo.setPersonViewCount(designer.getViewCount());

        StringBuilder sb = new StringBuilder();
        sb.append(areaManager.queryProvince(designer.getProvice()).getModel().getName());
        if (designer.getCity() != null) {
            sb.append(" ").append(areaManager.queryCity(designer.getCity(), designer.getProvice()).getModel().getName());
        }
        vo.setAddress(sb.toString());

        sb = new StringBuilder();
        for (String str : designerWork.getStyle().split(",")) {
            sb.append(codeValueManager.queryOne(CollectionCode.fengge, str, null).getModel().getValueName()).append("，");
        }
        vo.setStyleVo(sb.toString().substring(0, sb.toString().length() - 1));
        vo.setWorkCount(this.countWork(vo.getMemberId(), EditStatus.PUBLISHED, Status.STATUS).getModel());
        vo.setDesignerType(DesignerType.findByIndex(designer.getDesignerType()).getDescription());
        MemberCare memberCare = new MemberCare();
        memberCare.setObjectId(designerWork.getMemberId());
        memberCare.setObjectType(ObjectType.shejishi.getIndex());
        vo.setFollowerCount(memberCareManager.count(memberCare).getModel());

        MemberFavoriteOption option = new MemberFavoriteOption();
        option.setObjectId(designerWork.getId());
        option.setObjectType(ObjectType.shejizuopin.getIndex());
        vo.setLike(memberFavoriteManager.count(option).getModel());

        return vo;
    }

    @Override
    public int updateById(DesignerWork record) {
        record.setUpdateTime(Calendar.getInstance());
        return dao.updateByObj("DesignerWorkMapper.updateById", record);
    }

    @Override
    public ModelResult<List<DesignerWork>> queryList(DesignerWorkOption option) {
        return new ModelResult<>(dao.queryList("DesignerWorkMapper.selectList", JSONUtils.object2MapSpecail(option)));
    }

    @Override
    public DataPage<DesignerWork> queryPage(DataPage<DesignerWork> dataPage, DesignerWorkOption option) {
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
            if (option.getEditStatus() != null) {
                map.put("editStatus", option.getEditStatus());
            }
            if (option.getStatus() != null) {
                map.put("status", option.getStatus());
            }
            Calendar beginTime = option.getBeginUploadTime();
            Calendar endTime = option.getEndUploadTime();
            if (beginTime != null) {
                beginTime.set(Calendar.HOUR_OF_DAY, 0);
                beginTime.set(Calendar.MINUTE, 0);
                beginTime.set(Calendar.SECOND, 0);
                beginTime.set(Calendar.MILLISECOND, 0);
                map.put("beginUploadTime", beginTime);
            }
            if (endTime != null) {
                endTime.set(Calendar.HOUR_OF_DAY, 23);
                endTime.set(Calendar.MINUTE, 59);
                endTime.set(Calendar.SECOND, 59);
                endTime.set(Calendar.MILLISECOND, 999);
                map.put("endUploadTime", endTime);
            }
        }
        return dao.queryPage("DesignerWorkMapper.countByPage", "DesignerWorkMapper.selectByPage",
                map, dataPage);
    }

    @Override
    public ModelResult<Integer> countWork(Integer memberId, EditStatus editStatus, Status status) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        if (editStatus != null) {
            param.put("editStatus", editStatus.getIndex());
        }
        if (status != null) {
            param.put("status", status.getIndex());
        }

        return new ModelResult<>(dao.queryCount("DesignerWorkMapper.countWork", param));
    }

    @Override
    public PageResult<DesignerWorkVO> queryUnion(String area, String style, Integer type, DataPage<DesignerWork> dataPage) {
        Map<String, Object> param = new HashMap<>();
        if (area != null) {
            param.put("area", area);
        }
        if (style != null) {
            param.put("style", style);
        }

        if (type != null) {
            param.put("type", type);
        }

        dataPage = dao.queryPage("DesignerWorkMapper.countUnion", "DesignerWorkMapper.queryUnion", param, dataPage);

        DesignerWorkVO vo = null;
        StringBuilder sb = null;
        Designer designer = null;
        List<DesignerWorkVO> workVOList = new ArrayList<>();
        for (DesignerWork work : dataPage.getDataList()) {
            vo = new DesignerWorkVO();
            try {
                BeanUtils.copyProperties(vo, work);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            designer = designerManager.queryByMemberId(work.getMemberId()).getModel();
            vo.setDesignerName(designer.getName());
            if (StringUtils.isNotBlank(designer.getCoverImg())) {
                vo.setDesignerImg(JSONObject.parseObject(designer.getCoverImg()).getString("pic4343"));
            }

            sb = new StringBuilder();
            sb.append(areaManager.queryProvince(designer.getProvice()).getModel().getName());
            if (designer.getCity() != null) {
                sb.append(" ").append(areaManager.queryCity(designer.getCity(), designer.getProvice()).getModel().getName());
            }
            vo.setAddress(sb.toString());

            sb = new StringBuilder();
            for (String str : work.getStyle().split(",")) {
                sb.append(codeValueManager.queryOne(CollectionCode.fengge, str, null).getModel().getValueName()).append("，");
            }
            vo.setStyleVo(sb.toString().substring(0, sb.toString().length() - 1));
            vo.setWorkCount(this.countWork(vo.getMemberId(), EditStatus.PUBLISHED, Status.STATUS).getModel());

            MemberCare memberCare = new MemberCare();
            memberCare.setObjectId(work.getMemberId());
            memberCare.setObjectType(ObjectType.shejishi.getIndex());
            vo.setFollowerCount(memberCareManager.count(memberCare).getModel());

            workVOList.add(vo);
        }

        DataPage<DesignerWorkVO> result = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());
        result.setDataList(workVOList);
        result.setTotalCount(dataPage.getTotalCount());

        PageResult<DesignerWorkVO> pageResult = new PageResult<>();
        pageResult.setPage(result);
        return pageResult;
    }

    @Override
    public ModelResult<Integer> countUnion(String area, String style, Integer type) {
        Map<String, Object> param = new HashMap<>();
        if (area != null) {
            param.put("area", area);
        }
        if (style != null) {
            param.put("style", style);
        }
        if (type != null) {
            param.put("type", type);
        }
        return new ModelResult<>(dao.queryCount("DesignerWorkMapper.countUnion", param));
    }

    @Override
    public ModelResult<DataPage<DesignerWorkVO>> queryByDesigner(DesignerWorkOption designerWorkOption, DataPage<DesignerWork> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(designerWorkOption);
        dataPage = dao.queryPage("DesignerWorkMapper.countByDesigner", "DesignerWorkMapper.queryByDesigner", param, dataPage);
        DesignerWorkVO vo = null;
        MemberFavoriteOption option = new MemberFavoriteOption();
        option.setObjectType(ObjectType.shejizuopin.getIndex());
        CommentOption commentOption = new CommentOption();
        commentOption.setObjectType(ObjectType.shejizuopin.getIndex());

        Designer designer = null;
        StringBuilder sb = null;
        List<DesignerWorkVO> designerWorkVOList = new ArrayList<>();
        for (DesignerWork work : dataPage.getDataList()) {
            vo = new DesignerWorkVO();

            try {
                BeanUtils.copyProperties(vo, work);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            designer = designerManager.queryByMemberId(work.getMemberId()).getModel();
            vo.setDesignerName(designer.getName());
            vo.setDesignerImg(designer.getCoverImg());

            sb = new StringBuilder();
            sb.append(areaManager.queryProvince(designer.getProvice()).getModel().getName());
            if (designer.getCity() != null) {
                sb.append(" ").append(areaManager.queryCity(designer.getCity(), designer.getProvice()).getModel().getName());
            }
            vo.setAddress(sb.toString());

            sb = new StringBuilder();
            for (String str : work.getStyle().split(",")) {
                sb.append(codeValueManager.queryOne(CollectionCode.fengge, str, null).getModel().getValueName()).append("，");
            }
            vo.setStyleVo(sb.toString().substring(0, sb.toString().length() - 1));

            option.setObjectId(work.getId());
            vo.setLike(memberFavoriteManager.count(option).getModel());

            commentOption.setObjectId(work.getId());
            vo.setCommentCount(commentManager.count(commentOption).getModel());
            vo.setWorkCount(this.countWork(vo.getMemberId(), EditStatus.PUBLISHED, Status.STATUS).getModel());

            MemberCare memberCare = new MemberCare();
            memberCare.setObjectId(work.getMemberId());
            memberCare.setObjectType(ObjectType.shejishi.getIndex());
            vo.setFollowerCount(memberCareManager.count(memberCare).getModel());

            designerWorkVOList.add(vo);
        }

        DataPage<DesignerWorkVO> workVODataPage = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());
        workVODataPage.setTotalCount(dataPage.getTotalCount());
        workVODataPage.setDataList(designerWorkVOList);

        return new ModelResult<>().withModel(workVODataPage);
    }

    @Override
    public ModelResult<Integer> countByDesigner(Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", memberId);
        return new ModelResult<>(dao.queryCount("DesignerWorkMapper.countByDesigner", param));
    }

    @Override
    public Boolean updateStatus(Integer id, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("status", status);
        try {
            Integer updated = dao.update("DesignerWorkMapper.updateStatus", map);
            if (updated >= 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
