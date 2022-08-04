package com.yejin.article.controller;

import com.yejin.Container;
import com.yejin.Rq;
import com.yejin.annotation.Autowired;
import com.yejin.annotation.Controller;
import com.yejin.annotation.GetMapping;
import com.yejin.article.dto.ArticleDto;
import com.yejin.article.service.ArticleService;

import java.util.List;

@Controller // 컨트롤러 다
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @GetMapping("/usr/article/list/{boardCode}")
    //@GetMapping("/usr/article/list") // /usr/article/list/free 와 같이 관련된 요청을 처리하는 함수이다.
    // 아래 showList 는 Get /usr/article/list 으로 요청이 왔을 때 실행 되어야 하는 함수이다.
    public void showList(Rq rq) {
        System.out.println("controller.showList() : ");
       // rq.println("게시물 리스트");
        List<ArticleDto> articleDtos = articleService.getArticles();
        System.out.println("controller articleDtos : "+articleDtos);
        rq.setAttr("articles", articleDtos);
        rq.view("usr/article/list");
    }

    @GetMapping("/usr/article/detail/{boardCode}/{id}")
    public void showDetail(Rq rq) {
        System.out.println("controller.showDetail() : ");
        rq.println("게시물 상세페이지<br>");

        String boardCode = rq.getParam("boardCode","");
        System.out.println("boardCode " + boardCode);
       // long id = rq.getLongPathValueByIndex(1, -1);
        long id = rq.getLongParam("id",-1);
        System.out.println("게시물 id " + id);

        rq.println("%d번 게시물".formatted(id));
    }


    @GetMapping("/usr/article/modify/{boardCode}/{id}")
    public void showModify(Rq rq) {
        System.out.println("controller.showModify() : ");
        rq.println("게시물 수정페이지<br>");

        long id = rq.getLongPathValueByIndex(1, -1);
        // long id = rq.getLongParam("id"); // 곧 기능 구현

        rq.println("%d번 게시물".formatted(id));
    }

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


}
