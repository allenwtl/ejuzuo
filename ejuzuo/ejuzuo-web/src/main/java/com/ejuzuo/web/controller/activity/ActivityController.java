package com.ejuzuo.web.controller.activity;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.CollectionCode;
import com.ejuzuo.common.domain.ActivityEnroll;
import com.ejuzuo.common.domain.ContentInfo;
import com.ejuzuo.common.domain.type.ContentInfoRelatedType;
import com.ejuzuo.common.option.ActivityEnrollOption;
import com.ejuzuo.common.option.ActivityInfoOption;
import com.ejuzuo.common.service.ActivityEnrollService;
import com.ejuzuo.common.service.ActivityInfoService;
import com.ejuzuo.common.service.CodeValueService;
import com.ejuzuo.common.service.ContentInfoService;
import com.ejuzuo.common.util.DateUtil;
import com.ejuzuo.common.vo.ActivityInfoVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.common.ActivityTime;
import com.ejuzuo.web.controller.BaseController;
import com.ejuzuo.web.vo.MemberVo;
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
 * Created by tianlun.wu on 2016/4/29 0029.
 */


@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Resource
    private ActivityInfoService activityInfoService;

    @Resource
    private ActivityEnrollService activityEnrollService;

    @Resource
    private CodeValueService codeValueService;

    @Resource
    private ContentInfoService contentInfoService;

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("huodongleibei", codeValueService.queryList(CollectionCode.huodongleibei, null, null).getModel());
        model.addAttribute("areaList", codeValueService.queryList(CollectionCode.remenchengshi, null, null).getModel());
        model.addAttribute("timeList", ActivityTime.getAllList());

        ActivityInfoOption activityInfoOption = new ActivityInfoOption();
        activityInfoOption.setStatus(1);
        activityInfoOption.setEditStatus(1);
        int totalCount = activityInfoService.count(activityInfoOption).getModel();
        model.addAttribute("totalCount", totalCount);

        return "/activity/activityIndex";
    }


    @RequestMapping("/list/{activityType}/{area}/{time}")
    public String list(@PathVariable String activityType, @PathVariable String area, @PathVariable Integer time, Model model) {

        model.addAttribute("huodongleibei", codeValueService.queryList(CollectionCode.huodongleibei, null, null).getModel());
        model.addAttribute("areaList", codeValueService.queryList(CollectionCode.remenchengshi, null, null).getModel());
        model.addAttribute("timeList", ActivityTime.getAllList());

        ActivityInfoOption activityInfoOption = new ActivityInfoOption();
        activityInfoOption.setStatus(1);
        activityInfoOption.setEditStatus(1);

        if (Integer.parseInt(activityType) != 0) {
            activityInfoOption.setCategory(activityType);
        }

        if (Integer.parseInt(area) != 0) {
            activityInfoOption.setCity(area);
        }

        if (ActivityTime.findByIndex(time) != null) {
            setHoldDate(ActivityTime.findByIndex(time), activityInfoOption);
        }
        if (ActivityTime.findByIndex(time) != null) {
            setHoldDate(ActivityTime.findByIndex(time), activityInfoOption);
        }

        model.addAttribute("totalCount", activityInfoService.count(activityInfoOption).getModel());

        return "/activity/activityIndex";
    }


    @RequestMapping("/ajaxList/{activityType}/{area}/{time}")
    @ResponseBody
    public List<ActivityInfoVO> ajaxList(@PathVariable String activityType, @PathVariable String area, @PathVariable Integer time,
                                         DataPage<ActivityInfoVO> dataPage) {

        ActivityInfoOption activityInfoOption = new ActivityInfoOption();
        activityInfoOption.setStatus(1);
        activityInfoOption.setEditStatus(1);
        if (Integer.parseInt(activityType) != 0) {
            activityInfoOption.setCategory(activityType);
        }

        if (Integer.parseInt(area) != 0) {
            activityInfoOption.setCity(area);
        }

        if (ActivityTime.findByIndex(time) != null) {
            setHoldDate(ActivityTime.findByIndex(time), activityInfoOption);
        }

        dataPage.setPageSize(9);
        dataPage.setNeedTotalCount(false);

        List<ActivityInfoVO> activityInfoVOList = activityInfoService.queryVOPage(dataPage, activityInfoOption).getPage().getDataList();

        return activityInfoVOList;
    }


    @RequestMapping("/detail/{activityId}")
    public String activityDetail(@PathVariable Integer activityId, Model model, HttpServletRequest request) {

        ActivityInfoVO activityInfoVO = activityInfoService.queryVOById(activityId).getModel();
        model.addAttribute("data", activityInfoVO);
        ContentInfo contentInfo = contentInfoService.query(ContentInfoRelatedType.ACTIVITY, activityInfoVO.getId()).getModel();
        model.addAttribute("contentInfo", contentInfo);
        MemberVo memberVo = getMember(request);
        if (memberVo != null) {
            ModelResult<ActivityEnroll> modelResult = activityEnrollService.queryByMemberIdAndActivityId(activityId, memberVo.getId());
            if (modelResult.isSuccess() && modelResult.getModel() != null) {
                model.addAttribute("sign", true);
            }
        }

        return "/activity/activityDetail";
    }


    @Login
    @RequestMapping("/signUp/{activityId}")
    @ResponseBody
    public JSONObject signUp(@PathVariable Integer activityId, HttpServletRequest request) {
        ActivityEnrollOption option = new ActivityEnrollOption();
        option.setActivityId(activityId);
        option.setMemberId(getMemberId(request));

        ModelResult<Boolean> modelResult = activityEnrollService.signUp(option);

        JSONObject jsonObject = new JSONObject();
        if (modelResult.isSuccess() && modelResult.getModel()) {
            jsonObject.put("code", 222);
            jsonObject.put("msg", "您好！恭喜您已成功参加了本次的活动，更多精彩活动请继续关注巨作网，谢谢您的参与！");
        } else {
            jsonObject.put("code", 444);
            jsonObject.put("msg", modelResult.getErrorMsg()); //已经报名了或者没有权限报名。
        }

        return jsonObject;
    }


    @RequestMapping("/viewCount/{activityId}")
    public String viewCount(@PathVariable Integer activityId) {
        activityInfoService.updateViewCount(activityId);
        return "forward:/common/pic1.gif";
    }

    private void setHoldDate(ActivityTime activityTime, ActivityInfoOption activityInfoOption) {
        if (activityTime.getIndex() == ActivityTime.today.getIndex()) {
            activityInfoOption.setHoldBeginDate(DateUtil.getTheDayZero());
            activityInfoOption.setHoldEndDate(DateUtil.getTheDayMidnight());
        } else if (activityTime.getIndex() == ActivityTime.oneWeek.getIndex()) {
            activityInfoOption.setHoldBeginDate(DateUtil.getTheDayZero());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 7);
            activityInfoOption.setHoldEndDate(DateUtil.getTheDayMidnight(calendar));
        } else if (activityTime.getIndex() == ActivityTime.twoWeek.getIndex()) {
            activityInfoOption.setHoldBeginDate(DateUtil.getTheDayZero());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 14);
            activityInfoOption.setHoldEndDate(DateUtil.getTheDayMidnight(calendar));
        } else if (activityTime.getIndex() == ActivityTime.oneMonth.getIndex()) {
            activityInfoOption.setHoldBeginDate(DateUtil.getTheDayZero());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 30);
            activityInfoOption.setHoldEndDate(DateUtil.getTheDayMidnight(calendar));
        }
    }
}