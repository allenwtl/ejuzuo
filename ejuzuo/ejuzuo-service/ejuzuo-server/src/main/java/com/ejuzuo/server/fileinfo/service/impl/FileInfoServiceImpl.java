package com.ejuzuo.server.fileinfo.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.option.FileInfoOption;
import com.ejuzuo.common.service.FileInfoService;
import com.ejuzuo.server.fileinfo.manager.FileInfoManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by allen on 2016/4/3.
 */

@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {

    @Resource
    private FileInfoManager fileInfoManager;


    @Override
    public ModelResult<FileInfo> queryById(Integer id) {
        return new ModelResult<>(fileInfoManager.selectById(id));
    }

    @Override
    public ModelResult<FileInfo> save(FileInfo fileInfo) {
    	ModelResult<FileInfo> result = new ModelResult<FileInfo>();
    	FileInfo obj = fileInfoManager.save(fileInfo);
    	result.setModel(obj);
    	return result;
    }

	@Override
	public PageResult<FileInfo> queryPage(DataPage<FileInfo> dataPage, FileInfoOption qVo) {
		PageResult<FileInfo> result = new PageResult<FileInfo>();
		dataPage = fileInfoManager.queryPage(dataPage,qVo);
		result.setPage(dataPage);
		return result;
	}
}
