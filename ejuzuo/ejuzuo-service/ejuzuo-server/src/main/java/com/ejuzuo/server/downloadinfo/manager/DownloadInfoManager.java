package com.ejuzuo.server.downloadinfo.manager;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.option.DownloadInfoOption;

import java.util.List;


public interface DownloadInfoManager {
    int deleteById(Integer id);

    DownloadInfo save(DownloadInfo record);

    DownloadInfo queryById(Integer id);

    DownloadInfo queryByOption(DownloadInfoOption option);

    /**
     * 在有效期内已经支付的订单
     * @param option
     * @return
     */
    List<DownloadInfo> queryListByOption(DownloadInfoOption option);

    int updateById(DownloadInfo record);

    /**
     * 查询 在有效期内且已经支付的 可下载的文件
     * @param option
     * @param dataPage
     * @return
     */
    ModelResult<DataPage<DownloadInfo>> queryPage(DownloadInfoOption option, DataPage<DownloadInfo> dataPage);

    /**
     * 判断传入的id所代表的文件是否是可下载
     * @return
     */
    ModelResult<DownloadInfo> judgeAvailableFile (Integer id);

    /**
     * 统计条数
     * @param option
     * @return
     */
    ModelResult<Integer> countPage(DownloadInfoOption option);
    
    
    public int countByPage(DownloadInfoOption option);


}