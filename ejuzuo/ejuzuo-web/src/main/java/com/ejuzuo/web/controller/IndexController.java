package com.ejuzuo.web.controller;

import com.aicai.appmodel.page.DataPage;
import com.ejuzuo.common.constants.DigitalType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.NewsInfo;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.domain.type.DesignerType;
import com.ejuzuo.common.domain.type.DigitalShelfStatus;
import com.ejuzuo.common.domain.type.NewsInfoCategory;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.option.DesignerOption;
import com.ejuzuo.common.option.NewsInfoOption;
import com.ejuzuo.common.service.*;
import com.ejuzuo.common.vo.ActivityInfoVO;
import com.ejuzuo.common.vo.DigitalFurnitureVO;
import com.ejuzuo.common.vo.GoodsNavigationVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/3/26 0026.
 */

@Controller
@RequestMapping()
public class IndexController extends BaseController {


    @Resource(name = "brandServiceClient")
    private BrandService brandService;

    @Resource
    private DigitalFurnitureService digitalFurnitureService ;

    @Resource
    private ActivityInfoService activityInfoService ;

    @Resource
    private DesignerService designerService ;

    @Resource
    private NewsInfoService newsInfoService ;

    @Resource(name = "indexServiceClient")
    private IndexService indexService ;

    @RequestMapping(value = {"","/index"})
    public String index(Model model){

        NewsInfoOption newsInfoOption = new NewsInfoOption();
        newsInfoOption.setCategory(NewsInfoCategory.xinwenzixun.getIndex());
        newsInfoOption.setEditStatus(1);
        newsInfoOption.setStatus(1);
        DataPage<NewsInfo> dataPage = new DataPage<>(1,4);
        dataPage.setOrderBy("id");
        dataPage.setOrder("desc");
        dataPage.setNeedTotalCount(false);
        dataPage = newsInfoService.queryPage(dataPage , newsInfoOption).getPage();


        ActivityInfoOption activityInfoOption = new ActivityInfoOption();
        activityInfoOption.setStatus(1);
        activityInfoOption.setEditStatus(1);
        DataPage<ActivityInfoVO> activityInfoDataPage = new DataPage<>(1,3);
        activityInfoDataPage.setOrderBy("id");
        activityInfoDataPage.setOrder("desc");
        activityInfoDataPage = activityInfoService.queryVOPage(activityInfoDataPage, activityInfoOption).getPage();

        //品牌馆
        DigitalFurniture option = new DigitalFurniture();
        option.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
        option.setStatus(Status.STATUS.getIndex());
        option.setType(DigitalType.QUALITY_GOODS.getIndex());
        DataPage<DigitalFurniture> digitalFurnitureDataPage = new DataPage<>(1,12);

//        digitalFurnitureDataPage.setOrderBy("id");
//        digitalFurnitureDataPage.setOrder("desc");
//        digitalFurnitureDataPage.setOrderBy("update_time");
        digitalFurnitureDataPage.setOrderBy("shelf_time");
        digitalFurnitureDataPage.setOrder("desc");
        DataPage<DigitalFurnitureVO> digitalFurnitureDataPageVO = digitalFurnitureService.queryPage( digitalFurnitureDataPage , option).getPage();


        //定制馆
        option = new DigitalFurniture();
        option.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
        option.setStatus(Status.STATUS.getIndex());
        option.setType(DigitalType.SPECIAL_DO.getIndex());
        digitalFurnitureDataPage.setOrderBy("shelf_time");
        digitalFurnitureDataPage.setOrder("desc");

        DataPage<DigitalFurniture> digitalFurnitureDataPageSPECIALDO = new DataPage<>(1,8);
        DataPage<DigitalFurnitureVO> digitalFurnitureDataPageSPECIALDOVO = digitalFurnitureService.queryPage( digitalFurnitureDataPageSPECIALDO , option).getPage();



        //进口馆
        option = new DigitalFurniture();
        option.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
        option.setStatus(Status.STATUS.getIndex());
        option.setType(DigitalType.IMPORT_PAVILION.getIndex());
        digitalFurnitureDataPage.setOrderBy("shelf_time");
        digitalFurnitureDataPage.setOrder("desc");

        DataPage<DigitalFurniture> digitalFurnitureDataPageIMPORTPAVILION = new DataPage<>(1,8);
        DataPage<DigitalFurnitureVO> digitalFurnitureDataPageIMPORTPAVILIONVO = digitalFurnitureService.queryPage( digitalFurnitureDataPageSPECIALDO , option).getPage();



        //家居尾品汇
        option = new DigitalFurniture();
        option.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
        option.setStatus(Status.STATUS.getIndex());
        option.setType(DigitalType.END_PRODUCT.getIndex());
        digitalFurnitureDataPage.setOrderBy("shelf_time");
        digitalFurnitureDataPage.setOrder("desc");

        DataPage<DigitalFurniture> digitalFurnitureDataPageEndProduct = new DataPage<>(1,8);
        DataPage<DigitalFurnitureVO> digitalFurnitureDataPageEndProductVO = digitalFurnitureService.queryPage( digitalFurnitureDataPageEndProduct , option).getPage();


        DesignerOption personOption = new DesignerOption();
        List<Integer> personList = new ArrayList<Integer>();
        personList.add(DesignerType.personal.getIndex());
        personOption.setDesignerTypeList(personList);
        personOption.setStatus(DesignerStatus.shenhetongguo.getIndex());
        DataPage<Designer> designerDataPage = new DataPage<>(1, 24);
        designerDataPage.setNeedTotalCount(false);
        designerDataPage = designerService.queryHomeList(personOption, designerDataPage).getModel();
        model.addAttribute("personList", designerDataPage.getDataList());

        DesignerOption companyOption = new DesignerOption();
        List<Integer> companyList = new ArrayList<Integer>();
        companyList.add(DesignerType.shejigongsi.getIndex());
        companyList.add(DesignerType.zhuangxiugongsi.getIndex());
        companyOption.setDesignerTypeList(companyList);
        companyOption.setStatus(DesignerStatus.shenhetongguo.getIndex());
        designerDataPage = new DataPage<>(1, 24);
        designerDataPage.setNeedTotalCount(false);
        designerDataPage = designerService.queryHomeList(companyOption, designerDataPage).getModel();
        model.addAttribute("companyList", designerDataPage.getDataList());

        model.addAttribute("digital", digitalFurnitureDataPageVO.getDataList());
        model.addAttribute("digitalSpecial", digitalFurnitureDataPageSPECIALDOVO.getDataList());
        model.addAttribute("digitalImprove", digitalFurnitureDataPageIMPORTPAVILIONVO.getDataList());
        model.addAttribute("digitalEnd", digitalFurnitureDataPageEndProductVO.getDataList());
        model.addAttribute("activityInfo", activityInfoDataPage.getDataList());
        model.addAttribute("news", dataPage.getDataList());
        model.addAttribute("brand", brandService.queryAllNomal().getModel());
        return "/index";
    }


    @RequestMapping("/index/navigation")
    @ResponseBody
    public List<GoodsNavigationVO> navigation (){
        return indexService.queryAllGoods();
    }


    @RequestMapping("/aboutMe")
    public String aboutMe(){

        return "/aboutMe";
    }

}
