package com.ejuzuo.server.digitalFurniture.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.type.DigitalShelfStatus;
import com.ejuzuo.common.service.DigitalFurnitureService;
import com.ejuzuo.common.vo.DigitalFurnitureVO;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import com.ejuzuo.server.elasticsearch.manager.BaseIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */

@Service("digitalFurnitureService")
public class DigitalFurnitureServiceImpl implements DigitalFurnitureService {
	
	@Resource
	private DigitalFurnitureManager digitalFurnitureManager;
	@Autowired
	private TaskExecutor taskExecutor;
	@Resource
	private BaseIndexManager<DigitalFurniture> baseIndexManager;
	@Override
	public PageResult<DigitalFurnitureVO> queryPage(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
		PageResult<DigitalFurnitureVO> pageResult = new PageResult<DigitalFurnitureVO>();
		pageResult.setPage(digitalFurnitureManager.queryPage(dataPage,qVo));
		return pageResult;
	}

	@Override
	public PageResult<DigitalFurniture> queryPageUinon(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
		PageResult<DigitalFurniture> pageResult = new PageResult<DigitalFurniture>();
		dataPage = digitalFurnitureManager.queryPageUinon(dataPage, qVo);
		pageResult.setPage(dataPage);
		return pageResult;
	}

	@Override
	public ModelResult<DigitalFurniture> save(DigitalFurniture obj) {
		ModelResult<DigitalFurniture> result = new ModelResult<DigitalFurniture>();
		obj = digitalFurnitureManager.insert(obj);
		result.setModel(obj);
		return result;
	}
	@Override
	public ModelResult<DigitalFurniture> update(DigitalFurniture obj) {
		ModelResult<DigitalFurniture> result = new ModelResult<DigitalFurniture>();
		obj = digitalFurnitureManager.update(obj);
		this.updateESIndex(obj.getId());
		result.setModel(obj);
		return result;
	}
	@Override
	public ModelResult<DigitalFurniture> queryById(Integer id) {
		ModelResult<DigitalFurniture> result = new ModelResult<DigitalFurniture>();
		DigitalFurniture obj = digitalFurnitureManager.queryById(id);
		result.setModel(obj);
		return result;
	}

	@Override
    public ModelResult<List<DigitalFurniture>> queryByIds(List<Integer> ids) {
        return new ModelResult<>(digitalFurnitureManager.queryInIds(ids));
    }

	@Override
	public ModelResult<Integer> count(DigitalFurniture option) {
		return digitalFurnitureManager.count(option);
	}

	@Override
	public ModelResult<Boolean> updateStatus(Integer id, Integer shelfStatus, Integer status) {
		ModelResult<Boolean> result = new ModelResult<Boolean>();
		Boolean updated = digitalFurnitureManager.updateStatus(id,shelfStatus,status);
		if (updated) {
			System.out.println("更新成功");
			this.updateESIndex(id);
		}
		result.setModel(updated);
		return result;
	}

	@Override
	public ModelResult<Boolean> updateDownloadCount(Integer id) {
		return digitalFurnitureManager.updateDownloadCount(id);
	}

	@Override
	public ModelResult<List<DigitalFurnitureVO>> queryLatestListByBrand(int brandId, int size, int digitalId,String spaceCategory,String style, Integer type) {
		return digitalFurnitureManager.queryLatestListByBrand(brandId, size, digitalId,spaceCategory,style,type);
	}

	@Override
	public ModelResult<List<DigitalFurnitureVO>> queryLatestListWithoutBrand(int brandId, int size,String spaceCategory,String style, Integer type) {
		return digitalFurnitureManager.queryLatestListWithoutBrand(brandId, size,spaceCategory,style,type);
	}
	
	@Override
	public ModelResult<List<DigitalFurnitureVO>> queryLatestDecoration(int brandId, int size, String spaceCategory,
			String style, Integer type) {
		return digitalFurnitureManager.queryLatestDecoration(brandId, size,spaceCategory,style,type);
	}

	@Override
	public ModelResult<List<DigitalFurnitureVO>> queryLastTimeAndPayBrand(DigitalFurniture qVo) {
		return digitalFurnitureManager.queryLastTimeAndPayBrand(qVo);
	}

	@Override
	public ModelResult<Boolean> deleteMemcachedLastTimeAndPayBrand(int brandId) {
		return digitalFurnitureManager.deleteMemcachedLastTimeAndPayBrand(brandId);
	}

//	private void deleteESIndex(Integer id) {
//		taskExecutor.execute(new Runnable() {
//			@Override
//			public void run() {
//				DigitalFurniture obj = digitalFurnitureManager.queryById(id);
//				if (!(obj.getStatus().equals(Status.STATUS.getIndex()) && obj.getShelfStatus().equals(DigitalShelfStatus.SALES.getIndex()))) {
//					baseIndexManager.deleteById(id);
//				}
//			}
//		});
//	}

	private void updateESIndex(Integer id) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				DigitalFurniture obj = digitalFurnitureManager.queryById(id);
				System.out.println(obj.getStatus()+":"+obj.getShelfStatus());
				if (obj.getStatus().equals(Status.STATUS.getIndex()) && obj.getShelfStatus().equals(DigitalShelfStatus.SALES.getIndex())) {
					baseIndexManager.createIndex(obj);
				}else {
					baseIndexManager.deleteById(id);
				}
			}
		});
	}

	@Override
	public PageResult<DigitalFurniture> queryPage1(DataPage<DigitalFurniture> dataPage, DigitalFurniture qVo) {
		PageResult<DigitalFurniture> pageResult = new PageResult<DigitalFurniture>();
		dataPage = digitalFurnitureManager.queryPage1(dataPage,qVo);
		pageResult.setPage(dataPage);
		return pageResult;
	}

}
