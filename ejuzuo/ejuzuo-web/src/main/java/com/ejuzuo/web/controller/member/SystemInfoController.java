package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.domain.MsgRecord;
import com.ejuzuo.common.service.MsgRecordService;
import com.ejuzuo.common.vo.MsgRecordVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlun.wu on 2016/5/7 0007.
 */

@Controller
@RequestMapping("info")
public class SystemInfoController extends BaseController {

    @Resource
    private MsgRecordService msgRecordService;


    @Login
    @RequestMapping("/index")
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("menu", "info");
        model.addAttribute("totalCount", msgRecordService.count().getModel());
        return "/member/info/infoList";
    }

    @Login
    @RequestMapping("/ajaxList")
    @ResponseBody
    public List<MsgRecordVO> queryList(HttpServletRequest request, DataPage<MsgRecordVO> dataPage) {
        dataPage.setPageSize(5);
        List<MsgRecord> list = msgRecordService.getMsgRecordNormal(getMemberId(request),
                (dataPage.getPageNo() - 1) * dataPage.getPageSize(), dataPage.getPageSize()).getModel();
        List<MsgRecordVO> voList = new ArrayList<>();
        MsgRecordVO vo = null;
        for (MsgRecord item : list) {
            vo = new MsgRecordVO();
            try {
                BeanUtils.copyProperties(vo, item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            voList.add(vo);
        }

        return voList;
    }


    @Login
    @RequestMapping("/click/{msgId}")
    @ResponseBody
    public JSONObject click(@PathVariable Integer msgId, HttpServletRequest request) {
        msgRecordService.updateMsgReach((long) msgId, getMemberId(request));
        return new JSONObject();
    }


}
