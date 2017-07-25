package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.vo.FavoriteVO;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
public interface MemberFavoriteService {

    ModelResult<MemberFavorite> save(MemberFavorite memberFavorite);

    ModelResult<DataPage<MemberFavorite>> queryByPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage);

    ModelResult<Boolean> deleteById(Integer id);

    ModelResult<Boolean> deleteByIds(List<Integer> ids, Integer memberId);

    ModelResult<Integer> count(MemberFavoriteOption option);

    ModelResult<List<MemberFavorite>> queryList(MemberFavoriteOption option);

    /**
     * 收藏or不收藏
     * @return
     */
    ModelResult<Integer> collect(MemberFavorite memberFavorite);

    /**
     * 用户中心
     * @param option
     * @return
     */
    ModelResult<List<FavoriteVO>> queryVOList(MemberFavoriteOption option);

    PageResult<FavoriteVO> queryVOPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage);

}
