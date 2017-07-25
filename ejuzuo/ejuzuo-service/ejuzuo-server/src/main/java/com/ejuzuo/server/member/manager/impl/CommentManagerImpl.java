package com.ejuzuo.server.member.manager.impl;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Comment;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.Member;
import com.ejuzuo.common.domain.type.CommentMask;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.util.JSONUtils;
import com.ejuzuo.common.vo.CommentVO;
import com.ejuzuo.server.designer.manager.DesignerManager;
import com.ejuzuo.server.member.manager.CommentManager;
import com.ejuzuo.server.member.manager.MemberManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by tianlun.wu on 2016/4/6 0006.
 */
@Component
public class CommentManagerImpl implements CommentManager {

    private static final Logger logger = LoggerFactory.getLogger(CommentManagerImpl.class);
    
    @Resource(name = "dao")
    protected GenericDao dao;

    @Resource
    private MemberManager memberManager;

    @Resource
    private DesignerManager designerManager;

    @Override
    public ModelResult<Comment> save(Comment comment) {
        comment.setStatus(Status.STATUS.getIndex());
        comment.setMasked(CommentMask.un_pingbi.getIndex());
        comment.setUpdateTime(Calendar.getInstance());
        comment.setCreateTime(Calendar.getInstance());
        dao.insertAndReturnAffectedCount("CommentMapper.save", comment);
        return new ModelResult<>(comment);
    }

    @Override
    public ModelResult<Comment> queryById(Integer id) {
        return new ModelResult<>(dao.queryUnique("CommentMapper.selectById", id.longValue()));
    }

    @Override
    public ModelResult<List<Comment>> queryList(CommentOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        return new ModelResult<>(dao.queryList("CommentMapper.selectByOption", param));
    }

    @Override
    public ModelResult<List<CommentVO>> queryVOList(CommentOption option) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        List<Comment> list = dao.queryList("CommentMapper.selectByOption", param);

        List<CommentVO> listVO = new ArrayList<>();
        CommentVO vo = null;
        Member member = null;
        Designer designer = null;
        for (Comment comment : list) {
            vo = new CommentVO();
            vo.setId(comment.getId());
            vo.setMemberId(comment.getMemberId());
            member = memberManager.queryByMemberId(comment.getMemberId()).getModel();
            vo.setNickName(member.getNickName());
            if (!StringUtils.isBlank(member.getProfileImg())){
                vo.setProfileImg(JSONObject.parseObject(member.getProfileImg()).getString("pic4343"));
            }

            vo.setMasked(comment.getMasked());
            if (comment.getMasked() == CommentMask.pingbi.getIndex()) {
                vo.setContent("内容被屏蔽");
            } else{
                vo.setContent(comment.getComment());
            }
            designer = designerManager.queryByMemberId(comment.getMemberId()).getModel();
            if (designer != null && designer.getStatus() == DesignerStatus.shenhetongguo.getIndex()) {
                vo.setDesigner(true);
            } else {
                vo.setDesigner(false);
            }
            vo.setDateBefore(DateUtil.getCommentShowTime(comment.getUpdateTime()));
            listVO.add(vo);
        }

        return new ModelResult<>(listVO);
    }

    @Override
    public ModelResult<DataPage<Comment>> queryByPage(CommentOption option, DataPage<Comment> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        Calendar beginDate = option.getBeginDate();
		Calendar endDate = option.getEndDate();
		if (beginDate != null) {
			beginDate.set(Calendar.HOUR_OF_DAY, 0);
			beginDate.set(Calendar.MINUTE, 0);
			beginDate.set(Calendar.SECOND, 0);
			beginDate.set(Calendar.MILLISECOND, 0);
			param.put("beginDate", beginDate);
		}
		if (endDate != null) {
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			param.put("endDate", endDate);
		}
        return new ModelResult<>(dao.queryPage("CommentMapper.queryPageCount", "CommentMapper.queryPage", param, dataPage));
    }

    @Override
    public ModelResult<DataPage<CommentVO>> queryVOByPage(CommentOption option, DataPage<CommentVO> dataPage) {
        Map<String, Object> param = JSONUtils.object2MapSpecail(option);
        DataPage<Comment> commentDataPage = new DataPage<>(dataPage.getPageNo(), dataPage.getPageSize());

        commentDataPage = dao.queryPage("CommentMapper.queryPageCount", "CommentMapper.queryPage", param, commentDataPage);

        List<CommentVO> listVO = new ArrayList<>();

        CommentVO vo = null;
        Member member = null;
        Designer designer = null;
        for (Comment comment : commentDataPage.getDataList()) {
            vo = new CommentVO();
            vo.setMemberId(comment.getMemberId());
            vo.setContent(comment.getComment());
            member = memberManager.queryByMemberId(comment.getMemberId()).getModel();
            vo.setNickName(member.getNickName());
            if (!StringUtils.isBlank(member.getProfileImg())){
                vo.setProfileImg(JSONObject.parseObject(member.getProfileImg()).getString("pic4343"));
            }
            vo.setMasked(comment.getMasked());
            if (comment.getMasked() == CommentMask.pingbi.getIndex()) {
                vo.setContent("内容被屏蔽");
            } else{
                vo.setContent(comment.getComment());
            }
            designer = designerManager.queryByMemberId(comment.getMemberId()).getModel();
            if (designer != null && designer.getStatus() == DesignerStatus.shenhetongguo.getIndex()) {
                vo.setDesigner(true);
            } else {
                vo.setDesigner(false);
            }
            vo.setDateBefore(DateUtil.getCommentShowTime(comment.getUpdateTime()));
            listVO.add(vo);
        }

        dataPage.setDataList(listVO);
        dataPage.setTotalCount(commentDataPage.getTotalCount());

        return new ModelResult<>(dataPage);
    }

    @Override
    public ModelResult<Integer> count(CommentOption option) {
        return new ModelResult<>(dao.queryCount("CommentMapper.queryPageCount", JSONUtils.object2MapSpecail(option)));
    }

	@Override
	public ModelResult<Boolean> update(Integer id, Integer masked, Integer status) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			if (masked != null) {
				map.put("masked", masked);
			}
			if (status != null) {
				map.put("status", status);
			}
			dao.update("CommentMapper.update", map);
        } catch (Exception e) {
            logger.error("CommentMapper.update 更新报错", e);
            return new ModelResult<>().withError("exception", "更新报错");
        }
        return new ModelResult<>(Boolean.TRUE);
	}
}
