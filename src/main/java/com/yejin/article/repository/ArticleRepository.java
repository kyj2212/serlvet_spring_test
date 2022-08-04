package com.yejin.article.repository;

import com.yejin.annotation.Autowired;
import com.yejin.annotation.Repository;
import com.yejin.article.dto.ArticleDto;
import com.yejin.mymap.MyMap;
import com.yejin.mymap.SecSql;

import java.util.List;

@Repository
public class ArticleRepository {
    @Autowired
    private MyMap myMap;
    //private List<ArticleDto> articleDtoList;

    public List<ArticleDto> getArticles() {
        System.out.println("repo : ");
        System.out.println("Mymap in repo is null? "+myMap);
        SecSql sql = myMap.genSecSql();
        System.out.println("genSecSql complete");
        sql
                .append("SELECT *")
                .append("FROM article")
                .append("ORDER BY id DESC");
        System.out.println("repo articleDtos : "+ sql.selectRows(ArticleDto.class));
        return sql.selectRows(ArticleDto.class);
    }
    public ArticleDto getArticleById(long id) {

        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT *")
                .append("FROM article")
                .append("WHERE id=?",id)
               // .append("WHERE id=1")
                .append("ORDER BY id DESC");
        System.out.println("repo articleDtos : "+ sql.selectRows(ArticleDto.class));
        return sql.selectRow(ArticleDto.class);
    }
    public ArticleDto getArticleById_yejin(long id) {
        // 매번 getArticles() 를 하는게 맞는가?
        // 기존에는 영속성을 위해 static으로 dtoList를 repo에서 유지했는데,
        // 이제 db 에서 가져오니까 매번 db에서 select 해서 가져와야 하는 것이 아닐지?
        List<ArticleDto> articleDtoList=getArticles();
        for(ArticleDto article :  articleDtoList){
            if(article.getId()==id)
                return article;
        }
        return null;
    }

    public long getArticlesCount() {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT COUNT(*)")
                .append("FROM article");
       // System.out.println("count : "+ sql.selectLong());
        return sql.selectLong();
    }

    public long write(String title, String body, boolean isBlind) {

        SecSql sql = myMap.genSecSql();

        sql
                .append("INSERT INTO article")
                .append("SET createdDate = NOW()")
                .append(", modifiedDate = NOW()")
                .append(", title = ?",title)
                .append(", body = ?",body)
                .append(", isBlind = ?",isBlind);
        // System.out.println("count : "+ sql.selectLong());
      //  System.out.println(sql.selectRows(ArticleDto.class));
       return sql.insert();
       // sql.delete();
/*        sql
                .append("SELECT id")
                .append("FROM article")
                .append("ORDER BY DESC")
                .append("LIMIT ?",1);
        System.out.println(sql.selectLong());
        return sql.selectLong();*/
        //return sql.selectRows(ArticleDto.class);
    }

    public long modify(int id, String title, String body, boolean isBlind) {
        SecSql sql = myMap.genSecSql();

        sql
                .append("UPDATE article")
                .append("SET title = ?", title)
                .append(", body = ?", body)
                .append(", isBlind = ?", isBlind)
                .append(", modifiedDate = NOW()")
                .append("WHERE id = ?", id);

        // System.out.println("count : "+ sql.selectLong());
        //  System.out.println(sql.selectRows(ArticleDto.class));
        return sql.update();
    }

    public long delete(long id) {
        SecSql sql = myMap.genSecSql();

        sql
                .append("DELETE FROM article")
                .append("WHERE id = ?",id);

        // System.out.println("count : "+ sql.selectLong());
        //  System.out.println(sql.selectRows(ArticleDto.class));
        return sql.delete();
    }
}
