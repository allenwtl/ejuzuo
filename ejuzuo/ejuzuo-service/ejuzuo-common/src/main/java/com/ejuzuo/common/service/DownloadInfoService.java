package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.option.DownloadInfoOption;

/**
 * Created by allen on 2016/4/2.
 */
public interface DownloadInfoService {


    ModelResult<DownloadInfo> addDownloadInfo(DownloadInfo downloadInfo);

    ModelResult<DataPage<DownloadInfo>> queryPage(DownloadInfoOption option, DataPage<DownloadInfo> dataPage);

    ModelResult<DownloadInfo> judgeAvailableFile(Integer id);

    ModelResult<Integer> countPage(DownloadInfoOption option);

    ModelResult<DownloadInfo> createDownloadInfoAndPay(DownloadInfo downloadInfo);

    ModelResult<DownloadInfo> queryById(Integer id);

    ModelResult<DownloadInfo> queryByOption(DownloadInfoOption option);
    
    int countByPage(DownloadInfoOption option);

}
