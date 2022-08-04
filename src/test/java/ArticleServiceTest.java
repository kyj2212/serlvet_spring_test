
import com.yejin.Container;
import com.yejin.article.dto.ArticleDto;
import com.yejin.article.service.ArticleService;
import com.yejin.mymap.MyMap;
import com.yejin.util.Ut;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ArticleServiceTest {


    private MyMap myMap;
    private ArticleService articleService;
    private static final int TEST_DATA_SIZE=6;

    @BeforeAll
    public void beforeAll() {

        // 이거 모든 테스트 실행 전에 한번 실행함.
        myMap=Container.getObj(MyMap.class);
        articleService=Container.getObj(ArticleService.class);
        // 테스트할때 넣기
//        MyMap myMap = Container.getObj(MyMap.class);
        myMap.setDevMode(true); // 처리되는 SQL 을 콘솔에 출력

    }


    @BeforeEach
    public void beforeEach() {
        truncateArticleTable();
        makeArticleTestData();
    }

    private void makeArticleTestData() {
       // MyMap myMap = Container.getObj(MyMap.class);
        IntStream.rangeClosed(1, TEST_DATA_SIZE).forEach(no -> {
            boolean isBlind = no > 3;
            String title = "제목%d".formatted(no);
            String body = "내용%d".formatted(no);

            myMap.run("""
                    INSERT INTO article
                    SET createdDate = NOW(),
                    modifiedDate = NOW(),
                    title = ?,
                    `body` = ?,
                    isBlind = ?
                    """, title, body, isBlind);
        });
    }


    private void truncateArticleTable() {
     //   MyMap myMap = Container.getObj(MyMap.class);
        myMap.run("TRUNCATE article"); //TRUNCATE TABLE 은 drop은 완전히 지우는거고, truncate는 그냥 내용만 날리기!!
    }

    @Test
    public void articleService_exit(){
//        ArticleService articleService = Container.getObj(ArticleService.class);
        assertThat(articleService).isNotNull();
    }

    @Test
    public void getArticles(){
//        ArticleService articleService = Container.getObj(ArticleService.class);

        List<ArticleDto> articleDtoList = articleService.getArticles();
        System.out.println(articleDtoList);
        assertThat(articleDtoList.size()).isEqualTo(TEST_DATA_SIZE);
    }
    @Test
    public void getArticleByID(){
//        ArticleService articleService = Container.getObj(ArticleService.class);
        ArticleDto articleDto = articleService.getArticleById(1);

        System.out.println(articleDto);
        assertThat(articleDto.getId()).isEqualTo(1L);
        assertThat(articleDto.getTitle()).isEqualTo("제목1");
        assertThat(articleDto.getBody()).isEqualTo("내용1");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isFalse();
        //articleService.write("제목 new","내용 new");
    }

    @Test
    public void getArticlesCount(){
//        ArticleService articleService = Container.getObj(ArticleService.class);
        long articlesCount = articleService.getArticlesCount();
        assertThat(articlesCount).isEqualTo(TEST_DATA_SIZE);
    }

    @Test
    public void write(){
//        ArticleService articleService = Container.getObj(ArticleService.class);
        long newArticleId = articleService.write("예진","예진예진",false);
        ArticleDto articleDto = articleService.getArticleById(newArticleId);

        assertThat(articleDto.getId()).isEqualTo(newArticleId);
        assertThat(articleDto.getTitle()).isEqualTo("예진");
        assertThat(articleDto.getBody()).isEqualTo("예진예진");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isFalse();

        //articleService.write("제목 new","내용 new");
    }

    @Test
    public void modify(){

        Ut.sleep(5000);

//        ArticleService articleService = Container.getObj(ArticleService.class);
        long newArticleId = articleService.modify(1,"예진","예진예진",false);
        ArticleDto articleDto = articleService.getArticleById(1);

        assertThat(articleDto.getId()).isEqualTo(1L);
        assertThat(articleDto.getTitle()).isEqualTo("예진");
        assertThat(articleDto.getBody()).isEqualTo("예진예진");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isFalse();

        // DB 에서 받아온 날짜랑 비교
        long diff = ChronoUnit.SECONDS.between(articleDto.getModifiedDate(), LocalDateTime.now());
        assertThat(diff).isLessThanOrEqualTo(1L);
    }

    @Test
    public void delete(){

       // Ut.sleep(5000);

//        ArticleService articleService = Container.getObj(ArticleService.class);
        articleService.delete(1);
      // List<ArticleDto> articleDtoList = articleService.getArticles();
        ArticleDto articleDto=articleService.getArticleById(1);
        System.out.println(articleDto);

        assertThat(articleDto).isNull();
    }


    @Test
    public void getPrevArticle2st__is1st(){

        ArticleDto articleDto = articleService.getPrevArticle(2);

        assertThat(articleDto.getId()).isEqualTo(1L);
        assertThat(articleDto.getTitle()).isEqualTo("제목1");
        assertThat(articleDto.getBody()).isEqualTo("내용1");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
       // assertThat(articleDto.isBlind()).isFalse(); // isBlind 는 체크하지 말자
    }

    @Test
    public void getNextArticle2st__del_id3__is_id4(){

        // id=3 이 없으면 4가 나와야지
        articleService.delete(3);

        ArticleDto articleDto = articleService.getNextArticle(2);


        assertThat(articleDto.getId()).isEqualTo(4L);
        assertThat(articleDto.getTitle()).isEqualTo("제목4");
        assertThat(articleDto.getBody()).isEqualTo("내용4");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        //assertThat(articleDto.isBlind()).isFalse(); // isBlind 는 체크하지 말자
    }

    @Test
    public void getPrevArticle1st__none(){

        ArticleDto articleDto = articleService.getPrevArticle(1);

        assertThat(articleDto).isNull();
        // assertThat(articleDto.isBlind()).isFalse(); // isBlind 는 체크하지 말자
    }
    @Test
    public void getNextArticleLast__none(){

        ArticleDto articleDto = articleService.getNextArticle(TEST_DATA_SIZE);

        assertThat(articleDto).isNull();
        // assertThat(articleDto.isBlind()).isFalse(); // isBlind 는 체크하지 말자
    }


}
