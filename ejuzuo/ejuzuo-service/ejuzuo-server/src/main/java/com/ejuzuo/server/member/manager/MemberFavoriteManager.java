package com.ejuzuo.server.member.manager;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.vo.FavoriteVO;

import java.util.List;

public interface MemberFavoriteManager {

    ModelResult<MemberFavorite> save(MemberFavorite memberFavorite);

    ModelResult<DataPage<MemberFavorite>> queryByPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage);

    ModelResult<Boolean> deleteById(Integer id);

    ModelResult<Boolean> deleteByIds(List<Integer> ids, Integer memberId);

    ModelResult<Boolean> deleteByObjectIdAndType(Integer objectId, ObjectType objectType);

    ModelResult<Integer> count(MemberFavoriteOption option);

    ModelResult<List<MemberFavorite>> queryList(MemberFavoriteOption option);

    ModelResult<Integer> collect(MemberFavorite memberFavorite);

    ModelResult<List<FavoriteVO>> queryVOList(MemberFavoriteOption option);

    PageResult<FavoriteVO> queryVOPage(MemberFavoriteOption option, DataPage<MemberFavorite> dataPage);
}