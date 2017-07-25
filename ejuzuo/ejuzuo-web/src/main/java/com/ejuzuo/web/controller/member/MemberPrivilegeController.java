package com.ejuzuo.web.controller.member;

import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tianlun.wu on 2016/4/28 0028.
 */

@Controller
@RequestMapping("privilege")
public class MemberPrivilegeController extends BaseController {

    @Login
    @RequestMapping("/index")
    public String toPrivilegeIndex(Model model){
        model.addAttribute("menu", "privilege");
        return "/member/privilege";
    }


}
