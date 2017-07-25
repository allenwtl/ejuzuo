package com.ejuzuo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allen on 2016/4/3.
 */

@Controller
@RequestMapping("exception")
public class ExceptionController  extends BaseController{


    @RequestMapping("/invalidRequest")
    public String invalidRequest (){

        return "/error/invalidRequest";
    }


    @RequestMapping("/404")
    public String notFound(){

        return "/error/404";
    }

    @RequestMapping("/500")
    public String exception(){

        return "/error/500";
    }

    @RequestMapping("/timeOut")
    public String timeOut(){
        return "/error/timeOut";
    }

}
