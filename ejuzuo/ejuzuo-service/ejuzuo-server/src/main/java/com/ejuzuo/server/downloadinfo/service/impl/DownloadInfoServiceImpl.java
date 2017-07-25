package com.ejuzuo.server.downloadinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.service.DownloadInfoService;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import com.ejuzuo.server.downloadinfo.manager.DownloadInfoManager;
import com.ejuzuo.server.fileinfo.manager.FileInfoManager;
import com.ejuzuo.server.member.manager.MemberManager;
import com.ejuzuo.server.member.manager.MemberPointManager;

/**
 * Created by allen on 2016/4/2.
 */

@Service("downloadInfoService")
public class DownloadInfoServiceImpl implements DownloadInfoService {

    @Resource
    private DownloadInfoManager downloadInfoManager;
    @Resource
    private MemberPointManager memberPointManager;
    @Resource
    private MemberManager memberManager;
    @Resource
    private DigitalFurnitureManager digitalFurnitureManager;
    @Resource
    private FileInfoManager fileInfoManager;
    
    @Override
    public ModelResult<DownloadInfo> addDownloadInfo(DownloadInfo downloadInfo) {
        return new ModelResult<>(downloadInfoManager.save(downloadInfo));
    }

    @Override
    public ModelResult<DataPage<DownloadInfo>> queryPage(DownloadInfoOption option, DataPage<DownloadInfo> dataPage) {
    	ModelResult<DataPage<DownloadInfo>> result = downloadInfoManager.queryPage(option, dataPage);
    	List<DownloadInfo> list = result.getModel().getDataList();
    	List<Integer> memberIds = new ArrayList<Integer>();
    	List<Integer> goodsIds = new ArrayList<Integer>();
    	List<Integer> fileIds = new ArrayList<Integer>();
    	for (DownloadInfo downloadInfo : list) {
			memberIds.add(downloadInfo.getMemberId());
			goodsIds.add(downloadInfo.getGoodsId());
			fileIds.add(downloadInfo.getFileId());
		}
    	List<Member> members = memberManager.querybyIds(memberIds);
    	List<DigitalFurniture> digitalFurnitures = digitalFurnitureManager.queryByIds(goodsIds);
    	List<FileInfo> fileInfos = fileInfoManager.queryByIds(fileIds);
    	for (DownloadInfo downloadInfo : list) {
    		for (Member member : members) {
    			if (downloadInfo.getMemberId().equals(member.getId())) {
					downloadInfo.setNickName(member.getNickName());
					downloadInfo.setAccount(member.getAccount());
					break;
    			}
    		}
    		for (DigitalFurniture digitalFurniture : digitalFurnitures) {
    			if (downloadInfo.getGoodsId().equals(digitalFurniture.getId())) {
					downloadInfo.setGoodsName(digitalFurniture.getName());
					break;
    			}
    		}
    		for (FileInfo fileInfo : fileInfos) {
    			if (downloadInfo.getFileId().equals(fileInfo.getId())) {
					downloadInfo.setFileName(fileInfo.getFileName());
					break;
				}
    		}
		}
        return result;
    }

    @Override
    public ModelResult<DownloadInfo> judgeAvailableFile(Integer id) {
        return downloadInfoManager.judgeAvailableFile(id);
    }

    @Override
    public ModelResult<Integer> countPage(DownloadInfoOption option) {
        return downloadInfoManager.countPage(option);
    }

    @Override
    public ModelResult<DownloadInfo> createDownloadInfoAndPay(DownloadInfo downloadInfo) {
        DownloadInfo result = downloadInfoManager.save(downloadInfo);
        return  memberPointManager.pay(result);
    }

    @Override
    public ModelResult<DownloadInfo> queryById(Integer id) {
        return new ModelResult<>(downloadInfoManager.queryById(id));
    }

    @Override
    public ModelResult<DownloadInfo> queryByOption(DownloadInfoOption option) {
        return new ModelResult<>(downloadInfoManager.queryByOption(option));
    }

	@Override
	public int countByPage(DownloadInfoOption option) {		
		return downloadInfoManager.countByPage(option);
	}
    
    
}
