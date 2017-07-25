package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ShoppingCart;
import com.ejuzuo.common.vo.ShoppingCartVO;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/15 0015.
 */
public interface ShoppingCarService {

    ModelResult<ShoppingCartVO> queryById(Integer id, Integer memberId);

    ModelResult<Boolean> updateById(ShoppingCart shoppingCart);

    ModelResult<Boolean> saveList(List<Integer> goodsList, Integer memberId);

    ModelResult<ShoppingCart> save(ShoppingCart shoppingCart);

    ModelResult<Boolean> remove(ShoppingCart shoppingCart);

    ModelResult<DataPage<ShoppingCartVO>> queryPage(Integer memberId, DataPage<ShoppingCartVO> dataPage);

    ModelResult<Boolean> batchRemove(List<Integer> idList, Integer memberId);

    /**
     * 结算
     * @return
     */
    ModelResult<Boolean> settle(List<Integer> idList, Integer memberId);

}
