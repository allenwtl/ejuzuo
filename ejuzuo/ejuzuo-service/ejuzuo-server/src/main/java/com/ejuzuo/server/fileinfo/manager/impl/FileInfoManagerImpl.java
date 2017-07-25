package com.ejuzuo.server.fileinfo.manager.impl;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.option.FileInfoOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.fileinfo.manager.FileInfoManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allen on 2016/4/3.
 */

@Component
public class FileInfoManagerImpl implements FileInfoManager{

    private static final Logger logger = LoggerFactory.getLogger(FileInfoManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("FileInfoMapper.deleteById", id);
    }

    @Override
    public FileInfo save(FileInfo record) {
        record.setStatus(Status.STATUS.getIndex());
        record.setUploadTime(Calendar.getInstance());
        record.setUpdateTime(Calendar.getInstance());
        record.setCreateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("FileInfoMapper.save", record);
        return record;
    }

    @Override
    public FileInfo selectById(Integer id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("id", id);
        return dao.queryUnique("FileInfoMapper.selectById", map);
    }

    @Override
    public int updateById(FileInfo record) {
        record.setUpdateTime(Calendar.getInstance());
        return dao.updateByObj("FileInfoMapper.updateById", record);
    }

	@Override
	public DataPage<FileInfo> queryPage(DataPage<FileInfo> dataPage, FileInfoOption qVo) {
		Map<String, Object> map = JSONUtils.object2MapSpecail(qVo);
		map.put("order", dataPage.getOrder());
		map.put("orderBy", dataPage.getOrderBy());
		Calendar beginDate = qVo.getBeginDate();
		Calendar endDate = qVo.getEndDate();
		if (beginDate != null) {
			beginDate.set(Calendar.HOUR_OF_DAY, 0);
			beginDate.set(Calendar.MINUTE, 0);
			beginDate.set(Calendar.SECOND, 0);
			beginDate.set(Calendar.MILLISECOND, 0);
			map.put("beginDate", beginDate);
		}
		if (endDate != null) {
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			map.put("endDate", endDate);
		}
		return dao.queryPage("FileInfoMapper.countPage", "FileInfoMapper.queryPage", map, dataPage);
	}

	@Override
	public List<FileInfo> queryByIds(List<Integer> fileIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileIds", fileIds);
		return dao.queryList("FileInfoMapper.queryByIds", map);
	}
}
