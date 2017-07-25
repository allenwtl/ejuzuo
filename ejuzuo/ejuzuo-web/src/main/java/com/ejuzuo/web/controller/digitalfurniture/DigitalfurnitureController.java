package com.ejuzuo.web.controller.digitalfurniture;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.client.service.DigitalFurnitureServiceClient;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.DigitalType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Brand;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.domain.DigitalFurniture;
import com.ejuzuo.common.domain.FileInfo;
import com.ejuzuo.common.domain.MemberPoint;
import com.ejuzuo.common.domain.type.DigitalShelfStatus;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.CommentOption;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.service.BrandService;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.service.CommentService;
import com.ejuzuo.common.service.FileInfoService;
import com.ejuzuo.common.service.MemberFavoriteService;
import com.ejuzuo.common.service.MemberPointService;
import com.ejuzuo.common.vo.DigitalFurnitureVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.ResultTypeEnum;

/**
 * Created by allen on 2016/4/23.
 */

@Controller
@RequestMapping("/digital")
public class DigitalfurnitureController extends BaseController {

    @Resource(name = "digitalFurnitureServiceClient")
    private DigitalFurnitureServiceClient digitalFurnitureService;

    @Resource
    private MemberFavoriteService memberFavoriteService;

    @Resource
    private MemberPointService memberPointService ;

    @Resource
    private BrandService brandService;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private CodeValueService codeValueService;

    @Resource
    private CommentService commentService;


/*    @RequestMapping("/index")
    public String digitalIndex(DataPage<DigitalFurniture> dataPage, Model model) {

        List<CodeValue> codeValueList = codeValueService.queryList(CollectionCode.kongjian, null, null).getModel();
        dataPage.setOrderBy("id");
        dataPage.setOrder("desc");
        for (CodeValue codeValue : codeValueList) {
            DigitalFurniture digitalFurniture = new DigitalFurniture();
            digitalFurniture.setSpaceCategory(codeValue.getValueCode());
            digitalFurniture.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
            digitalFurniture.setStatus(Status.STATUS.getIndex());

            dataPage = digitalFurnitureService.queryPageUinon(dataPage, digitalFurniture).getPage();
            List<CodeValue> kongjianxiaolei = codeValueService.queryList(CollectionCode.kongjianxiaolei, null, codeValue.getValueCode()).getModel();
            model.addAttribute(convert(codeValue.getValueName()) + "kjxl", kongjianxiaolei);
            model.addAttribute(convert(codeValue.getValueName()), dataPage.getDataList());
        }

        return "/digitalfurniture/digitalfurnitureindex";
    }*/
    //家具正品
    @RequestMapping("/index/{id}")
    public String digitalIndex(Model model, @PathVariable Integer id) {
        List<CodeValue> codeValueList = codeValueService.queryList(CollectionCode.kongjian, null, null).getModel();
        List<DigitalFurnitureVO> digitalFurnitureList = null;
        DigitalType digitalType = DigitalType.fromIndex(id);
        if(digitalType == null){
            return "";
        }
        for (CodeValue codeValue : codeValueList) {
            DigitalFurniture digitalFurniture = new DigitalFurniture();
            digitalFurniture.setSpaceCategory(codeValue.getValueCode());
            digitalFurniture.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
            digitalFurniture.setStatus(Status.STATUS.getIndex());
            digitalFurniture.setType(digitalType.getIndex());

            digitalFurnitureList = digitalFurnitureService.queryLastTimeAndPayBrand(digitalFurniture).getModel();
            List<CodeValue> kongjianxiaolei = codeValueService.queryList(CollectionCode.kongjianxiaolei, null, codeValue.getValueCode()).getModel();
            model.addAttribute(convert(codeValue.getValueName()) + "kjxl", kongjianxiaolei);
            model.addAttribute(convert(codeValue.getValueName()), digitalFurnitureList);
        }
        model.addAttribute("title", digitalType.getDescription());
        model.addAttribute("digitalType", digitalType.getIndex());
        return "/digitalfurniture/digitalfurnitureindex";
    }

//    //家具尾品
//    @RequestMapping("/index/end")
//    public String digitalIndexEnd(Model model) {
//        List<CodeValue> codeValueList = codeValueService.queryList(CollectionCode.kongjian, null, null).getModel();
//        List<DigitalFurnitureVO> digitalFurnitureList = null;
//        for (CodeValue codeValue : codeValueList) {
//            DigitalFurniture digitalFurniture = new DigitalFurniture();
//            digitalFurniture.setSpaceCategory(codeValue.getValueCode());
//            digitalFurniture.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
//            digitalFurniture.setStatus(Status.STATUS.getIndex());
//            digitalFurniture.setType(DigitalType.END_PRODUCT.getIndex());
//
//            digitalFurnitureList = digitalFurnitureService.queryLastTimeAndPayBrand(digitalFurniture).getModel();
//            List<CodeValue> kongjianxiaolei = codeValueService.queryList(CollectionCode.kongjianxiaolei, null, codeValue.getValueCode()).getModel();
//            model.addAttribute(convert(codeValue.getValueName()) + "kjxl", kongjianxiaolei);
//            model.addAttribute(convert(codeValue.getValueName()), digitalFurnitureList);
//        }
//        model.addAttribute("title", DigitalType.END_PRODUCT.getIndex());
//        return "/digitalfurniture/digitalfurnitureindex";
//    }


