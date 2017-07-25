package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.domain.result.PageResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.MemberFavorite;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.service.MemberFavoriteService;
import com.ejuzuo.common.vo.FavoriteVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 * Created by allen on 2016/4/26.
 */

@Controller
@RequestMapping("favorite")
public class MemberFavoriteController extends BaseController {


    @Resource
    private MemberFavoriteService memberFavoriteService;


    @Login
    @ResponseBody
    @RequestMapping("/save/{objectType}/{workId}")
    public JSONObject saveFavorite(@PathVariable Integer workId, @PathVariable Integer objectType, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ObjectType tempObjectType = ObjectType.findByIndex(objectType);
        if (tempObjectType == null) {
            jsonObject.put("code", 444);
            jsonObject.put("code", "参数错误");
            return jsonObject;
        }

        MemberFavorite memberFavorite = new MemberFavorite();
        memberFavorite.setMemberId(getMemberId(request));
        memberFavorite.setObjectId(workId);
        memberFavorite.setObjectType(tempObjectType.getIndex());

        ModelResult<Integer> modelResult = memberFavoriteService.collect(memberFavorite);

        if (modelResult.isSuccess()) {
            jsonObject.put("code", 222);
            jsonObject.put("model", modelResult.getModel());
        } else {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
        }
        return jsonObject;
    }


    @Login
    @RequestMapping("deleteIds")
    @ResponseBody
    public JSONObject deleteIds(HttpServletRequest request, String ids) {
        JSONObject jsonObject = new JSONObject();

        List<Integer> idList = new ArrayList<>();
        for (String str : ids.split(",")) {
            idList.add(Integer.parseInt(str));
        }

        ModelResult<Boolean> modelResult = memberFavoriteService.deleteByIds(idList, getMemberId(request));
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }
        jsonObject.put("code", 222);
        jsonObject.put("msg", "成功删除");
        return jsonObject;
    }


    @Login
    @RequestMapping("list")
    public String queryList(HttpServletRequest request, Model model) {
        MemberFavoriteOption memberFavoriteOption = new MemberFavoriteOption();
        memberFavoriteOption.setMemberId(getMemberId(request));
        model.addAttribute("totalCount", memberFavoriteService.count(memberFavoriteOption).getModel());
        model.addAttribute("menu", "favorite");
        return "/favorite/favoriteList";
    }

    @Login
    @ResponseBody
    @RequestMapping("ajaxList")
    public JSONObject ajaxList(HttpServletRequest request, DataPage<MemberFavorite> dataPage) {
        MemberFavoriteOption memberFavoriteOption = new MemberFavoriteOption();
        memberFavoriteOption.setMemberId(getMemberId(request));
        dataPage.setNeedTotalCount(false);
        dataPage.setPageSize(6);
        PageResult<FavoriteVO> favoriteVOPageResult = memberFavoriteService.queryVOPage(memberFavoriteOption, dataPage);

        JSONObject jsonObject = new JSONObject();
        if (favoriteVOPageResult.isSuccess()) {
            jsonObject.put("data", favoriteVOPageResult.getPage().getDataList());
        }
        return jsonObject;
    }
}
