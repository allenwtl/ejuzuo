package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.EditStatus;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.option.MemberFavoriteOption;
import com.ejuzuo.common.service.*;
import com.ejuzuo.common.vo.DesignerWorkVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.util.ExecutorUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.List;

/**
 * 设计师 作品管理
 * Created by tianlun.wu on 2016/4/11 0011.
 */

@Controller
@RequestMapping("designerWork")
public class DesignerWorkController extends BaseController {

    @Resource(name = "designerWorkServiceClient")
    private DesignerWorkService designerWorkService;

    @Resource
    private CodeValueService codeValueService;

    @Resource
    private DesignerService designerService;

    @Resource
    private MemberPointLogService memberPointLogService;

    @Resource
    private MemberFavoriteService memberFavoriteService;
    
    @Resource
    private MsgRecordService msgRecordService;


    //展示数据的页面
    @Login
    @RequestMapping("/uploadWork")
    public String uploadWork(Model model, HttpServletRequest request) {
        Integer memberId = getMemberId(request);
        Designer designer = designerService.queryByMemberId(memberId).getModel();
        if (designer == null) {
            logger.info("memberId:{} 不是设计师", memberId);
            return "redirect:/designer/setting";
        }
        model.addAttribute("menu", "designerwork");
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        return "/designerwork/uploadPage";
    }


    /**
     * 用户中心-作品管理
     */
    @Login
    @RequestMapping("/index")
    public String memberCenterWorkList(HttpServletRequest request, Model model) {
        Integer memberId = getMemberId(request);
        Designer designer = designerService.queryByMemberId(memberId).getModel();
        if (designer == null) {
            logger.info("memberId:{} 不是设计师", memberId);
            return "redirect:/designer/setting";
        }

        model.addAttribute("totalCount", designerWorkService.countWork(designer.getMemberId(), null, null).getModel());
        model.addAttribute("menu", "designerwork");
        return "/designerwork/designerworkList";
    }

    /**
     * 用户中心-作品管理-分页查询
     *
     * @param request
     * @param dataPage
     * @return
     */
    @Login
    @RequestMapping("/ajaxList")
    @ResponseBody
    public List<DesignerWorkVO> ajaxList(HttpServletRequest request, DataPage<DesignerWork> dataPage) {
        dataPage.setNeedTotalCount(false);
        dataPage.setPageSize(5);
        DesignerWorkOption designerWorkOption = new DesignerWorkOption();
        designerWorkOption.setMemberId(getMemberId(request));
        return designerWorkService.queryByDesigner(designerWorkOption, dataPage).getModel().getDataList();
    }

    /**
     * 用户中心-作品管理-删除
     *
     * @param request
     * @param id
     * @return
     */
    @Login
    @RequestMapping("/deleteById/{id}")
    @ResponseBody
    public JSONObject deleteById(HttpServletRequest request, @PathVariable Integer id) {
        JSONObject jsonObject = new JSONObject();
        boolean result = designerWorkService.deleteByMemberId(getMemberId(request), id).getModel();
        if (result) {
            jsonObject.put("code", "222");
            jsonObject.put("msg", "删除成功");
            return jsonObject;
        } else {
            jsonObject.put("code", "444");
            jsonObject.put("msg", "删除失败");
            return jsonObject;
        }
    }

    /**
     * 用户中心-作品管理-发布or暂停发布
     *
     * @return
     */
    @Login
    @RequestMapping("/deploy/{id}")
    @ResponseBody
    public JSONObject deploy(@PathVariable Integer id, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        DesignerWorkOption designerWorkOption = new DesignerWorkOption();
        designerWorkOption.setMemberId(getMemberId(request));

        DesignerWork designerWork = designerWorkService.queryById(id, designerWorkOption).getModel();
        if (designerWork == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "找不到该作品");
            return jsonObject;
        }

        DesignerWork temp = new DesignerWork();
        temp.setId(designerWork.getId());
        temp.setMemberId(designerWork.getMemberId());
        if (designerWork.getEditStatus() == EditStatus.PUBLISHED.getIndex()) {
            temp.setEditStatus(EditStatus.SAVE.getIndex());
        } else {
            temp.setEditStatus(EditStatus.PUBLISHED.getIndex());
        }