    @RequestMapping("/digitalList/{bigSpace}/{smallSpace}/{style}/{brand}/{digitalType}")
    public String digitalList(@PathVariable String bigSpace, @PathVariable String smallSpace,
                              @PathVariable String style, @PathVariable Integer brand,@PathVariable Integer digitalType,  Model model) {

        List<CodeValue> bigSpaceList = codeValueService.queryList(CollectionCode.kongjian, null, null).getModel();

        List<CodeValue> smallSpaceList = null;

        if (Integer.parseInt(bigSpace) != 0) {
            smallSpaceList = codeValueService.queryList(CollectionCode.kongjianxiaolei, null, bigSpace).getModel();
        } else if (Integer.parseInt(smallSpace) != 0) {
            bigSpace = codeValueService.queryOne(CollectionCode.kongjianxiaolei, smallSpace, null).getModel().getParentCode();
            smallSpaceList = codeValueService.queryList(CollectionCode.kongjianxiaolei, null, bigSpace).getModel();
        }

        List<CodeValue> styleList = codeValueService.queryList(CollectionCode.fengge, null, null).getModel();
        List<Brand> brandList = brandService.queryAllNomal().getModel();

        model.addAttribute("bigSpace", bigSpace);
        model.addAttribute("smallSpace", smallSpace);
        model.addAttribute("style", style);
        model.addAttribute("brand", brand);
        model.addAttribute("digitalType", digitalType);

        model.addAttribute("title", DigitalType.fromIndex(digitalType).getDescription());

        if (Integer.parseInt(bigSpace) == 0) {
            bigSpace = null;
            model.addAttribute("smallSpace", 0);
            smallSpace = String.valueOf(0);
        }
        if (Integer.parseInt(smallSpace) == 0) {
            smallSpace = null;
        }
        if (Integer.parseInt(style) == 0) {
            style = null;
        }
        if (brand == 0) {
            brand = null;
        }
        if (digitalType == 0) {
            digitalType = null;
        }

        DigitalFurniture digitalFurniture = new DigitalFurniture();
        digitalFurniture.setSpaceCategory(bigSpace);
        digitalFurniture.setSpaceSmallCategory(smallSpace);
        digitalFurniture.setStyle(style);
        digitalFurniture.setBrand(brand);
        digitalFurniture.setStatus(Status.STATUS.getIndex());
        digitalFurniture.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
        digitalFurniture.setType(digitalType);

        Integer totalCount = digitalFurnitureService.count(digitalFurniture).getModel();
        model.addAttribute("totalCount", totalCount);

        model.addAttribute("bigSpaceList", bigSpaceList);
        model.addAttribute("smallSpaceList", smallSpaceList);
        model.addAttribute("styleList", styleList);
        model.addAttribute("brandList", brandList);
        model.addAttribute("digitalTypeList", DigitalType.getAll());

        return "/digitalfurniture/digitalfurniturelist";
    }


