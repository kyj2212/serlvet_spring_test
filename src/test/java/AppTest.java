

import com.yejin.Container;
import com.yejin.ControllerManager;
import com.yejin.RouteInfo;
import com.yejin.article.controller.ArticleController;
import com.yejin.article.repository.ArticleRepository;
import com.yejin.article.service.ArticleService;
import com.yejin.home.controller.HomeController;
import com.yejin.home.service.HomeService;
import com.yejin.util.Ut;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void test_assertThat() {
        int rs = 10 + 20;
        assertThat(rs).isEqualTo(30);
    }
    @Test
    public void ioc_articleControl__Test1(){
      //  ArticleController articleController1 = Container.getArticleController();
       // assertThat(articleController1).isNotNull();
    }
    @Test
    public void ioc_articleControl__SingleTon(){
       // ArticleController articleController1 = Container.getArticleController();
      //  ArticleController articleController2 = Container.getArticleController();
      //  assertThat(articleController1).isEqualTo(articleController2);
    }


    @Test
    public void ioc_Control__annotation_with_container(){
        List<String> controllerNames= Container.getAllControllerNames();
        //controllerNames.contains("home");
        //controllerNames.contains("article");

        System.out.println(controllerNames);
        assertThat(controllerNames).contains("home");
        assertThat(controllerNames).contains("article");
       // assertThat(controllerNames).contains("member");
    }


    @Test
    public void ioc_homeControl__SingleTon(){
        HomeController homeController1 = Container.getObj(HomeController.class);
        HomeController homeController2 = Container.getObj(HomeController.class);

        assertThat(homeController1).isEqualTo(homeController2);
    }

    @Test
    public void ioc__articleService() {
        ArticleService articleService = Container.getObj(ArticleService.class);
        System.out.println(articleService);
        assertThat(articleService).isNotNull();
    }

    @Test
    public void ioc__articleService__singleton() {
        ArticleService articleService1 = Container.getObj(ArticleService.class);
        ArticleService articleService2 = Container.getObj(ArticleService.class);
        System.out.println(articleService1);
        System.out.println(articleService2);
        assertThat(articleService2).isEqualTo(articleService1);
    }

    @Test
    public void ioc_createController__should_create_service_too(){
        ArticleController articleController = Container.getObj(ArticleController.class);
        HomeController homeController = Container.getObj(HomeController.class);
        // ArticleService articleService = articleController.getArticleServiceForTest();
        ArticleService articleService = Ut.reflection.getFieldValue(articleController, "articleService", null);
        HomeService homeService = Ut.reflection.getFieldValue(homeController,"homeService",null);
        System.out.println(articleService);
        assertThat(articleService).isNotNull();
        assertThat(homeService).isNull();
    }

    @Test
    public void ioc_createService__should_create_repo_too(){
        ArticleService articleService = Container.getObj(ArticleService.class);
        ArticleRepository articleRepository = Ut.reflection.getFieldValue(articleService, "articleRepository", null);
        System.out.println(articleRepository);
        assertThat(articleRepository).isNotNull();
    }


    @Test
    public void ControllerManager__scanMappings() {
        ControllerManager.init(); // 클래스를 강제로 로딩되게 하려는 목적
        // init() 비어있는데?? 흠?
    }

    @Test
    public void ControllerManager__라우트정보_개수() {
        Map<String, RouteInfo> routeInfos = ControllerManager.getRouteInfosForTest();

        assertThat(routeInfos.size()).isEqualTo(4);
    }

    @Test
    public void Ut__beforeFrom(){
        String path = "/usr/article/list/free";
        String actionPath= Ut.str.beforeFrom(path,"/",4);
        assertThat(actionPath).isEqualTo("/usr/article/list");
    }
}