        ModelResult<Boolean> modelResult = designerWorkService.updateById(temp);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }
        jsonObject.put("code", 222);
        jsonObject.put("msg", "操作成功");
        return jsonObject;
    }


    /**
     * 用户中心-作品管理-修改
     *
     * @param id
     * @param request
     * @return
     */
    @Login
    @RequestMapping("/update/{id}")
    public String updateById(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Integer memberId = getMemberId(request);
        Designer designer = designerService.queryByMemberId(memberId).getModel();
        if (designer == null) {
            logger.info("memberId:{} 不是设计师", memberId);
            return "redirect:/designer/setting";
        }
        DesignerWorkOption designerWorkOption = new DesignerWorkOption();
        designerWorkOption.setMemberId(memberId);
        DesignerWork designerWork = designerWorkService.queryById(id, designerWorkOption).getModel();

        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        model.addAttribute("data", designerWork);
        model.addAttribute("menu", "designerwork");
        return "/designerwork/memberworkDetail";
    }


    //作品上传页面
    @Login
    @RequestMapping("/toUploadPage")
    public String toUploadPage(Model model) {
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        return "/designerwork/uploadPage";
    }

    //修改用户的作品
    @Login
    @RequestMapping("/updateWork")
    @ResponseBody
    public JSONObject updateWork(DesignerWork designerWork, String[] img, String[] intro, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Integer memberId = getMemberId(request);
        DesignerWorkOption designerWorkOption = new DesignerWorkOption();
        designerWorkOption.setMemberId(memberId);
        DesignerWork designerWorkFromDB = designerWorkService.queryById(designerWork.getId(), designerWorkOption).getModel();

        if (designerWorkFromDB == null) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", "本作品未找到");
            return jsonObject;
        }

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
        designerWork.setMemberId(memberId);
        ModelResult<Boolean> modelResult = designerWorkService.updateById(designerWork);
        if (!modelResult.isSuccess()) {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg());
            return jsonObject;
        }
        jsonObject.put("code", 222);
        jsonObject.put("msg", "修改成功");
        return jsonObject;
    }


    //保存用户上传的作品
    @Login
    @RequestMapping("/saveWork")
    @ResponseBody
    public JSONObject saveWork(DesignerWork designerWork, String[] img, String[] intro, HttpServletRequest request) {
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

        Integer memberId = getMemberId(request);
        logger.info("savework:{}", jArray.toJSONString());
        designerWork.setContent(jArray.toJSONString());
        designerWork.setMemberId(memberId);
        DesignerWork designerWorkFromDB = designerWorkService.save(designerWork).getModel();
        jsonObject.put("code", 222);
        //保存成功
        jsonObject.put("msg", "您好！您的作品已上存成功，距离发布在首页还差一步，请到后台“作品管理”点击“发布”完成哦~");


        ExecutorUtil.fixedThread().execute(new Runnable() {
            @Override
            public void run() {
                MemberPointLog memberPointLog = new MemberPointLog();
                memberPointLog.setMemberId(memberId);
                memberPointLog.setTransType(MemberPointLogTransType.UPLOAD_WORK.getIndex());
                memberPointLog.setRelatedId(designerWorkFromDB.getId());
                memberPointLog.setRelatedType(ObjectType.shejizuopin.getIndex());
                memberPointLogService.sendPoint(memberPointLog);
                
                //发送系统中的个人消息
                logger.info("memberId [{}] 上传作品成功，发送个人消息", memberId);
                String msgContent = "您好！您已成功上传作品，获得了5个积分，请再接再厉！";
                msgRecordService.addPersonMsg(memberId, msgContent);  
            }
        });

        return jsonObject;
    }


    //前端展示设计师作品
    @RequestMapping("/indexList/{type}/{area}/{style}")
    public String indexList(Model model, @PathVariable Integer type, @PathVariable String area, @PathVariable String style, DataPage<DesignerWork> dataPage) {
        model.addAttribute("hotcity", codeValueService.queryList(CollectionCode.remenchengshi, null, null).getModel());
        model.addAttribute("fengge", codeValueService.queryList(CollectionCode.fengge, null, null).getModel());
        model.addAttribute("area", area);
        model.addAttribute("style", style);
        model.addAttribute("type", type);
        if (Integer.parseInt(area) == 0) {
            area = null;
        }
        if (Integer.parseInt(style) == 0) {
            style = null;
        }

        int totalCount = designerWorkService.countUnion(area, style, type).getModel();
        model.addAttribute("totalCount", totalCount);

        return "/designerwork/listwork";
    }

    //设计师首页--分页
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject list(String area, String style, Integer type, DataPage<DesignerWork> dataPage) {
        JSONObject jsonObject = new JSONObject();
        if (Integer.parseInt(area) == 0) {
            area = null;
        }
        if (Integer.parseInt(style) == 0) {
            style = null;
        }

        DataPage<DesignerWorkVO> dataPageVO = designerWorkService.queryUnion(area, style, type, dataPage).getPage();
        jsonObject.put("data", dataPageVO.getDataList());
        return jsonObject;
    }

    //设计师详情页面--设计作品分页
    @RequestMapping("/designerList/{memberId}")
    @ResponseBody
    public JSONObject listDesigner(@PathVariable Integer memberId, DataPage<DesignerWork> dataPage) {
        JSONObject jsonObject = new JSONObject();
        DesignerWorkOption designerWorkOption = new DesignerWorkOption();
        designerWorkOption.setMemberId(memberId);
        designerWorkOption.setEditStatus(EditStatus.PUBLISHED.getIndex());
        designerWorkOption.setStatus(Status.STATUS.getIndex());
        DataPage<DesignerWorkVO> dataPageVO = designerWorkService.queryByDesigner(designerWorkOption, dataPage).getModel();
        jsonObject.put("data", dataPageVO.getDataList());
        return jsonObject;
    }

    /**
     * 设计师作品详情页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/detail/{id}")
    public String designerWorkDetail(@PathVariable Integer id, Model model, HttpServletRequest request) {
        DesignerWorkVO designerWorkVO = designerWorkService.queryVOById(id).getModel();
        if (designerWorkVO == null) {
            logger.info("根据id:[{}] 查询不到作品", id);
        } else {
            Integer memberId = getMemberId(request);
            if (memberId != null) {
                MemberFavoriteOption memberFavoriteOption = new MemberFavoriteOption();
                memberFavoriteOption.setMemberId(memberId);
                memberFavoriteOption.setObjectType(ObjectType.shejizuopin.getIndex());
                memberFavoriteOption.setObjectId(id);
                int size = memberFavoriteService.count(memberFavoriteOption).getModel();
                if (size > 0) {
                    designerWorkVO.setFavorite(true);
                }
//                //通过memberId找到
//                Designer designer = designerService.queryByMemberId(designerWorkVO.getMemberId()).getModel();
//                designerWorkVO.setDesignerId(designer.getId());
            }
            //做一些格式处理
            postHandleDetail(designerWorkVO);
            model.addAttribute("data", designerWorkVO);
        }

        return "/designerwork/designerworkDetail";
    }

    /**
     * 把图片57001470277360294-2240294.jpg转换成57001470277360294.jpg
     * 
     * @param designerWorkVO
     */
    private void postHandleDetail(DesignerWorkVO designerWorkVO) {
    	//处理图片成原图片
    	String preContent = designerWorkVO.getContent();
    	//preContent = "[{\"img\":\"/img/designerwork/20160804/57001470277360294-2240294.jpg\",\"intro\":\"装修二次改造\"},{\"img\":\"/img/designerwork/20160803/23101470193509635-0509635.jpg\",\"intro\":\"装修二次改造\"},{\"img\":\"/img/designerwork/20160803/22491470193522738-0522739.jpg\",\"intro\":\"装修二次改造\"}]";
    	JSONArray array = JSONArray.parseArray(preContent);
    	for (int i=0;i<array.size();i++){
    	 	JSONObject o = array.getJSONObject(i);
    	 	String img = o.getString("img");
    	 	o.put("img", img.replaceAll("\\-[0-9]+\\.", "\\.")); 
    	}
    	
    	String content = array.toJSONString();
    	designerWorkVO.setContent(content);    	
    }
    
    @RequestMapping("/viewCount/{id}")
    public String viewCount(@PathVariable Integer id) {
        DesignerWork designerWork = designerWorkService.queryById(id, null).getModel();
        DesignerWork tempDesignerWork = new DesignerWork();
        tempDesignerWork.setId(designerWork.getId());
        tempDesignerWork.setViewCount(designerWork.getViewCount() + 1);
        tempDesignerWork.setUpdateTime(Calendar.getInstance());
        designerWorkService.updateById(tempDesignerWork);
        return "forward:/common/pic1.gif";
    }

}
