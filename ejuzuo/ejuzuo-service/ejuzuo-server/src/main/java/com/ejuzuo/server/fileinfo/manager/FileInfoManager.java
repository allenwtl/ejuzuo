package com.ejuzuo.server.fileinfo.manager;


import java.util.List;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.option.FileInfoOption;

public interface FileInfoManager {
    int deleteById(Integer id);

    FileInfo save(FileInfo record);

    FileInfo selectById(Integer id);

    int updateById(FileInfo record);

	DataPage<FileInfo> queryPage(DataPage<FileInfo> dataPage, FileInfoOption qVo);

	List<FileInfo> queryByIds(List<Integer> fileIds);
}