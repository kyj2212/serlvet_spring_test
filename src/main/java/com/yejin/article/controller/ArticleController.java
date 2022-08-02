package com.yejin.article.controller;

import com.yejin.annotation.Autowired;
import com.yejin.annotation.Controller;
import com.yejin.annotation.GetMapping;
import com.yejin.article.service.ArticleService;

@Controller // 컨트롤러 다
public class ArticleController {

    @Autowired
    private ArticleService articleService;

/*    public ArticleService getArticleServiceForTest(){
        return articleService;
    }*/

/*    private void initArticleService(ArticleService articleService){
        this.articleService=Container.getFileds(ArticleService.class);
        System.out.println(this.articleService);
    }*/
/*
    private static ArticleController instance;

   // private ArticleController(){}
    public static ArticleController getInstance() {
        if(instance==null)
            return instance=new ArticleController();
        else return instance;
    }
*/

    @GetMapping("/usr/article/list")  // /usr/article/list/free 등
    public void showList(){

    }

}