    @RequestMapping("/digitalAjaxList/{bigSpace}/{smallSpace}/{style}/{brand}/{digitalType}")
    @ResponseBody
    public JSONObject digitaAjaxlList(@PathVariable String bigSpace, @PathVariable String smallSpace,
                                      @PathVariable String style, @PathVariable Integer brand,@PathVariable Integer digitalType,
                                      DataPage<DigitalFurniture> dataPage) {

        JSONObject jsonObject = new JSONObject();

        if (Integer.parseInt(bigSpace) == 0) {
            bigSpace = null;
        }
        if (Integer.parseInt(smallSpace) == 0) {
            smallSpace = null;
        }
        if (Integer.parseInt(style) == 0) {
            style = null;
        }
        if (brand == 0) {
            brand = null;
        }
        if (digitalType == 0){
            digitalType = null ;
        }

        DigitalFurniture digitalFurniture = new DigitalFurniture();
        digitalFurniture.setSpaceCategory(bigSpace);
        digitalFurniture.setSpaceSmallCategory(smallSpace);
        digitalFurniture.setStyle(style);
        digitalFurniture.setBrand(brand);
        digitalFurniture.setStatus(Status.STATUS.getIndex());
        digitalFurniture.setShelfStatus(DigitalShelfStatus.SALES.getIndex());
        digitalFurniture.setType(digitalType);

        //按上架时间倒序显示
        dataPage.setOrderBy("shelf_time");
        dataPage.setOrder("desc");
        List<DigitalFurnitureVO> datalist = digitalFurnitureService.queryPage(dataPage, digitalFurniture).getPage().getDataList();
        jsonObject.put("data", datalist);
        return jsonObject;
    }


    @RequestMapping("/digitalDetail/{id}")
    public String digitalDetail(@PathVariable Integer id, Model model, HttpServletRequest request) {

        ModelResult<DigitalFurniture> modelResult = digitalFurnitureService.queryById(id);
        if (!modelResult.isSuccess()) {
            logger.info("根据id:[{}]查询数字家居报错", modelResult.getErrorMsg());
        }

        DigitalFurniture digitalFurniture = modelResult.getModel();

        DigitalFurnitureVO vo = new DigitalFurnitureVO();

        try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, digitalFurniture);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
        

        Integer memberId = getMemberId(request);
        if (memberId != null) {
            MemberFavoriteOption memberFavoriteOption = new MemberFavoriteOption();
            memberFavoriteOption.setMemberId(memberId);
            memberFavoriteOption.setObjectType(ObjectType.shuzijiaju.getIndex());
            memberFavoriteOption.setObjectId(id);
            int size = memberFavoriteService.count(memberFavoriteOption).getModel();
            if(size>0){
                vo.setFavorite(true);
            }
        }

        FileInfo fileInfo = fileInfoService.queryById(vo.getFileId()).getModel();
        if (fileInfo == null) {
            logger.info("fileInfo_id:[{}] 找不到", vo.getFileId());
            vo.setSizeMB("0 M");
        } else {
            vo.setSizeMB(convertSize(fileInfo.getSize()));
        }

        MemberFavoriteOption option = new MemberFavoriteOption();
        option.setObjectId(vo.getId());
        option.setObjectType(ObjectType.shuzijiaju.getIndex());
        vo.setFavoriteCount(memberFavoriteService.count(option).getModel());

        //格式化域名显示。
//        postHandleFormat(vo);
        model.addAttribute("data", vo);

