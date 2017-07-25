package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.vo.DigitalFurnitureVO;

import java.util.List;


/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public interface DigitalFurnitureService {

	PageResult<DigitalFurnitureVO> queryPage(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo);

	PageResult<DigitalFurniture> queryPageUinon(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo);
	
	ModelResult<DigitalFurniture> save(DigitalFurniture obj);
	
	ModelResult<DigitalFurniture> update(DigitalFurniture obj);
	
	ModelResult<DigitalFurniture> queryById(Integer id);

	ModelResult<List<DigitalFurniture>> queryByIds(List<Integer> ids);

	ModelResult<Integer> count(DigitalFurniture option);

	ModelResult<Boolean> updateStatus(Integer id, Integer shelfStatus, Integer status);

	/**
	 * 更新下载量
	 * @param id
	 * @return
	 */
	ModelResult<Boolean> updateDownloadCount(Integer id);

	/**
	 * 查询最新的数字家具（按照时间来）
	 * @param brandId
	 * @param size
	 * @param digitalId
	 * @return
	 */
	ModelResult<List<DigitalFurnitureVO>> queryLatestListByBrand(int brandId, int size, int digitalId,String spaceCategory,String style, Integer type);

	/**
	 * 查询其它品牌的数字家具（按照时间来）
	 * @param brandId
	 * @param size
	 * @return
	 */
	ModelResult<List<DigitalFurnitureVO>> queryLatestListWithoutBrand(int brandId, int size,String spaceCategory,String style, Integer type);

	/**
	 * 查询拍套饰品（按照时间来）
	 * @param brandId
	 * @param size
	 * @return
	 */
	ModelResult<List<DigitalFurnitureVO>> queryLatestDecoration(int brandId, int size,String spaceCategory,String style, Integer type);

	/**
	 * 按照最新时间和最新付费的品牌
	 * @param qVo
	 * @return
	 */
	ModelResult<List<DigitalFurnitureVO>> queryLastTimeAndPayBrand(DigitalFurniture qVo);

	/**
	 * 删除与品牌相关的大类缓存
	 * @param brandId
	 * @return
	 */
	ModelResult<Boolean> deleteMemcachedLastTimeAndPayBrand(int brandId);

	PageResult<DigitalFurniture> queryPage1(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo);

}
