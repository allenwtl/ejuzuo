package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.ShoppingCart;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.vo.ShoppingCartVO;
import com.ejuzuo.server.brand.manager.BrandManager;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import com.ejuzuo.server.downloadinfo.manager.DownloadInfoManager;
import com.ejuzuo.server.member.manager.MemberPointManager;
import com.ejuzuo.server.member.manager.ShoppingCartManager;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
@Component
public class ShopingCartManagerImpl implements ShoppingCartManager {

    private static final Logger logger = LoggerFactory.getLogger(ShopingCartManagerImpl.class);

    @Resource(name = "dao")
    protected GenericDao dao;

    @Resource
    private DigitalFurnitureManager digitalFurnitureManager;

    @Resource
    private DownloadInfoManager downloadInfoManager ;

    @Resource
    private MemberPointManager memberPointManager;

    @Resource
    private BrandManager brandManager ;

    @Override
    public ModelResult<ShoppingCart> save(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartDB = queryByGoodsId(shoppingCart.getGoodsId(), shoppingCart.getMemberId()).getModel();
        if (shoppingCartDB != null) {
            logger.info("goodsId:{}:memberId:{}:已经存在了", shoppingCart.getGoodsId(), shoppingCart.getMemberId());
            return new ModelResult<>().withError("exception", "已经存在了");
        }
        DownloadInfoOption downloadInfoOption = new DownloadInfoOption();
        downloadInfoOption.setGoodsId(shoppingCart.getGoodsId());
        downloadInfoOption.setMemberId((long)shoppingCart.getMemberId());
        downloadInfoOption.setStatus(Status.STATUS.getIndex());

        List<DownloadInfo> downloadInfoList = downloadInfoManager.queryListByOption(downloadInfoOption);
        if(downloadInfoList !=null && !downloadInfoList.isEmpty()){
            logger.info("添加购物车--已经存在了有效的商品id:[{}]", shoppingCart.getGoodsId());
            return new ModelResult<>().withError("exception", "已经存在有效的商品");
        }

        shoppingCart.setCreateTime(Calendar.getInstance());
        int result = dao.insertAndReturnAffectedCount("ShoppingCartMapper.save", shoppingCart);
        if (result == 1) {
            return new ModelResult<>(shoppingCart);
        }
        return new ModelResult<>().withError("exception", "保存成功");
    }

    @Override
    public ModelResult<DataPage<ShoppingCartVO>> queryPage(Integer memberId, DataPage<ShoppingCartVO> dataPage) {
        Map<String, Object> param = new HashMap<>();
        if (memberId != null) {
            param.put("memberId", memberId);
        }
        DataPage<ShoppingCart> dataPageFromDB = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());
        dataPageFromDB = dao.queryPage("ShoppingCartMapper.countByPage", "ShoppingCartMapper.selectByPage", param, dataPageFromDB);
        List<ShoppingCartVO> shoppingCartVOList = new ArrayList<>();
        for (ShoppingCart item : dataPageFromDB.getDataList()) {
            ShoppingCartVO vo = new ShoppingCartVO();
            try {
                BeanUtils.copyProperties(vo, item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            DigitalFurniture digitalFurniture = digitalFurnitureManager.queryById(item.getGoodsId());
            vo.setDigitalFurniture(digitalFurniture);
            vo.setBrandName(brandManager.selectById(digitalFurniture.getBrand()).getModel().getName());
            shoppingCartVOList.add(vo);
        }
        dataPage.setDataList(shoppingCartVOList);
        dataPage.setTotalCount(dataPageFromDB.getTotalCount());
        return new ModelResult<>(dataPage);
    }

    @Override
    public ModelResult<Boolean> deleteById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        int size = dao.update("ShoppingCartMapper.deleteById", param);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>().withError("exception", "删除失败");
    }

    @Override
    public ModelResult<Boolean> deleteByMemberIdAndGoodsId(Integer memberId, Integer goodsId) {
        Map<String,Object> param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("goodsId", goodsId);
        int size = dao.update("ShoppingCartMapper.deleteByMemberIdAndGoodsId", param);
        if(size ==1){
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }


    @Override
    public ModelResult<ShoppingCartVO> queryById(Integer id, Integer memberId) {
        if (id == null || memberId == null) {
            return new ModelResult<>().withError("paramException", "参数id不能为null");
        }

        ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("memberId", memberId);
        ShoppingCart shoppingCart = dao.queryUnique("ShoppingCartMapper.selectById", param);
        try {
            BeanUtils.copyProperties(shoppingCartVO, shoppingCart);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        DigitalFurniture digitalFurniture = digitalFurnitureManager.queryById(shoppingCart.getGoodsId());
        shoppingCartVO.setDigitalFurniture(digitalFurniture);
        return new ModelResult<>(shoppingCartVO);
    }

    @Override
    public ModelResult<Boolean> updateById(ShoppingCart shoppingCart) {
        int size = dao.updateByObj("ShoppingCartMapper.updateById", shoppingCart);
        if (size == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>().withError("updateException", "更新失败");
    }

    @Override
    public ModelResult<Boolean> saveList(List<Integer> goodsList, Integer memberId) {
        ShoppingCart shoppingCart = null;
        for (Integer item : goodsList) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setGoodsId(item);
            shoppingCart.setMemberId(memberId);
            shoppingCart.setCreateTime(Calendar.getInstance());
            save(shoppingCart);
        }

        return new ModelResult<>(Boolean.TRUE);
    }

    @Override
    public ModelResult<ShoppingCart> queryByGoodsId(Integer goodsId, Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", goodsId);
        param.put("memberId", memberId);
        ShoppingCart shoppingCart = dao.queryUnique("ShoppingCartMapper.selectByGoodsId", param);
        return new ModelResult<>(shoppingCart);
    }

    @Override
    public ModelResult<Boolean> batchRemove(List<Integer> idList, Integer memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("ids", idList);
        param.put("memberId", memberId);
        try {
            dao.update("ShoppingCartMapper.batchDelete", param);
        } catch (Exception e) {
            logger.error("ShoppingCartMapper.batchDelete 异常", e);
            return new ModelResult<>().withError("exception", "删除异常");
        }

        return new ModelResult<>(Boolean.TRUE);
    }
}
