package com.ejuzuo.web.controller.member;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.Area;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.domain.MemberCare;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.domain.type.DesignerType;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.service.AreaService;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.service.DesignerService;
import com.ejuzuo.common.service.DesignerWorkService;
import com.ejuzuo.common.service.MemberCareService;
import com.ejuzuo.common.vo.DesignerVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.vo.MemberVo;

/**
 * 普通会员申请为个人设计师，设计公司，装修公司的业务逻辑
 * Created by allen on 2016/4/3.
 */

@Controller
@RequestMapping("designer")
public class DesignerController extends BaseController {

    @Resource
    private DesignerService designerService;

    @Resource
    private DesignerWorkService designerWorkService;

    @Resource
    private AreaService areaService;

    @Resource
    private CodeValueService codeValueService;


    @Resource
    private MemberCareService memberCareService ;

    private static final String province = "province";

    @Login
    @RequestMapping("/setting")
    public String setting(Model model, HttpServletRequest request) {
        model.addAttribute("menu", "designer");
        Designer designer = designerService.queryByMemberId(getMemberId(request)).getModel();
        if (designer != null) {
            model.addAttribute("data", designer);
            model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
            model.addAttribute("areaList", queryAreaList());
            model.addAttribute("city", areaService.queryAll(designer.getProvice()).getModel());
            if (designer.getDesignerType() == DesignerType.personal.getIndex()) {
                model.addAttribute("zhiye", codeValueService.queryList(CollectionCode.zhiyeshenfen, null, null).getModel());
                return "/designer/updatePersonInfoNew";
            } else if (designer.getDesignerType() == DesignerType.shejigongsi.getIndex() || designer.getDesignerType() == DesignerType.zhuangxiugongsi.getIndex()) {
                model.addAttribute("guimo", codeValueService.queryList(CollectionCode.gongsiguimo, null, null).getModel());
                return "/designer/updateCompanyInfoNew";
            }
        }
        return "/designer/setting";
    }

    @Login
    @RequestMapping("/apply/{type}")
    public String apply(@PathVariable String type, Model model) {
        model.addAttribute("menu", "designer");

        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        model.addAttribute("areaList", queryAreaList());
        if (type.equals("person")) {
            model.addAttribute("zhiye", codeValueService.queryList(CollectionCode.zhiyeshenfen, null, null).getModel());
            return "/designer/applyDesigner";
        }
        model.addAttribute("guimo", codeValueService.queryList(CollectionCode.gongsiguimo, null, null).getModel());
        return "/designer/applyCompany";
    }

    //申请设计师
    @Login
    @RequestMapping("/applyDesigner")
    public String applyDesigner(Model model, HttpServletRequest request) {
        model.addAttribute("menu", "designer");

        Designer designer = designerService.queryByMemberId(getMemberId(request)).getModel();
        if (designer != null) {
            model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
            model.addAttribute("zhiye", codeValueService.queryList(CollectionCode.zhiyeshenfen, null, null).getModel());
            model.addAttribute("model", designer);
            model.addAttribute("city", areaService.queryAll(designer.getProvice()).getModel());
            model.addAttribute("areaList", queryAreaList());
            logger.info("已经填写过了, 进入修改页面");
            if (designer.getDesignerType() == DesignerType.personal.getIndex()) {
                return "/designer/updatePersonInfo";
            }
            return "/designer/updateCompanyInfo";
        }

        model.addAttribute("zhiye", codeValueService.queryList(CollectionCode.zhiyeshenfen, null, null).getModel());
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        model.addAttribute("areaList", queryAreaList());

        logger.info("个人信息设置页面");
        return "/designer/applyDesigner";
    }

