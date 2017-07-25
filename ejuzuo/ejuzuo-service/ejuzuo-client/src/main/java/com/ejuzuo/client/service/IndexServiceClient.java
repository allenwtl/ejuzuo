package com.ejuzuo.client.service;

import com.aicai.memcachedclient.MemcachedClient;
import com.ejuzuo.common.service.IndexService;
import com.ejuzuo.common.util.MemcachedKeyUtil;
import com.ejuzuo.common.vo.GoodsNavigationVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/17 0017.
 */
@Service("indexServiceClient")
public class IndexServiceClient implements IndexService {

    @Resource(name = "indexService")
    private IndexService indexService ;

    @Resource(name="memcachedClient")
    public MemcachedClient memcachedClient ;


    @Override
    public List<GoodsNavigationVO> queryAllGoods() {
        List<GoodsNavigationVO> goodsNavigationVOList = memcachedClient.get(MemcachedKeyUtil.ALL_GOODS_TYPE_LIST);
        if(goodsNavigationVOList == null){
            goodsNavigationVOList = indexService.queryAllGoods();
            memcachedClient.add(MemcachedKeyUtil.ALL_GOODS_TYPE_LIST, 2 * 60 *60 , goodsNavigationVOList);
        }

        return goodsNavigationVOList;
    }
}
