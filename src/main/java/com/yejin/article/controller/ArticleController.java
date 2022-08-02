package com.yejin.article.controller;

import com.yejin.annotation.Controller;
import com.yejin.annotation.GetMapping;

@Controller // 컨트롤러 다
public class ArticleController {
    private static ArticleController instance;

   // private ArticleController(){}
    public static ArticleController getInstance() {
        if(instance==null)
            return instance=new ArticleController();
        else return instance;
    }

    @GetMapping("/usr/article/list")  // /usr/article/list/free 등
    public void showList(){

    }

}
