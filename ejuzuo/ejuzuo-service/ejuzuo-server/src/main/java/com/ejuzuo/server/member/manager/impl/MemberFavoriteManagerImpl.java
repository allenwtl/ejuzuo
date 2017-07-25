package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.domain.type.DesignerType;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.FavoriteVO;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.designer.manager.DesignerWorkManager;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import com.ejuzuo.server.member.manager.MemberFavoriteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
@Component
public class MemberFavoriteManagerImpl implements MemberFavoriteManager {

    private static final Logger logger = LoggerFactory.getLogger(MemberFavoriteManagerImpl.class);

    @Resource(name = "dao")
    protected GenericDao dao;

    @Resource
    private DesignerWorkManager designerWorkManager;

    @Resource
    private DigitalFurnitureManager digitalFurnitureManager;

    @Resource
    private DesignerManager designerManager;

    @Override
    public ModelResult<MemberFavorite> save(MemberFavorite memberFavorite) {
        memberFavorite.setCreateTime(Calendar.getInstance());
        try {
            int result = dao.insertAndReturnAffectedCount("MemberFavoriteMapper.save", memberFavorite);
            if (result == 1) {
                return new ModelResult<>(memberFavorite);
            }
            return new ModelResult<>().withError("exception", "没有保存");
        } catch (Exception e) {
            logger.error("保存报错:{}", e);
            return new ModelResult<>().withError("exception", e.getMessage());
        }
    }

    @Override
    public ModelResult<DataPage<MemberFavorite>> queryByPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        return new ModelResult<>(dao.queryPage("MemberFavoriteMapper.queryPageCount", "MemberFavoriteMapper.queryPage", param, dataPage));
    }

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            int result = dao.update("MemberFavoriteMapper.deleteById", param);
            if (result == 1) {
                return new ModelResult<>(Boolean.TRUE);
            }
            return new ModelResult<>().withError("exception", "没有保存");
        } catch (Exception e) {
            logger.error("保存报错:{}", e);
            return new ModelResult<>().withError("exception", e.getMessage());
        }
    }

    @Override
    public ModelResult<Boolean> deleteByIds(List<Integer> ids, Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        param.put("memberId", memberId);
        try {
            dao.update("MemberFavoriteMapper.deleteByIds", param);
        } catch (Exception e) {
            return new ModelResult<>().withError("exception", "删除失败");
        }
        return new ModelResult<>();
    }

    @Override
    public ModelResult<Boolean> deleteByObjectIdAndType(Integer objectId, ObjectType objectType) {
        Map<String, Object> param = new HashMap<>();
        param.put("objectId", objectId);
        param.put("objectType", objectType.getIndex());
        try {
            dao.update("MemberFavoriteMapper.deleteByObjectIdAndType", param);
        } catch (Exception e) {
            logger.info("删除收藏:objectType:{}, objectId:{} 失败:{}", objectType.getIndex(), objectId, e.getMessage());
            return new ModelResult<>(Boolean.FALSE);
        }

        return new ModelResult<>(Boolean.TRUE);
    }

    @Override
    public ModelResult<Integer> count(MemberFavoriteOption option) {
        return new ModelResult<>(dao.queryCount("MemberFavoriteMapper.queryPageCount", JSONUtils.object2MapSpecail(option)));
    }

    @Override
    public ModelResult<List<MemberFavorite>> queryList(MemberFavoriteOption option) {

        return new ModelResult<>(dao.queryList("MemberFavoriteMapper.queryList", JSONUtils.object2MapSpecail(option)));
    }

    @Override
    public ModelResult<Integer> collect(MemberFavorite memberFavorite) {

    	
        MemberFavoriteOption option = new MemberFavoriteOption();
        option.setMemberId(memberFavorite.getMemberId());
        option.setObjectId(memberFavorite.getObjectId());
        option.setObjectType(memberFavorite.getObjectType());
        /***
        List<MemberFavorite> memberFavoriteList = queryList(option).getModel();
        if (memberFavoriteList != null && !memberFavoriteList.isEmpty()) {
            deleteById(memberFavoriteList.get(0).getId());
            option.setMemberId(null);
            return new ModelResult<>(this.count(option).getModel());
        }***/

        ModelResult<MemberFavorite> modelResult = save(memberFavorite);
        if (modelResult.isSuccess()) {
            option.setMemberId(null);
            return new ModelResult<>(this.count(option).getModel());
        }

        return new ModelResult<>().withError("exception", modelResult.getErrorMsg());
    }

    @Override
    public ModelResult<List<FavoriteVO>> queryVOList(MemberFavoriteOption option) {
        List<MemberFavorite> memberFavoriteList = queryList(option).getModel();
        FavoriteVO favoriteVO = null;
        DesignerWork designerWork = null;
        DigitalFurniture digitalFurniture = null;
        List<FavoriteVO> favoriteVOList = new ArrayList<>();
        for (MemberFavorite item : memberFavoriteList) {
            favoriteVO = new FavoriteVO();
            favoriteVO.setType(item.getObjectType());
            if (item.getObjectType() == ObjectType.shejizuopin.getIndex()) {
                designerWork = designerWorkManager.selectById(item.getObjectId(), null);
                favoriteVO.setId(designerWork.getId());
                favoriteVO.setCoverImg(designerWork.getCoverImg());
            } else if (item.getObjectType() == ObjectType.shuzijiaju.getIndex()) {
                digitalFurniture = digitalFurnitureManager.queryById(item.getObjectId());
                favoriteVO.setCoverImg(digitalFurniture.getThumbnail());
                favoriteVO.setId(digitalFurniture.getId());
            }
            favoriteVOList.add(favoriteVO);
        }
        return new ModelResult<>(favoriteVOList);
    }

    @Override
    public PageResult<FavoriteVO> queryVOPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        dataPage = dao.queryPage("MemberFavoriteMapper.queryPageCount", "MemberFavoriteMapper.queryPage", param, dataPage);

        FavoriteVO favoriteVO = null;
        DesignerWork designerWork = null;
        Designer designer = null ;
        DigitalFurniture digitalFurniture = null;
        List<FavoriteVO> favoriteVOList = new ArrayList<>();
        for (MemberFavorite item : dataPage.getDataList()) {
            favoriteVO = new FavoriteVO();
            favoriteVO.setType(item.getObjectType());
            if (item.getObjectType() == ObjectType.shejizuopin.getIndex()) {
                designerWork = designerWorkManager.selectById(item.getObjectId(), null);
                favoriteVO.setId(designerWork.getId());
                favoriteVO.setCoverImg(designerWork.getCoverImg());
                designer = designerManager.queryByMemberId(designerWork.getMemberId()).getModel();
                favoriteVO.setDesignerName(designer.getName());
                favoriteVO.setDesignerType(DesignerType.findByIndex(designer.getDesignerType()).getDescription());
                favoriteVO.setUrl("/designerWork/detail/"+item.getObjectId());
            } else if (item.getObjectType() == ObjectType.shuzijiaju.getIndex()) {
                digitalFurniture = digitalFurnitureManager.queryById(item.getObjectId());
                favoriteVO.setCoverImg(digitalFurniture.getThumbnail());
                favoriteVO.setId(digitalFurniture.getId());
                favoriteVO.setUrl("/digital/digitalDetail/"+item.getObjectId());
            }
            favoriteVOList.add(favoriteVO);
        }
        DataPage<FavoriteVO> favoriteVODataPage = new DataPage<>();
        favoriteVODataPage.setDataList(favoriteVOList);

        PageResult<FavoriteVO> result = new PageResult<>();
        result.setPage(favoriteVODataPage);
        return result;
    }
}
