package com.ejuzuo.web.controller.newsinfo;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.NewsInfoType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;
import com.ejuzuo.common.domain.type.NewsInfoCategory;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.service.ContentInfoService;
import com.ejuzuo.common.service.NewsInfoService;
import com.ejuzuo.common.vo.NewsInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/3 0003.
 */

@Controller
@RequestMapping("/newsInfo")
public class NewsInfoController {

    @Resource
    private NewsInfoService newsInfoService;

    @Resource
    private ContentInfoService contentInfoService ;


    @RequestMapping("/index")
    public String index(Model model) {
        NewsInfoOption qVo = new NewsInfoOption();
        qVo.setCategory(NewsInfoCategory.xinwenzixun.getIndex());
        qVo.setStatus(Status.STATUS.getIndex());
        qVo.setEditStatus(1);

        int totalCount = newsInfoService.count(qVo).getModel();
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("type", 0);
        return "/newsinfo/newsInfoList";
    }



    @RequestMapping("/list/{type}")
    public String list(@PathVariable Integer type, Model model){
        NewsInfoOption qVo = new NewsInfoOption();
        qVo.setCategory(NewsInfoCategory.xinwenzixun.getIndex());
        qVo.setStatus(Status.STATUS.getIndex());
        qVo.setEditStatus(1);

        int totalCount = newsInfoService.count(qVo).getModel();
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("type", type);
        return "/newsinfo/newsInfoList";
    }

    @RequestMapping("/ajaxList/{type}")
    @ResponseBody
    public List<NewsInfoVO> ajaxList(@PathVariable Integer type, DataPage<NewsInfoVO> dataPage) {

        dataPage.setNeedTotalCount(false);
        dataPage.setPageSize(8);
        NewsInfoType newsInfoType = NewsInfoType.findByIndex(type);
        if (type == 0) {

        } else if (newsInfoType == null) {
            dataPage.setOrder("desc");
            dataPage.setOrderBy(" create_time ");
        } else if (newsInfoType.getIndex() == NewsInfoType.latest.getIndex()) {
            dataPage.setOrder("desc");
            dataPage.setOrderBy(" create_time ");
        } else if (newsInfoType.getIndex() == NewsInfoType.hotest.getIndex()) {
            dataPage.setOrder("desc");
            dataPage.setOrderBy(" view_count ");
        }

        NewsInfoOption qVo = new NewsInfoOption();
        qVo.setCategory(NewsInfoCategory.xinwenzixun.getIndex());
        qVo.setStatus(Status.STATUS.getIndex());
        qVo.setEditStatus(1);
        dataPage = newsInfoService.queryVOPage(dataPage, qVo).getPage();
        return dataPage.getDataList();
    }



    @RequestMapping("/detail/{newsId}")
    public String detail(@PathVariable Integer newsId, Model model){
        ContentInfo contentInfo = contentInfoService.query(ContentInfoRelatedType.NEWS, newsId).getModel();
        NewsInfo newsInfo = newsInfoService.queryById(newsId).getModel();
        model.addAttribute("data", contentInfo);
        model.addAttribute("newsId", newsId);
        model.addAttribute("newsInfo", newsInfo);


        if(newsInfo.getCategory() == NewsInfoCategory.wangzhanxinxi.getIndex()){
            return "/newsinfo/wangzhanDetail";
        }

        NewsInfoOption newsInfoOption = new NewsInfoOption();
        newsInfoOption.setCategory(NewsInfoCategory.xinwenzixun.getIndex());
        newsInfoOption.setEditStatus(EditStatus.PUBLISHED.getIndex());
        newsInfoOption.setStatus(Status.STATUS.getIndex());
        newsInfoOption.setId((long)newsId);
        model.addAttribute("newsInfoList", newsInfoService.queryList(newsInfoOption, 5).getModel());

        return "/newsinfo/newsInfoDetail";
    }


    @RequestMapping("/viewCount/{newsId}")
    public String viewCount(@PathVariable Integer newsId){
        newsInfoService.updateViewCount(newsId); 
        return "forward:/common/pic1.gif";
    }

}
