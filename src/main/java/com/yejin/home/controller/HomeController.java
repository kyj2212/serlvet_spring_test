package com.yejin.home.controller;

import com.yejin.Rq;
import com.yejin.annotation.Controller;
import com.yejin.annotation.GetMapping;
import com.yejin.home.service.HomeService;

@Controller
public class HomeController {
    // No use Autowired
    private static HomeService homeService;

    @GetMapping("/usr/home/main")
    public void showMain(Rq rq) {
        rq.println("메인 페이지");
    }
}
