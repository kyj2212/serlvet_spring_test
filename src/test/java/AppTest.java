

import com.yejin.Container;
import com.yejin.article.controller.ArticleController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    void test_assertThat() {
        int rs = 10 + 20;
        assertThat(rs).isEqualTo(30);
    }
    @Test
    public void ioc_articleControl__Test1(){
        ArticleController articleController1 = Container.getArticleController();
        assertThat(articleController1).isNotNull();
    }
    @Test
    public void ioc_articleControl__SingleTon(){
        ArticleController articleController1 = Container.getArticleController();
        ArticleController articleController2 = Container.getArticleController();
        assertThat(articleController1).isEqualTo(articleController2);
    }



}