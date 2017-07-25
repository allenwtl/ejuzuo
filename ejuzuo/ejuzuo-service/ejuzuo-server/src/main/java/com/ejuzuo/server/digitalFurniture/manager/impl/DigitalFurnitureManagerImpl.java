package com.ejuzuo.server.digitalFurniture.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.aicai.memcachedclient.MemcachedClient;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.util.MemcachedKeyUtil;
import com.ejuzuo.common.vo.DigitalFurnitureVO;
import com.ejuzuo.server.brand.manager.BrandManager;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
@Component
public class DigitalFurnitureManagerImpl implements DigitalFurnitureManager {

    @Resource(name = "dao")
    protected GenericDao dao;

    @Resource
    private CodeValueManager codeValueManager;

    @Resource
    private BrandManager brandManager ;

    @Resource(name = "memcachedClient")
    private MemcachedClient memcachedClient;

    @Override
    public DataPage<DigitalFurnitureVO> queryPage(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
        Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());

        DataPage<DigitalFurniture> dataPage2 =  dao.queryPage("DigitalFurnitureMapper.countPage", "DigitalFurnitureMapper.queryPage", map, dataPage);

        List<DigitalFurnitureVO> digitalFurnitureVOList = new ArrayList<>() ;

        DigitalFurnitureVO digitalFurnitureVO = null ;
        for (DigitalFurniture item: dataPage2.getDataList()){
            digitalFurnitureVO = new DigitalFurnitureVO();
            BeanUtils.copyProperties(item, digitalFurnitureVO);
            digitalFurnitureVO.setBrandName(brandManager.selectById(item.getBrand()).getModel().getName());
            digitalFurnitureVOList.add(digitalFurnitureVO);
        }
        DataPage<DigitalFurnitureVO> digitalFurnitureVODataPage = new DataPage<>();
        digitalFurnitureVODataPage.setDataList(digitalFurnitureVOList);
        return digitalFurnitureVODataPage;
    }

    @Override
    public DataPage<DigitalFurniture> queryPageUinon(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
        Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
        return dao.queryPage("DigitalFurnitureMapper.countPageUnionBrand", "DigitalFurnitureMapper.queryPageUnionBrand", map, dataPage);
    }


    @Override
    public DigitalFurniture insert(DigitalFurniture obj) {
        dao.insertAndReturnAffectedCount("DigitalFurnitureMapper.insert", obj);
        return obj;
    }

    @Override
    public DigitalFurniture update(DigitalFurniture obj) {
        obj.setUpdateTime(Calendar.getInstance());
        dao.updateByObj("DigitalFurnitureMapper.update", obj);
        return obj;
    }

    @Override
    public DigitalFurniture queryById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return dao.queryUnique("DigitalFurnitureMapper.selectById", map);
    }

    @Override
    public ModelResult<Integer> count(DigitalFurniture option) {

        return new ModelResult<>(dao.queryCount("DigitalFurnitureMapper.countPage", JSONUtils.object2MapSpecail(option)));
    }

    @Override
    public List<DigitalFurniture> queryInIds(List<Integer> ids) {
        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        List<DigitalFurniture> digitalFurnitureList = dao.queryList("DigitalFurnitureMapper.selectByIds", param);
        if (digitalFurnitureList == null) {
            digitalFurnitureList = new ArrayList<>();
        }
        return digitalFurnitureList;
    }

    @Override
    public Boolean updateStatus(Integer id, Integer shelfStatus, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("shelfStatus", shelfStatus);
        map.put("shelfTime", Calendar.getInstance());
        map.put("status", status);
        try {
            Integer updated = dao.update("DigitalFurnitureMapper.updateStatus", map);
            if (updated >= 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public ModelResult<Boolean> updateDownloadCount(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("updateTime", Calendar.getInstance());
        int result = dao.update("DigitalFurnitureMapper.updateDownload", map);
        if (result == 1) {
            return new ModelResult<>(Boolean.TRUE);
        }
        return new ModelResult<>(Boolean.FALSE);
    }

    @Override
    public ModelResult<List<DigitalFurnitureVO>> queryLatestListByBrand(int brandId, int size, int digitalId, String spaceCategory, String style, Integer type) {
        Map<String, Object> param = new HashMap<>();
        param.put("brandId", brandId);
        param.put("size", size);
        param.put("digitalId", digitalId);
        if (spaceCategory != null) {
        	param.put("spaceCategory", spaceCategory);
		}
        if (style != null) {
        	param.put("style", style);
		}
        if (type != null) {
        	param.put("type", type);
		}
        List<DigitalFurniture> digitalFurnitureList = dao.queryList("DigitalFurnitureMapper.selectLatestListByBrand", param);
        List<DigitalFurnitureVO> digitalFurnitureVOList = new ArrayList<>();
        DigitalFurnitureVO digitalFurnitureVO = null;
        for (DigitalFurniture vo : digitalFurnitureList) {
            digitalFurnitureVO = new DigitalFurnitureVO();
            BeanUtils.copyProperties(vo, digitalFurnitureVO);
            digitalFurnitureVO.setStyleVo(codeValueManager.queryOne(CollectionCode.fengge, vo.getStyle(), null).getModel().getValueName());
            digitalFurnitureVOList.add(digitalFurnitureVO);
        }

        return new ModelResult<>(digitalFurnitureVOList);
    }

    @Override
    public ModelResult<List<DigitalFurnitureVO>> queryLatestListWithoutBrand(int brandId, int size, String spaceCategory, String style, Integer type) {
        Map<String, Object> param = new HashMap<>();
        param.put("brandId", brandId);
        param.put("size", size);
        if (spaceCategory != null) {
        	param.put("spaceCategory", spaceCategory);
		}
        if (style != null) {
        	param.put("style", style);
		}
        if (type != null) {
        	param.put("type", type);
		}
        List<DigitalFurniture> digitalFurnitureList = dao.queryList("DigitalFurnitureMapper.selectLatestListWithoutBrand", param);
        List<DigitalFurnitureVO> digitalFurnitureVOList = new ArrayList<>();
        DigitalFurnitureVO digitalFurnitureVO = null;
        for (DigitalFurniture vo : digitalFurnitureList) {
            digitalFurnitureVO = new DigitalFurnitureVO();
            BeanUtils.copyProperties(vo, digitalFurnitureVO);
            digitalFurnitureVO.setStyleVo(codeValueManager.queryOne(CollectionCode.fengge, vo.getStyle(), null).getModel().getValueName());
            digitalFurnitureVOList.add(digitalFurnitureVO);
        }

        return new ModelResult<>(digitalFurnitureVOList);
    }
    
    @Override
	public ModelResult<List<DigitalFurnitureVO>> queryLatestDecoration(int brandId, int size, String spaceCategory,
			String style, Integer type) {
        Map<String, Object> param = new HashMap<>();
        param.put("brandId", brandId);
        param.put("size", size);
        if (spaceCategory != null) {
        	param.put("spaceCategory", spaceCategory);
		}
        if (style != null) {
        	param.put("style", style);
		}
        if (type != null) {
        	param.put("type", type);
		}
        List<DigitalFurniture> digitalFurnitureList = dao.queryList("DigitalFurnitureMapper.queryLatestDecoration", param);
        List<DigitalFurnitureVO> digitalFurnitureVOList = new ArrayList<>();
        DigitalFurnitureVO digitalFurnitureVO = null;
        for (DigitalFurniture vo : digitalFurnitureList) {
            digitalFurnitureVO = new DigitalFurnitureVO();
            BeanUtils.copyProperties(vo, digitalFurnitureVO);
            digitalFurnitureVO.setStyleVo(codeValueManager.queryOne(CollectionCode.fengge, vo.getStyle(), null).getModel().getValueName());
            digitalFurnitureVOList.add(digitalFurnitureVO);
        }

        return new ModelResult<>(digitalFurnitureVOList);
    }

    @Override
    public List<DigitalFurniture> queryByIds(List<Integer> goodsIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsIds", goodsIds);
        return dao.queryList("DigitalFurnitureMapper.queryByIds", map);
    }

    @Override
    public ModelResult<List<DigitalFurnitureVO>> queryLastTimeAndPayBrand(DigitalFurniture qVo) {
        //queryLastTime    queryPayBrand
        List<DigitalFurnitureVO> resultList = new ArrayList<>();

        Map<String, Object> params = JSONUtils.object2MapSpecail(qVo);
        List<DigitalFurniture> queryLastTimeList = dao.queryList("DigitalFurnitureMapper.queryLastTime", params);

        if (queryLastTimeList == null || queryLastTimeList.isEmpty()){
            return new ModelResult<>(resultList);
        }

        DigitalFurniture queryPayBrandOne = dao.queryUnique("DigitalFurnitureMapper.queryPayBrand", params);
        DigitalFurnitureVO digitalFurnitureVO = null ;
        Brand brand = null ;

        for (DigitalFurniture item : queryLastTimeList) {
            digitalFurnitureVO = new DigitalFurnitureVO();
            BeanUtils.copyProperties(item, digitalFurnitureVO);
            brand = brandManager.selectById(item.getBrand()).getModel();
            digitalFurnitureVO.setBrandName(brand.getName());
            resultList.add(digitalFurnitureVO);
        }

        if(queryPayBrandOne == null){
            return new ModelResult<>(resultList);
        }

        Iterator<DigitalFurnitureVO> iterator = resultList.iterator();
        while (iterator.hasNext()){
            digitalFurnitureVO = iterator.next() ;
            if(digitalFurnitureVO.getId() == queryPayBrandOne.getId()){
                iterator.remove();
            }
        }

        if( resultList.size() == 4){
            resultList.remove(3);
        }

        DigitalFurnitureVO queryPayBrandOneVO = new DigitalFurnitureVO();
        queryPayBrandOneVO.setBrandName(brandManager.selectById(queryPayBrandOne.getBrand()).getModel().getName());
        BeanUtils.copyProperties(queryPayBrandOne, queryPayBrandOneVO);
        resultList.add(queryPayBrandOneVO);

        return new ModelResult<>(resultList);
    }

    @Override
    public ModelResult<Boolean> deleteMemcachedLastTimeAndPayBrand(int brandId) {
        Map<String, Object> params = new HashMap<>();
        params.put("brand", brandId);
        List<String> spaceList = dao.queryList("DigitalFurnitureMapper.queryByBrand", params);

        for (String str : spaceList){
            memcachedClient.delete(MemcachedKeyUtil.FRONT_INDEX_DF+"_"+str);
        }
        return new ModelResult<>(Boolean.TRUE);
    }

	@Override
	public DataPage<DigitalFurniture> queryPage1(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
        Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
        map.put("order", dataPage.getOrder());
        map.put("orderBy", dataPage.getOrderBy());
        return dao.queryPage("DigitalFurnitureMapper.countPage", "DigitalFurnitureMapper.queryPage", map, dataPage);
    }

    @Override
    public List<DigitalFurniture> queryList(Integer type) {
        Map<String,Object> param = new HashMap<>();
        param.put("type", type);
        return dao.queryList("DigitalFurnitureMapper.queryList", param);
    }
}
