package com.ejuzuo.web.controller.comment;


import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Comment;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.service.CommentService;
import com.ejuzuo.common.vo.CommentVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.ResultTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/4/24 0024.
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    private CommentService commentService;


    @RequestMapping("/list/{objectId}/{objectType}")
    @ResponseBody
    public JSONObject listComment(@PathVariable Integer objectId, @PathVariable Integer objectType ,DataPage<CommentVO> dataPage){
        CommentOption option = new CommentOption();
        option.setObjectId(objectId);
        option.setObjectType(objectType);
        option.setStatus(Status.STATUS.getIndex());
        option.setStartIndex((dataPage.getPageNo()-1)*dataPage.getPageSize());
        option.setPageSize(dataPage.getPageSize());
        List<CommentVO> commentVOLit = commentService.queryVOList(option).getModel();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", commentVOLit);
        return jsonObject;
    }


    @Login(ResultTypeEnum.json)
    @ResponseBody
    @RequestMapping("/addComment/{objectId}/{objectType}")
    public JSONObject ajaxAddComment(String content, @PathVariable Integer objectId, @PathVariable Integer objectType, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Comment comment = new Comment();
        comment.setComment(content);
        comment.setObjectId(objectId);
        comment.setObjectType(objectType);
        comment.setMemberId(getMemberId(request));
        comment.setUpdator(getAccount(request));
        ModelResult<Comment> modelResult = commentService.save(comment);
        if(!modelResult.isSuccess()){
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
        } else {
            jsonObject.put("code", 222);
            jsonObject.put("msg", "添加成功");
        }

        return jsonObject;
    }

}
