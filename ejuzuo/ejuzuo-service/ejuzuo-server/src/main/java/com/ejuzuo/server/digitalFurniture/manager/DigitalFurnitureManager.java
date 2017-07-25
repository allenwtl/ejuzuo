package com.ejuzuo.server.digitalFurniture.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.vo.DigitalFurnitureVO;

import java.util.List;

public interface DigitalFurnitureManager {

    List<DigitalFurniture> queryInIds(List<Integer> ids);

	DataPage<DigitalFurnitureVO> queryPage(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo);

	DataPage<DigitalFurniture> queryPageUinon(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo);

	DigitalFurniture insert(DigitalFurniture obj);
	
	DigitalFurniture update(DigitalFurniture obj);
	
	DigitalFurniture queryById(Integer id);

	ModelResult<Integer> count(DigitalFurniture option);

	Boolean updateStatus(Integer id, Integer shelfStatus, Integer status);

	ModelResult<Boolean> updateDownloadCount(Integer id);

	/**
	 * 查询最新的数字家具（按照时间来）
	 * @param brandId
	 * @param size
	 * @param style 
	 * @param spaceCategory 
	 * @param type 
	 * @return
	 */
	ModelResult<List<DigitalFurnitureVO>> queryLatestListByBrand(int brandId, int size , int digitalId, String spaceCategory, String style, Integer type);

	/**
	 * 查询其它品牌的数字家具（按照时间来）
	 * @param brandId
	 * @param size
	 * @return
	 */
	ModelResult<List<DigitalFurnitureVO>> queryLatestListWithoutBrand(int brandId, int size, String spaceCategory, String style, Integer type);

	List<DigitalFurniture> queryByIds(List<Integer> goodsIds);

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

	DataPage<DigitalFurniture> queryPage1(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo);

	List<DigitalFurniture> queryList(Integer type);

	ModelResult<List<DigitalFurnitureVO>> queryLatestDecoration(int brandId, int size, String spaceCategory,
			String style, Integer type);

}