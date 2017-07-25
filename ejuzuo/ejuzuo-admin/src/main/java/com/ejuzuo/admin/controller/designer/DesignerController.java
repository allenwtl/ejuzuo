package com.ejuzuo.admin.controller.designer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aicai.appmodel.domain.result.BaseResult;
import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.admin.controller.BaseController;
import com.ejuzuo.admin.support.Page;
import com.ejuzuo.common.constants.AdminOperType;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.constants.MemberPointLogTransType;
import com.ejuzuo.common.domain.Area;
import com.ejuzuo.common.domain.CodeValue;
import com.ejuzuo.common.domain.Designer;
import com.ejuzuo.common.domain.DesignerWork;
import com.ejuzuo.common.domain.MemberPointLog;
import com.ejuzuo.common.domain.type.DesignerStatus;
import com.ejuzuo.common.domain.type.DesignerType;
import com.ejuzuo.common.domain.type.ObjectType;
import com.ejuzuo.common.option.DesignerOption;
import com.ejuzuo.common.option.DesignerWorkOption;
import com.ejuzuo.common.service.AreaService;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.service.DesignerService;
import com.ejuzuo.common.service.DesignerWorkService;
import com.ejuzuo.common.service.MemberPointLogService;
import com.ejuzuo.common.service.MsgRecordService;
import com.ejuzuo.common.service.SmsRecordService;

