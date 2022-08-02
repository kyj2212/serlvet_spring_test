

import com.yejin.Container;
import com.yejin.article.controller.ArticleController;
import com.yejin.home.controller.HomeController;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        HomeController homeController1 = Container.getHomeController();
        HomeController homeController2 = Container.getHomeController();

        assertThat(homeController1).isEqualTo(homeController2);
    }

}