package com.ejuzuo.common.service;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Comment;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.vo.CommentVO;

import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/24 0024.
 */
public interface CommentService {

    ModelResult<Comment> save(Comment comment);

    ModelResult<List<Comment>> queryList(CommentOption option);

    ModelResult<List<CommentVO>> queryVOList(CommentOption option);

    ModelResult<DataPage<CommentVO>> queryVOPage(CommentOption option, DataPage<CommentVO> dataPage);

    ModelResult<DataPage<Comment>> queryPage(CommentOption option, DataPage<Comment> dataPage);

    ModelResult<Integer> count(CommentOption option);

	ModelResult<Boolean> update(Integer id, Integer masked, Integer status);

}