@Controller
@RequestMapping("/designer")
public class DesignerController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(DesignerController.class);
	@Resource
	private DesignerService designerService;
	@Resource
	private CodeValueService codeValueService;
	@Resource
	private AreaService areaService;
	@Resource
	private DesignerWorkService designerWorkService;
	@Resource
	private MemberPointLogService memberPointLogService;
	@Resource
	private SmsRecordService smsRecordService;    
    @Resource
    private MsgRecordService msgRecordService;	
	@Resource(name = "newsDomainRes")
	private  String DOMAIN_RES;
	@Resource(name = "attachmentPath")
	private String PATH_ATTACHMENT;
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public String index(Model model) {
		/**设计师类型*/
		List<JSONObject> lstTree = new ArrayList<JSONObject>();
		List<DesignerType> list = DesignerType.getAllList();
		for (DesignerType designerType : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", designerType.getIndex());
			jsonObject.put("pId", 100);
			jsonObject.put("name", designerType.getDescription());
			lstTree.add(jsonObject);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 100);
		jsonObject.put("name","类别");
		jsonObject.put("isParent", true);
		jsonObject.put("open", true);
		lstTree.add(jsonObject);
		model.addAttribute("lstTree", lstTree);
		/**查询设计风格,存进json*/
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge, null, null);
		model.addAttribute("styles",JSONObject.toJSON(styleResult.getModel()));
		return "designer/designer-index";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(
			@ModelAttribute Page<Designer> page,
			@ModelAttribute DesignerOption option, Integer designerType) {
		
		if (designerType != null) {
			List<Integer> designerTypeList = new ArrayList<>();
			designerTypeList.add(designerType);
			option.setDesignerTypeList(designerTypeList);
		} 
		DataPage<Designer> dataPage = page.toDataPage();
		ModelResult<DataPage<Designer>> pageResult = null;
		try {
			pageResult = designerService.adminQueryPage(option, dataPage);
		} catch (Exception e) {
			logger.error("Page net error", e);
		}
		if (pageResult == null) {
			logger.error("Page is null");
			return Page.returnEmptyPage();
		}
		dataPage = pageResult.getModel();
		return Page.returnPage(dataPage);
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, ModelMap modelMap) {
		ModelResult<Designer> result = designerService.queryById(id);
		Designer designer = result.getModel();
		ModelResult<List<CodeValue>> companySizeResult = codeValueService.queryList(CollectionCode.gongsiguimo, null, null);
		ModelResult<List<CodeValue>> careerResult = codeValueService.queryList(CollectionCode.zhiyeshenfen, null, null);
		/**拼接擅长风格*/
		String adeptStyle = "";
		ModelResult<List<CodeValue>> styleResult = codeValueService.queryList(CollectionCode.fengge, null, null);
		String[] styles = designer.getAdeptStyles().split(",");
		for (String style : styles) {
			for (CodeValue codeValue : styleResult.getModel()) {
				if (style.equals(codeValue.getValueCode())) {
					adeptStyle += codeValue.getValueName()+" ";
					break;
				}
			}
		}
		
		/**查出省市*/
		String province = null;
		String city = null;
		ModelResult<List<Area>> areaResult = areaService.queryAll();
		List<Area> areaList = areaResult.getModel();
		int count = 0;
		for (Area area : areaList) {
			if (area.getId().equals(designer.getProvice())) {
				province = area.getName();
				count++;
			}
			if (area.getId().equals(designer.getCity())) {
				city = area.getName();
				count++;
			}
			if (count == 2) {
				break;
			}
		}
		/**设计师作品*/
		DesignerWorkOption option = new DesignerWorkOption();
		ModelResult<DesignerWork> designerWorkResult  = designerWorkService.queryById(designer.getWorkId(), option);
		if (designerWorkResult.isSuccess() && designerWorkResult.getModel() != null) {
			modelMap.put("designerWork", designerWorkResult.getModel());
		}
		modelMap.put("companySizes", companySizeResult.getModel());
		modelMap.put("carrers", careerResult.getModel());
		modelMap.put("adeptStyle", adeptStyle);
		modelMap.put("designer", designer);
		modelMap.put("city", city);
		modelMap.put("province", province);
		modelMap.put("domainImage", DOMAIN_RES);
		return "designer/designer-detail";
	}
	
	@RequestMapping(value = "{id:[1-9][0-9]*}/approval", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, ModelMap modelMap) {
		this.detail(id,modelMap);
		return "designer/designer-edit";
	}
	
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approval(HttpServletRequest request, @ModelAttribute Designer obj){
		obj.setVerifior(this.getUserName());
		obj.setVerifyTime(Calendar.getInstance());
		obj.setUpdateTime(Calendar.getInstance());
		ModelResult<Boolean> result = designerService.updateById(obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.isSuccess() && result.getModel() == true) {
			map.put("success", true);
			JSONObject joRemark = new JSONObject();
			joRemark.put("id", obj.getId());
			/**审核通过送50积分，短信提醒;不通过直接短信提醒*/
			if (obj.getStatus().equals(DesignerStatus.shenhetongguo.getIndex())) {
				taskExecutor.execute(()->{
					logger.info("设计师审核通过，发送短信通知，赠送50积分。memberId:{}",obj.getMemberId());
					MemberPointLog memberPointLog = new MemberPointLog();
					memberPointLog.setMemberId(obj.getMemberId());
					memberPointLog.setTransType(MemberPointLogTransType.APPLY_DESIGNER.getIndex());
					memberPointLog.setRelatedType(ObjectType.shejishi.getIndex());
					memberPointLog.setRelatedId(obj.getId());
					memberPointLogService.sendPoint(memberPointLog);
					smsRecordService.sendSmsRecord(obj.getMemberId(), "尊敬的用户，你的设计师认证已经通过，请登录平台查看。");
					this.logOperation(AdminOperType.DESIGNER_ENABLE, joRemark);
					
	                //发送个人消息
	                logger.info("memberId [{}] 申请的设计师审批成功，发送个人消息", obj.getMemberId());
	                String msgContent = "您好！您已完善资料，恭喜您升级为巨作网的高级会员，并且设计师认证已经审批通过，平台已赠送您50积分，请注意查看后台积分记录！";
	                msgRecordService.addPersonMsg(obj.getMemberId(), msgContent); 					
				});
			}else if (obj.getStatus().equals(DesignerStatus.shenhetuihui.getIndex())) {
				taskExecutor.execute(()->{
					logger.info("设计师审核退回，发送短信通知。memberId:{}",obj.getMemberId());
					smsRecordService.sendSmsRecord(obj.getMemberId(), "尊敬的用户，你的设计师认证已经被退回，具体原因，请登录平台查看。");
					this.logOperation(AdminOperType.DESIGNER_DISABLE, joRemark);
				});
			}
		}else {
			map.put("success", false);
			map.put("msg", result.getErrorMsg());
		}
		return map;
	}
	
	@RequestMapping(value = "/changeHomed", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changePreferred(Integer id, Integer value){
		Map<String, Object> map = new HashMap<String, Object>();
		BaseResult result = designerService.updateHomed(id,value);
		if (result.isSuccess()) {
			map.put("success", true);
		}else {
			map.put("success", false);
			map.put("msg", "设置失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/queryLicenseImg", method = RequestMethod.GET)
	public void queryLicenseImg(String path, HttpServletRequest request,HttpServletResponse response){
		File file=new File(PATH_ATTACHMENT + path);  
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
	
}
