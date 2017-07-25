package com.ejuzuo.web.controller.member;

import com.ejuzuo.common.service.MemberVipService;
import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by tianlun.wu on 2016/4/7 0007.
 */

@Controller
@RequestMapping("memberVip")
public class MemberVipController extends BaseController {

    @Resource
    private MemberVipService memberVipService;


    @Login
    @RequestMapping("/index")
    public String vipIndex(){

        return "/membervip/index";
    }

}
