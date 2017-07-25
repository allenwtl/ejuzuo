package com.ejuzuo.server.member.service.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.Comment;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.service.CommentService;
import com.ejuzuo.common.vo.CommentVO;
import com.ejuzuo.server.keywork.manager.FilterKeywordMananger;
import com.ejuzuo.server.member.manager.CommentManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by tianlun.wu on 2016/4/24 0024.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentManager commentManager ;

    @Resource
    private FilterKeywordMananger filterKeywordMananger ;

    @Override
    public ModelResult<Comment> save(Comment comment) {
        Set<String> result = filterKeywordMananger.findBannedKeyword(comment.getComment());
        if(result == null || result.size() == 0){
            return commentManager.save(comment);
        }
        return new ModelResult<>().withError("exception","评论中包涵不当内容");
    }

    @Override
    public ModelResult<List<Comment>> queryList(CommentOption option) {
        return commentManager.queryList(option);
    }

    @Override
    public ModelResult<List<CommentVO>> queryVOList(CommentOption option) {
        return commentManager.queryVOList(option);
    }

    @Override
    public ModelResult<DataPage<CommentVO>> queryVOPage(CommentOption option, DataPage<CommentVO> dataPage) {
        return commentManager.queryVOByPage(option, dataPage);
    }

    @Override
    public ModelResult<DataPage<Comment>> queryPage(CommentOption option, DataPage<Comment> dataPage) {
        return commentManager.queryByPage(option, dataPage);
    }

    @Override
    public ModelResult<Integer> count(CommentOption option) {
        return commentManager.count(option);
    }

	@Override
	public ModelResult<Boolean> update(Integer id, Integer masked, Integer status) {
		return commentManager.update(id,masked,status);
	}
}