        CommentOption commentOption = new CommentOption();
        commentOption.setObjectId(vo.getId());
        commentOption.setObjectType(ObjectType.shuzijiaju.getIndex());
        commentOption.setStatus(Status.STATUS.getIndex());
        model.addAttribute("totalCount", commentService.count(commentOption).getModel());
        // 配套商品
        model.addAttribute("matchGood", digitalFurnitureService.queryLatestListByBrand(vo.getBrand(), 4, vo.getId(),vo.getSpaceCategory(),vo.getStyle(),vo.getType()).getModel());
        // 配套饰品
        model.addAttribute("matchAccessory", digitalFurnitureService.queryLatestDecoration(vo.getBrand(), 4,vo.getSpaceCategory(),vo.getStyle(),vo.getType()).getModel());
        model.addAttribute("sameBrand", digitalFurnitureService.queryLatestListByBrand(vo.getBrand(), 16, vo.getId(),null,null,vo.getType()).getModel());
        model.addAttribute("otherBrand", digitalFurnitureService.queryLatestListWithoutBrand(vo.getBrand(), 3,null,null,vo.getType()).getModel());
        model.addAttribute("title", DigitalType.fromIndex(vo.getType()).getDescription());
        return "/digitalfurniture/digitalDetail";
    }

    
    private void postHandleFormat(DigitalFurnitureVO vo) {    	
    	String corporation = vo.getCorporation();
    	JSONObject corpJson = JSONObject.parseObject(corporation);    	 	
   	 	
    	String corp = corpJson.getString("公司网站");
   	 	if (StringUtils.isNotEmpty(corp)) {
   	 		corpJson.put("公司网站", corp.replaceAll("(?is)((http:|https:)//[/\\.\\w_\\-]+)","<a href='$1' target='_blank'>$1</a>")); 
   	 	}
    	corp = corpJson.getString("公司网址");
   	 	if (StringUtils.isNotEmpty(corp)) {
   	 		corpJson.put("公司网址", corp.replaceAll("(?is)((http:|https:)//[/\\.\\w_\\-]+)","<a href='$1' target='_blank'>$1</a>")); 
   	 	}
    	corp = corpJson.getString("官网");
   	 	if (StringUtils.isNotEmpty(corp)) {
   	 		corpJson.put("官网", corp.replaceAll("(?is)((http:|https:)//[/\\.\\w_\\-]+)","<a href='$1' target='_blank'>$1</a>")); 
   	 	}   	 	
    	String content = corpJson.toJSONString();
    	vo.setCorporation(content);
    }
    
    
    @RequestMapping("/viewCount/{id}")
    public String viewCount(@PathVariable Integer id) {
        ModelResult<DigitalFurniture> modelResult = digitalFurnitureService.queryById(id);
        if (!modelResult.isSuccess()) {
            logger.info("根据id:[{}]查询数字家居报错", modelResult.getErrorMsg());
            return "forward:/common/pic1.gif";
        }

        DigitalFurniture digitalFurniture = modelResult.getModel();

        DigitalFurniture tempDigital = new DigitalFurniture();
        tempDigital.setId(digitalFurniture.getId());
        tempDigital.setViewCount(digitalFurniture.getViewCount() + 1);
        digitalFurnitureService.update(tempDigital);
        return "forward:/common/pic1.gif";
    }

    @Login(ResultTypeEnum.json)
    @RequestMapping("/available/{digitalId}")
    @ResponseBody
    public Map<String, Object> available(@PathVariable Integer digitalId, HttpServletRequest request){

        DigitalFurniture digitalFurniture = digitalFurnitureService.queryById(digitalId).getModel();

        MemberPoint memberPoint = memberPointService.selectByMemberId(getMemberId(request)).getModel();

        Map<String , Object> resultMap = new HashMap<>();
        resultMap.put("price", digitalFurniture.getPointPrice());
        resultMap.put("balance", memberPoint.getBalance());
        resultMap.put("buy", false);
        if(digitalFurniture.getPointPrice() <= memberPoint.getBalance()){
            resultMap.put("buy", true);
        }

        return resultMap;
    }

    private String convertSize(long size){
        if (size > 1024*1024 ){
            if(size > 1024*1024*1024 ){
                return  new BigDecimal(size).divide(new BigDecimal(1024*1024*1024)).setScale(2, RoundingMode.HALF_UP).toString()+" G";
            }
            return new BigDecimal(size).divide(new BigDecimal(1024*1024)).setScale(2, RoundingMode.HALF_UP).toString()+" M";
        }
        return new BigDecimal(size).divide(new BigDecimal(1024)).setScale(2, RoundingMode.HALF_UP).toString()+" K";
    }

    private String convert(String valueName) {
        if (valueName.contains("客厅")) {
            return "keting";
        }
        if (valueName.contains("餐厅")) {
            return "canting";
        }
        if (valueName.contains("卧室")) {
            return "woshi";
        }
        if (valueName.contains("书房")) {
            return "shufang";
        }
        if (valueName.contains("儿童")) {
            return "children";
        }
        if (valueName.contains("灯饰")) {
            return "dengshi";
        }
        if (valueName.contains("厨卫")) {
            return "chuwei";
        }
        if (valueName.contains("固装")) {
            return "guzhuang";
        }
        if (valueName.contains("酒店")) {
            return "jiudian";
        }
        if (valueName.contains("办公")) {
            return "bangong";
        }
        if (valueName.contains("门厅")) {
            return "menting";
        }
        if (valueName.contains("饰品")) {
            return "shipin";
        }
        if (valueName.contains("其它")) {
            return "qita";
        }
        logger.info("valueName:{}", valueName);
        return null;
    }
}
