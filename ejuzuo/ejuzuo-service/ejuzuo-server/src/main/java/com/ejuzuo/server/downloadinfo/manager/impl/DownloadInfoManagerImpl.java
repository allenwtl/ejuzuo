package com.ejuzuo.server.downloadinfo.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.type.DownloadPayStatus;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.server.downloadinfo.manager.DownloadInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allen on 2016/4/2.
 */

@Component
public class DownloadInfoManagerImpl implements DownloadInfoManager {

    private static final Logger logger = LoggerFactory.getLogger(DownloadInfoManagerImpl.class);

    @Resource(name = "dao")
    private GenericDao dao;

    @Override
    public int deleteById(Integer id) {
        return dao.updateByObj("DownloadInfoMapper.deleteById", id);
    }

    @Override
    public DownloadInfo save(DownloadInfo downloadInfo) {
        downloadInfo.setStatus(Status.STATUS.getIndex());
        downloadInfo.setPayStatus(DownloadPayStatus.UN_PAY.getIndex());
        downloadInfo.setCreateTime(Calendar.getInstance());
        downloadInfo.setUpdateTime(Calendar.getInstance());
        int size = dao.insertAndReturnAffectedCount("DownloadInfoMapper.save", downloadInfo);
        if(size == 1){
            return downloadInfo ;
        }
        return null;
    }

    @Override
    public DownloadInfo queryById(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return dao.queryUnique("DownloadInfoMapper.selectByOption", param);
    }

    @Override
    public DownloadInfo queryByOption(DownloadInfoOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        return dao.queryUnique("DownloadInfoMapper.selectByOption", param);
    }

    @Override
    public List<DownloadInfo> queryListByOption(DownloadInfoOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        param.put("nowTime", Calendar.getInstance());
        return dao.queryList("DownloadInfoMapper.selectByOption", param);
    }

    @Override
    public int updateById(DownloadInfo record) {
        record.setUpdateTime(Calendar.getInstance());
        return dao.updateByObj("DownloadInfoMapper.updateById", record);
    }

    @Override
    public ModelResult<DataPage<DownloadInfo>> queryPage(DownloadInfoOption option, DataPage<DownloadInfo> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        DataPage<DownloadInfo> resultModel = dao.queryPage("DownloadInfoMapper.queryPageCount", "DownloadInfoMapper.queryPage", param, dataPage);
        return new ModelResult<>(resultModel);
    }

    @Override
    public ModelResult<DownloadInfo> judgeAvailableFile(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        DownloadInfo downloadInfo = dao.queryUnique("DownloadInfoMapper.selectByOption", param);
        if (downloadInfo == null) {
            logger.info("文件订单:id:[{}],表中不存在该记录", id);
            return new ModelResult<>(Boolean.FALSE).withError("exception", "表中不存在该记录");
        }

        if (Calendar.getInstance().compareTo(downloadInfo.getExpire()) > 0) {
            logger.info("文件订单:id:[{}],超过了有效期", id);
            return new ModelResult<>(Boolean.FALSE).withError("exception", "超过了有效期");
        }

        if (downloadInfo.getPayStatus() == DownloadPayStatus.UN_PAY.getIndex()) {
            logger.info("文件订单:id:[{}],未支付", id);
            return new ModelResult<>(Boolean.FALSE).withError("exception", "未支付");
        }

        if (downloadInfo.getStatus() == Status.UN_STATUS.getIndex()) {
            logger.info("文件订单:id:[{}],无效", id);
            return new ModelResult<>(Boolean.FALSE).withError("exception", "无效");
        }

        logger.info("文件订单:id:[{}],可以下载", id);
        return new ModelResult<>(downloadInfo);
    }

    @Override
    public ModelResult<Integer> countPage(DownloadInfoOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        param.put("nowTime", Calendar.getInstance());
        int size = dao.queryCount("DownloadInfoMapper.queryPageCount", param);
        return new ModelResult<>(size);
    }
    
    @Override
    public int countByPage(DownloadInfoOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        //param.put("nowTime", Calendar.getInstance());
        int size = dao.queryCount("DownloadInfoMapper.queryPageCount", param);
        return size;
    }
}
