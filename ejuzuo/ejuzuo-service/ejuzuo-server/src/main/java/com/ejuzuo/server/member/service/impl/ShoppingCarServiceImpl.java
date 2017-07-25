package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.PayStatus;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.ShoppingCart;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.service.DownloadInfoService;
import com.ejuzuo.common.service.MemberService;
import com.ejuzuo.common.service.ShoppingCarService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.vo.ShoppingCartVO;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import com.ejuzuo.server.downloadinfo.manager.DownloadInfoManager;
import com.ejuzuo.server.member.manager.MemberPointManager;
import com.ejuzuo.server.member.manager.ShoppingCartManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/15 0015.
 */

@Service("shoppingCarService")
public class ShoppingCarServiceImpl implements ShoppingCarService {
    
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private ShoppingCartManager shoppingCartManager;

    @Resource
    private MemberPointManager memberPointManager;

    @Resource
    private DownloadInfoManager downloadInfoManager;

    @Resource
    private DigitalFurnitureManager digitalFurnitureManager;
    
    @Resource
    private MemberService memberService;
    
    @Resource
    private DownloadInfoService downloadInfoService;

    @Override
    public ModelResult<ShoppingCartVO> queryById(Integer id, Integer memberId) {
        return shoppingCartManager.queryById(id, memberId);
    }

    @Override
    public ModelResult<Boolean> updateById(ShoppingCart shoppingCart) {
        return shoppingCartManager.updateById(shoppingCart);
    }

    @Override
    public ModelResult<Boolean> saveList(List<Integer> goodsList, Integer memberId) {
        return shoppingCartManager.saveList(goodsList, memberId);
    }

    @Override
    public ModelResult<ShoppingCart> save(ShoppingCart shoppingCart) {
        return shoppingCartManager.save(shoppingCart);
    }

    @Override
    public ModelResult<Boolean> remove(ShoppingCart shoppingCart) {
        return shoppingCartManager.deleteById(shoppingCart.getId());
    }

    @Override
    public ModelResult<DataPage<ShoppingCartVO>> queryPage(Integer memberId, DataPage<ShoppingCartVO> dataPage) {
        return shoppingCartManager.queryPage(memberId, dataPage);
    }

    @Override
    public ModelResult<Boolean> batchRemove(List<Integer> idList, Integer memberId) {
        return shoppingCartManager.batchRemove(idList, memberId);
    }

    @Override
    public ModelResult<Boolean> settle(List<Integer> idList, Integer memberId) {
        DownloadInfo downloadInfo = null ;
        List<DigitalFurniture> digitalFurnitureList = digitalFurnitureManager.queryInIds(idList);
        if(digitalFurnitureList == null){
            return new ModelResult<>().withError("exception","找不到购物车中相对应的商品");
        }

        //普通用户5次/天，高级用户10次/天，vip20次/天。 begin
        DownloadInfoOption query = new DownloadInfoOption();
        query.setMemberId(Long.valueOf(memberId));
        query.setPayStatus(PayStatus.PAY_SUCCESS.getIndex());
        query.setStatus(Status.STATUS.getIndex());
        query.setPayBeginTime(DateUtil.getTheDayZero());
        query.setPayEndTime(DateUtil.getTheDayMidnight());
        Integer count = downloadInfoService.countByPage(query);            
        
        boolean canDownload = false;
        int limit = 5;
      	if(memberService.isDesingerByMemberId(memberId)){
      		limit = 10;
    	}	
        if(memberService.isVipByMemberId(memberId)) { 
        	limit = 20;
        }
        Integer allCount = idList.size() + count;
        if (allCount < limit) {
           canDownload = true;
        }
        if (!canDownload) {                  
	        logger.info("下载频次超限！");
	        return new ModelResult<>().withError("exception","下载频次超限：普通用户5次/天，高级用户10次/天，vip用户20次/天。");
        }            
        //end
              
        List<DownloadInfo> downloadInfoList = new ArrayList<>();
        BigDecimal totalMoney = new BigDecimal(0);
        for (DigitalFurniture item : digitalFurnitureList) {
            downloadInfo = getDownloadInfo(item);
            downloadInfo.setMemberId(memberId);
            downloadInfoManager.save(downloadInfo);
            downloadInfoList.add(downloadInfo);
            totalMoney = totalMoney.add(downloadInfo.getActualPrice());
        }

        return memberPointManager.totalPay(totalMoney, downloadInfoList, memberId);
    }


    private DownloadInfo getDownloadInfo(DigitalFurniture digitalFurniture){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setGoodsId(digitalFurniture.getId());
        downloadInfo.setFileId(digitalFurniture.getFileId());
        downloadInfo.setPayStatus(PayStatus.UN_PAY.getIndex());
        downloadInfo.setActualPrice(BigDecimal.valueOf(digitalFurniture.getPointPrice()));
        downloadInfo.setPointPrice(new BigDecimal(digitalFurniture.getPointPrice()));
        downloadInfo.setStatus(Status.STATUS.getIndex());
        return downloadInfo;
    }
}
