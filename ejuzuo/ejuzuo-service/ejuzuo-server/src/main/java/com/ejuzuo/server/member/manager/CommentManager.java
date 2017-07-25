package com.ejuzuo.server.member.manager;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Comment;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.vo.CommentVO;

import java.util.List;

public interface CommentManager {

    ModelResult<Comment> save(Comment comment);

    ModelResult<Comment> queryById(Integer id);

    ModelResult<List<Comment>> queryList(CommentOption option);

    ModelResult<List<CommentVO>> queryVOList(CommentOption option);

    ModelResult<DataPage<Comment>> queryByPage(CommentOption option, DataPage<Comment> dataPage);

    ModelResult<DataPage<CommentVO>> queryVOByPage(CommentOption option, DataPage<CommentVO> dataPage);

    ModelResult<Integer> count(CommentOption option);

	ModelResult<Boolean> update(Integer id, Integer masked, Integer status);

}