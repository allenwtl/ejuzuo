package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.option.FileInfoOption;

/**
 * Created by allen on 2016/4/3.
 */
public interface FileInfoService {

    ModelResult<FileInfo> queryById(Integer id);

    ModelResult<FileInfo> save(FileInfo fileInfo);

	PageResult<FileInfo> queryPage(DataPage<FileInfo> dataPage, FileInfoOption qVo);


}
