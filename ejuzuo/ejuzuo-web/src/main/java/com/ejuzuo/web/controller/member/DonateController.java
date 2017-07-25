package com.ejuzuo.web.controller.member;

import com.ejuzuo.web.annotation.Login;
import com.ejuzuo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tianlun.wu on 2016/5/13 0013.
 */

@Controller
@RequestMapping("donate")
public class DonateController extends BaseController {

    @Login
    @RequestMapping("/index")
    public String donate(Model model){

        model.addAttribute("menu", "donate");

        return "/member/donate/donateIndex";
    }


    /**
     * 选择赞助类型
     * @param index
     * @return
     */
    @Login
    @RequestMapping("/type/{index}")
    public String donateType(@PathVariable Integer index, Model model){
        model.addAttribute("type", index);
        model.addAttribute("menu", "donate");
        return "/member/donate/donateSelect";
    }

}
