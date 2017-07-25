package com.ejuzuo.web.controller.member;

import com.aicai.appmodel.page.DataPage;
import com.alibaba.fastjson.JSONObject;
import com.ejuzuo.common.constants.Status;
import com.ejuzuo.common.domain.DownloadInfo;
import com.ejuzuo.common.domain.type.DownloadPayStatus;
import com.ejuzuo.common.option.DownloadInfoOption;
import com.ejuzuo.common.service.DigitalFurnitureService;
import com.ejuzuo.common.service.DownloadInfoService;
import com.ejuzuo.common.vo.DownloadInfoVO;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by allen on 2016/4/4.
 */

@Controller
@RequestMapping("downloadInfo")
public class DownloadInfoController extends BaseController {


    @Resource
    private DownloadInfoService downloadInfoService;

    @Resource
    private DigitalFurnitureService digitalFurnitureService ;

    @Login
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("menu", "downFile");

        DownloadInfoOption option = new DownloadInfoOption();
        option.setMemberId((long) getMemberId(request));
        option.setPayStatus(DownloadPayStatus.PAYED.getIndex());
        option.setStatus(Status.STATUS.getIndex());

        model.addAttribute("totalCount", downloadInfoService.countPage(option).getModel());

        return "/mydownloadfile/downloadList";
    }


    @RequestMapping("/queryList")
    @ResponseBody
    public JSONObject queryList(HttpServletRequest request, DataPage<DownloadInfo> dataPage, Model model) {
        DownloadInfoOption option = new DownloadInfoOption();
        option.setMemberId((long) getMemberId(request));
        option.setPayStatus(DownloadPayStatus.PAYED.getIndex());
        option.setStatus(Status.STATUS.getIndex());
        option.setNowTime(Calendar.getInstance());

        dataPage = downloadInfoService.queryPage(option, dataPage).getModel();

        JSONObject jsonObject = new JSONObject();

        List<DownloadInfoVO> dataList = new ArrayList<>();
        DownloadInfoVO vo = null ;
        for (DownloadInfo downloadInfo : dataPage.getDataList()) {
            vo = new DownloadInfoVO();
            try {
                BeanUtils.copyProperties(vo, downloadInfo);
                vo.setExpireTime(new SimpleDateFormat("yyyy-MM-dd").format(downloadInfo.getExpire().getTime()));
                vo.setDigitalFurniture(digitalFurnitureService.queryById(vo.getGoodsId()).getModel());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            dataList.add(vo);
        }

        jsonObject.put("data", dataList);
        return jsonObject;
    }

}
