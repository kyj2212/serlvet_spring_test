package com.yejin.article.controller;

import com.yejin.Container;
import com.yejin.Rq;
import com.yejin.annotation.Autowired;
import com.yejin.annotation.Controller;
import com.yejin.annotation.GetMapping;
import com.yejin.annotation.PostMapping;
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

    //@GetMapping("/usr/article/detail/{id}") // boardCode 왜 뺏지?
    @GetMapping("/usr/article/detail/{boardCode}/{id}")
    public void showDetail(Rq rq) {
        System.out.println("controller.showDetail() : ");

        String boardCode = rq.getParam("boardCode","");
        System.out.println("boardCode " + boardCode);
        // 아직 board 를 나누지 않았구나..!
        if (boardCode == null) {
            rq.historyBack("잘못된 접근입니다.");
            System.out.println("[E-01] boardCode is null, Select the board type, ex) free");
            return;
        }
        long id = rq.getLongParam("id",-1);

        if (id == 0) {
            rq.historyBack("번호를 입력해주세요.");
            return;
        }

        ArticleDto articleDto=articleService.getArticleById(id);
        System.out.println("article : "+ articleDto);

        if (articleDto == null) {
            rq.historyBack("해당 글이 존재하지 않습니다.");
            return;
        }

        rq.setAttr("article",articleDto);
        rq.view("usr/article/detail");
      //  System.out.println("게시물 id " + id);

      //  rq.println("%d번 게시물".formatted(id));
    }

    @GetMapping("/usr/article/write")
    public void showWrite(Rq rq) {
        System.out.println("controller.showWrite() : ");

        rq.view("/usr/article/write");
    }

    @PostMapping("/usr/article/write")
    public void write(Rq rq){
        String title= rq.getParam("title","");
        String body = rq.getParam("body","");

        if (title.length() == 0) {
            rq.historyBack("제목을 입력해주세요.");
            return;
        }

        if (body.length() == 0) {
            rq.historyBack("내용을 입력해주세요.");
            return;
        }

        long id = articleService.write(title, body);
        rq.replace("/usr/article/detail/free/%d".formatted(id), "%d번 게시물이 생성 되었습니다.".formatted(id));
    }

    @GetMapping("/usr/article/modify/{boardCode}/{id}")
    public void showModify(Rq rq) {
        System.out.println("controller.showModify() : ");
        //rq.println("게시물 수정페이지<br>");
        String boardCode = rq.getParam("boardCode","");

        if (boardCode == null) {
            rq.historyBack("잘못된 접근입니다.");
            System.out.println("[E-01] boardCode is null, Select the board type, ex) free");
            return;
        }
        long id = rq.getLongParam("id",-1);
        if (id == 0) {
            rq.historyBack("번호를 입력해주세요.");
            return;
        }

        ArticleDto articleDto = articleService.getArticleById(id);

        if (articleDto == null) {
            rq.historyBack("해당 글이 존재하지 않습니다.");
            return;
        }
        System.out.println(articleDto);
        rq.setAttr("article",articleDto);
        rq.view("usr/article/modify");
    }

    @PostMapping("/usr/article/modify/{boardCode}/{id}")
    public void modify(Rq rq){
        String title= rq.getParam("title","");
        String body = rq.getParam("body","");

        if (title.length() == 0) {
            rq.historyBack("제목을 입력해주세요.");
            return;
        }

        if (body.length() == 0) {
            rq.historyBack("내용을 입력해주세요.");
            return;
        }
        long id = rq.getLongParam("id",-1);
        if(id==-1){
            rq.historyBack("번호를 입력해주세요.");
            return;
        }

        articleService.modify(id,title, body);
        rq.replace("/usr/article/detail/free/%d".formatted(id), "%d번 게시물이 생성 되었습니다.".formatted(id));
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
