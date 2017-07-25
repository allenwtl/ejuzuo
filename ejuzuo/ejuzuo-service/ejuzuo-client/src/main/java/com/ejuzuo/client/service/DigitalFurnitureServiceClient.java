package com.ejuzuo.client.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.memcachedclient.MemcachedClient;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.service.DigitalFurnitureService;
import com.ejuzuo.common.util.MemcachedKeyUtil;
import com.ejuzuo.common.vo.DigitalFurnitureVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/21 0021.
 */

@Service("digitalFurnitureServiceClient")
public class DigitalFurnitureServiceClient implements DigitalFurnitureService {

    @Resource(name = "memcachedClient")
    private MemcachedClient memcachedClient;

    @Resource(name = "digitalFurnitureService")
    private DigitalFurnitureService digitalFurnitureService ;

    @Override
    public PageResult<DigitalFurnitureVO> queryPage(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
        return digitalFurnitureService.queryPage(dataPage, qVo);
    }

    @Override
    public PageResult<DigitalFurniture> queryPageUinon(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
        return digitalFurnitureService.queryPageUinon(dataPage, qVo);
    }

    @Override
    public ModelResult<DigitalFurniture> save(DigitalFurniture obj) {
        return digitalFurnitureService.save(obj);
    }

    @Override
    public ModelResult<DigitalFurniture> update(DigitalFurniture obj) {
        return digitalFurnitureService.update(obj);
    }

    @Override
    public ModelResult<DigitalFurniture> queryById(Integer id) {
        return digitalFurnitureService.queryById(id);
    }

    @Override
    public ModelResult<List<DigitalFurniture>> queryByIds(List<Integer> ids) {
        return digitalFurnitureService.queryByIds(ids);
    }

    @Override
    public ModelResult<Integer> count(DigitalFurniture option) {
        return digitalFurnitureService.count(option);
    }

    @Override
    public ModelResult<Boolean> updateStatus(Integer id, Integer shelfStatus, Integer status) {
        return digitalFurnitureService.updateStatus(id, shelfStatus, status);
    }

    @Override
    public ModelResult<Boolean> updateDownloadCount(Integer id) {
        return digitalFurnitureService.updateDownloadCount(id);
    }

    @Override
    public ModelResult<List<DigitalFurnitureVO>> queryLatestListByBrand(int brandId, int size, int digitalId,String spaceCategory,String style, Integer type) {
        return digitalFurnitureService.queryLatestListByBrand(brandId, size, digitalId,spaceCategory,style,type);
    }

    @Override
    public ModelResult<List<DigitalFurnitureVO>> queryLatestListWithoutBrand(int brandId, int size,String spaceCategory,String style, Integer type) {
//        List<DigitalFurnitureVO> digitalFurnitureList = memcachedClient.get(MemcachedKeyUtil.DIGITAL_DETAIL_OTHER_BRAND+"_"+brandId);
//        if (digitalFurnitureList == null) {
//            digitalFurnitureList = digitalFurnitureService.queryLatestListWithoutBrand(brandId, size).getModel();
//            memcachedClient.add(MemcachedKeyUtil.DIGITAL_DETAIL_OTHER_BRAND+"_"+brandId, 2* 60 *60, digitalFurnitureList);
//        }
        return digitalFurnitureService.queryLatestListWithoutBrand(brandId, size,spaceCategory,style,type);
    }

    @Override
    public ModelResult<List<DigitalFurnitureVO>> queryLastTimeAndPayBrand(DigitalFurniture qVo) {
        List<DigitalFurnitureVO> digitalFurnitureList = memcachedClient.get( MemcachedKeyUtil.FRONT_INDEX_DF +"_"+qVo.getSpaceCategory()+"_"+qVo.getType());
        if( digitalFurnitureList == null ){
            digitalFurnitureList = digitalFurnitureService.queryLastTimeAndPayBrand(qVo).getModel();
            memcachedClient.add(MemcachedKeyUtil.FRONT_INDEX_DF +"_"+qVo.getSpaceCategory()+"_"+qVo.getType() , 1 * 60, digitalFurnitureList);
        }
        return new ModelResult<>(digitalFurnitureList);
    }

    @Override
    public ModelResult<Boolean> deleteMemcachedLastTimeAndPayBrand(int brandId) {
        return digitalFurnitureService.deleteMemcachedLastTimeAndPayBrand(brandId);
    }

	@Override
	public PageResult<DigitalFurniture> queryPage1(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
		return digitalFurnitureService.queryPage1(dataPage, qVo);
	}

	@Override
	public ModelResult<List<DigitalFurnitureVO>> queryLatestDecoration(int brandId, int size, String spaceCategory,
			String style, Integer type) {
		return digitalFurnitureService.queryLatestDecoration(brandId, size, spaceCategory, style, type);
	}
}
