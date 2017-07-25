package com.ejuzuo.web.controller.search;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.service.ElasticSearchService;
import com.ejuzuo.common.vo.ContentSearchResult;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/12 0012.
 */

@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping("/list")
    public String queryResult(String keyword, @RequestParam(defaultValue = "111")Integer objectType, Model model) {

        DataPage<ContentSearchResult> dataPage = new DataPage<>(1, 10);
        dataPage.setNeedData(false);
        model.addAttribute("type", objectType);

        if(objectType == 111){
            objectType = null ;
            model.addAttribute("content", "全部");
        } else {
            model.addAttribute("content", ObjectType.findByIndex(objectType).getDescription());
        }
        ModelResult<DataPage<ContentSearchResult>> modelResult = elasticSearchService.searchContentByKeyword(keyword, objectType, null, dataPage);
        model.addAttribute("totalCount", modelResult.getModel().getTotalCount());
        model.addAttribute("keyword", keyword);
        return "/search/searchList";
    }


    @RequestMapping("/ajaxList")
    @ResponseBody
    public List<ContentSearchResult> ajaxList(String keyword, Integer objectType, DataPage<ContentSearchResult> dataPage) {
        dataPage.setNeedTotalCount(false);
        dataPage.setPageSize(8);
        ModelResult<DataPage<ContentSearchResult>> modelResult = elasticSearchService.searchContentByKeyword(keyword, objectType, null, dataPage);
        if (modelResult.isSuccess()) {
            return modelResult.getModel().getDataList();
        }
        return new ArrayList<ContentSearchResult>();
    }

}
