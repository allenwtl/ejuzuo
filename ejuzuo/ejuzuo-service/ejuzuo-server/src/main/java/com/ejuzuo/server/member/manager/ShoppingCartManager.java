package com.ejuzuo.server.member.manager;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.ShoppingCart;
import com.ejuzuo.common.vo.ShoppingCartVO;

import java.util.List;

public interface ShoppingCartManager {

    ModelResult<ShoppingCart> save(ShoppingCart shoppingCart);

    ModelResult<DataPage<ShoppingCartVO>> queryPage(Integer memberId, DataPage<ShoppingCartVO> dataPage);

    ModelResult<ShoppingCartVO> queryById(Integer id, Integer memberId);

    ModelResult<Boolean> deleteById(Integer id);

    ModelResult<Boolean> deleteByMemberIdAndGoodsId(Integer memberId, Integer goodsId);

    ModelResult<Boolean> updateById(ShoppingCart shoppingCart);

    ModelResult<Boolean> saveList(List<Integer> goodsList, Integer memberId);

    ModelResult<ShoppingCart> queryByGoodsId(Integer goodsId, Integer memberId);

    ModelResult<Boolean> batchRemove(List<Integer> idList, Integer memberId);

}