    //保存申请信息
    @Login
    @RequestMapping("/save")
    @ResponseBody
    public JSONObject savePersonal(HttpServletRequest request, Designer designer) {
        JSONObject jsonObject = new JSONObject();
        try {
            Designer designerDB = designerService.queryByMemberId(getMemberId(request)).getModel();
            if(designerDB == null){
                logger.info("保存个人设计师的信息");
                designer.setMemberId(getMemberId(request));
                designerService.save(designer);
                jsonObject.put("code", 222);
                jsonObject.put("msg", "保存成功");
                return jsonObject ;
            }

            jsonObject.put("code", 444);
            jsonObject.put("msg", "已经存在了");
            return jsonObject ;
        } catch (Exception e) {
            logger.error("保存报错:", e);
            jsonObject.put("code", 444);
            jsonObject.put("msg", "保存失败");
        }
        return jsonObject;
    }

    //更新申请信息
    @Login
    @RequestMapping("/update")
    @ResponseBody
    public JSONObject update(HttpServletRequest request, Designer designer) {
        JSONObject jsonObject = new JSONObject();
        //需要判断吗 todo
        Designer designerFromDB = designerService.queryByMemberId(getMemberId(request)).getModel();

        //页面上不好做 审核中  不能更新的操作 放在服务端做 更好
        if(designerFromDB.getStatus() == DesignerStatus.daishenhe.getIndex()){
            jsonObject.put("code", 444);
            jsonObject.put("msg", "您的信息审核中，暂不能修改");
            return jsonObject ;
        }

        Designer designerTemp = new Designer();

        ModelResult<Boolean> modelResult = null;
        //增加支持审核退回后保存的情况，add by xmm
        if (designerFromDB.getStatus() == DesignerStatus.zancun.getIndex() || designerFromDB.getStatus() == DesignerStatus.shenhetuihui.getIndex()) {
            designer.setId(designerFromDB.getId());
            designer.setMemberId(designerFromDB.getMemberId());
            designer.setStatus(DesignerStatus.daishenhe.getIndex());
            modelResult = designerService.updateById(designer);

            if (!modelResult.isSuccess()) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", modelResult.getErrorMsg());
            } else {
                jsonObject.put("code", 222);
                jsonObject.put("doType", "提交审核成功");
            }
            return jsonObject;
        } else if (designerFromDB.getStatus() == DesignerStatus.shenhetongguo.getIndex()) {
            designerTemp.setId(designerFromDB.getId());
            designerTemp.setMemberId(designerFromDB.getMemberId());
            designerTemp.setCoverImg(designer.getCoverImg());
            designerTemp.setAdeptStyles(designer.getAdeptStyles());
            designerTemp.setProvice(designer.getProvice());
            designerTemp.setCity(designer.getCity());
            designerTemp.setAddress(designer.getAddress());
            if (designerFromDB.getDesignerType() == DesignerType.personal.getIndex()) {
                designerTemp.setQq(designer.getQq());
                designerTemp.setContact(designer.getContact());
                designerTemp.setContactMobile(designer.getContactMobile());
                designerTemp.setCareer(designer.getCareer());
            } else {
                designerTemp.setCompanySize(designer.getCompanySize());
                designerTemp.setLicenseImg(designer.getLicenseImg());
            }
            designerTemp.setBrief(designer.getBrief());
            designerTemp.setDesignIdea(designer.getDesignIdea());
            designerTemp.setExperience(designer.getExperience());
            designerTemp.setClassWorks(designer.getClassWorks());
            designerTemp.setStyleIntro(designer.getStyleIntro());

            modelResult = designerService.updateById(designerTemp);
        }
        try {
            if (!modelResult.isSuccess()) {
                jsonObject.put("code", 444);
                jsonObject.put("msg", modelResult.getErrorMsg());
            } else {
                jsonObject.put("code", 222);
                jsonObject.put("doType", "更新成功");
            }
        } catch (Exception e) {
            logger.error("更新个人设计师：", e);
            jsonObject.put("code", 444);
            jsonObject.put("msg", "更新失败");
        }
        return jsonObject;
    }

    //申请的作品管理
    @Login
    @RequestMapping("/toworks/{designerType}")
    public String toworks(HttpServletRequest request, @PathVariable Integer designerType, Model model) {
        model.addAttribute("menu", "designer");
        model.addAttribute("type", designerType);
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        return "/designer/works";
    }

    //保存作品

    /**
     * @param request
     * @param designerWork
     * @param img
     * @param intro
     * @return
     */
    @Login
    @RequestMapping(value = "/savework", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject savework(HttpServletRequest request, DesignerWork designerWork, String[] img, String[] intro) {
        JSONObject jsonObject = new JSONObject();

        if (img.length != intro.length) {
            logger.info("图片和介绍的大小不一致");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "信息未被填写完整");
            return jsonObject;
        }

        JSONArray jArray = new JSONArray();
        JSONObject jObject = null;
        for (int i = 0; i < img.length; i++) {
            jObject = new JSONObject();
            jObject.put("img", img[i]);
            jObject.put("intro", intro[i]);
            jArray.add(jObject);
        }


        designerWork.setContent(jArray.toJSONString());
        designerWork.setMemberId(getMemberId(request));
        ModelResult<DesignerWork> modelResult = designerWorkService.save(designerWork);

        Designer designer = designerService.queryByMemberId(getMemberId(request)).getModel();
        designer.setWorkId(modelResult.getModel().getId());
//        designer.setStatus(DesignerStatus.daishenhe.getIndex());
        designerService.updateById(designer);

        jsonObject.put("code", 222);
        jsonObject.put("msg", "保存成功");
        return jsonObject;
    }

    @Login
    @RequestMapping("/afterWorkIndex")
    public String afterWorkIndex(HttpServletRequest request, Model model) {
        Designer designer = designerService.queryByMemberId(getMemberId(request)).getModel();
        model.addAttribute("status", designer.getStatus());
        return "/designer/applyResult";
    }


    @RequestMapping("/detail/{designerId}")
    public String designerDetail(Model model, @PathVariable Integer designerId, HttpServletRequest request) {
    	return getDesignerDetailById(model,designerId,request);
    }
    
    @RequestMapping("/detailByMemberId/{memberId}")
    public String designerDetailByMemberId(Model model, @PathVariable Integer memberId, HttpServletRequest request) {
      //通过memberId找到designerId
      Designer designer = designerService.queryByMemberId(memberId).getModel();
      return getDesignerDetailById(model,designer.getId(),request);
    }
    
    private String getDesignerDetailById(Model model, @PathVariable Integer designerId, HttpServletRequest request) {
        ModelResult<DesignerVO> modelResult = designerService.queryVOByDesignerId(designerId);
        MemberVo memberInCache = getMember(request);
        if (modelResult.isSuccess()) {
            DesignerVO designerVO = modelResult.getModel();
            if (memberInCache == null) {
                designerVO.setFollow(false);
            } else {
                MemberCare option = new MemberCare();
                option.setMemberId(memberInCache.getId());
                option.setObjectId(designerVO.getMemberId());
                option.setObjectType(ObjectType.shejishi.getIndex());
                int result = memberCareService.count(option).getModel();
                if(result ==0){
                    designerVO.setFollow(false);
                } else {
                    designerVO.setFollow(true);
                }
            }
            model.addAttribute("data", designerVO);
        }
        return "/designer/designerDetail";    	
    }
    

    //去修改已经提交的认证作品
    @Login
    @RequestMapping("/upApplyWork")
    public String upApplyWork(HttpServletRequest request, Model model) {
        Designer designer = designerService.queryByMemberId(getMemberId(request)).getModel();
        DesignerWork designerWork = designerWorkService.queryById(designer.getWorkId(), null).getModel();
        if (designerWork != null) {
            model.addAttribute("data", designerWork);
        }
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        model.addAttribute("menu", "designer");
        return "/designerwork/upApplyWork";
    }

    /**
     * 更新认证的作品
     *
     * @return
     */
    @Login
    @ResponseBody
    @RequestMapping(value = "/updateWork", method = RequestMethod.POST)
    public JSONObject updateWork(HttpServletRequest request, DesignerWork designerWork, String[] img, String[] intro) {
        JSONObject jsonObject = new JSONObject();
        if (img.length != intro.length) {
            logger.info("图片和介绍的大小不一致");
            jsonObject.put("code", 444);
            jsonObject.put("msg", "信息未被填写完整");
            return jsonObject;
        }

        Designer designer = designerService.queryByMemberId(getMemberId(request)).getModel();

        if (designer.getStatus() == DesignerStatus.daishenhe.getIndex()) {
            logger.info("desinger [{}] 状态：[{}]", designer.getId(), DesignerStatus.findByIndex(designer.getStatus()).getDescription());
            jsonObject.put("code", 444);
            jsonObject.put("msg", "审核中，不能修改");
            return jsonObject;
        }

        JSONArray jArray = new JSONArray();
        JSONObject jObject = null;
        for (int i = 0; i < img.length; i++) {
            jObject = new JSONObject();
            jObject.put("img", img[i]);
            jObject.put("intro", intro[i]);
            jArray.add(jObject);
        }

        designerWork.setId(designer.getWorkId());
        designerWork.setContent(jArray.toJSONString());
        designerWork.setMemberId(getMemberId(request));
        ModelResult<Boolean> modelResult = designerWorkService.updateById(designerWork);

        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }

        jsonObject.put("code", 222);
        jsonObject.put("msg", "保存成功");
        return jsonObject;
    }


    @RequestMapping("/viewCount/{id}")
    public String viewCount(@PathVariable Integer id){
        Designer designer = designerService.queryById(id).getModel();
        Designer tempDesigner = new Designer();
        tempDesigner.setId(designer.getId());
        tempDesigner.setViewCount(designer.getViewCount()+1);
        tempDesigner.setUpdateTime(Calendar.getInstance());
        designerService.updateById(tempDesigner);
        return "forward:/common/pic1.gif";
    }


    /**
     * 前台前端设计师查询列表
     */
    @RequestMapping("/queryDesigner/{type}/{area}/{style}")
    public String indexList(Model model, @PathVariable Integer type, @PathVariable String area, @PathVariable String style) {
        model.addAttribute("hotcity", codeValueService.queryList(CollectionCode.remenchengshi, null, null).getModel());
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        model.addAttribute("types", DesignerType.getAllList());
        model.addAttribute("area", area);
        model.addAttribute("style", style);
        model.addAttribute("type", type);
        if (type == 0 ){
           type = null;
        }
        if (Integer.parseInt(area) == 0) {
            area = null;
        }
        if (Integer.parseInt(style) == 0) {
            style = null;
        }
        int totalCount = designerService.count(area, style, type).getModel();
        model.addAttribute("totalCount", totalCount);

        return "/designer/listDesigner";
    }

    @RequestMapping("/ajaxDesigner")
    @ResponseBody
    public List<DesignerVO> ajaxDesigner(Integer type, String area, String style, DataPage<DesignerVO> dataPage){
        if (Integer.parseInt(area) == 0) {
            area = null;
        }
        if (Integer.parseInt(style) == 0) {
            style = null;
        }
        if (type == 0 ){
            type = null;
        }
        return designerService.queryDesigner(area, style, type, dataPage).getModel().getDataList() ;
    }
    
    /**查看营业执照*/
    @Login
    @RequestMapping(value = "/queryLicenseImg", method = RequestMethod.GET)
	public void queryLicenseImg(String path, HttpServletRequest request,HttpServletResponse response){
		File file=new File(globalParam.getFileRootPrivate() + path);  
        if(file.exists()){
            try {  
                DataOutputStream dos=new DataOutputStream(response.getOutputStream());  
                DataInputStream dis=new DataInputStream(new FileInputStream(file.getAbsolutePath()));  
                byte[] data=new byte[2048];  
                while((dis.read(data))!=-1){
                    dos.write(data);  
                    dos.flush();  
                }
                dis.close();  
                dos.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }
        }
	}

    private List<Area> queryAreaList() {
        List<Area> areaList = memcachedClient.get(province);
        if (areaList == null) {
            areaList = areaService.queryAll(null).getModel();
            memcachedClient.set(province, 60 * 60 * 24, areaList);
        }
        return areaList;
    }

}
