package com.yejin.home.controller;

import com.yejin.annotation.Controller;

@Controller
public class HomeController {
    private static HomeController instance;
    public static HomeController getInstance() {
        if(instance==null)
            return instance=new HomeController();
        else return instance;
    }
}
