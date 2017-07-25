package com.ejuzuo.server.index.service.impl;

import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.type.DigitalShelfStatus;
import com.ejuzuo.common.service.IndexService;
import com.ejuzuo.common.vo.GoodsNavigationVO;
import com.ejuzuo.server.codevalue.manager.CodeValueManager;
import com.ejuzuo.server.digitalFurniture.manager.DigitalFurnitureManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlun.wu on 2016/5/16 0016.
 */

@Service("indexService")
public class IndexServiceImpl implements IndexService {


    @Resource
    private CodeValueManager codeValueManager ;

    @Resource
    private DigitalFurnitureManager digitalFurnitureManager ;

    @Resource
    private Map<String, String> codeValueToCss;

    @Override
    public List<GoodsNavigationVO> queryAllGoods() {
        List<GoodsNavigationVO> voList = new ArrayList<>();

        List<CodeValue> codeValueList = codeValueManager.queryList(CollectionCode.kongjian, null, null).getModel();
        GoodsNavigationVO goodsNavigationVO;

        List<CodeValue> subList = null ;
        DigitalFurniture item = null ;
        for (CodeValue codeValue : codeValueList) {
            goodsNavigationVO = new GoodsNavigationVO() ;
            subList = codeValueManager.queryList(CollectionCode.kongjianxiaolei, null , codeValue.getValueCode()).getModel();
            goodsNavigationVO.setSubList(subList);
            String value = codeValueToCss.get(codeValue.getValueName());
            goodsNavigationVO.setBz(value);
            goodsNavigationVO.setExtension(codeValue.getExtension());
            item = new DigitalFurniture();
            item.setStatus(1);
            item.setSpaceCategory(codeValue.getValueCode());
            item.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
            goodsNavigationVO.setTotalCount(digitalFurnitureManager.count(item).getModel());
            goodsNavigationVO.setValueCode(codeValue.getValueCode());
            goodsNavigationVO.setValueName(codeValue.getValueName());
            voList.add(goodsNavigationVO);
        }

        return voList;
    }
}
