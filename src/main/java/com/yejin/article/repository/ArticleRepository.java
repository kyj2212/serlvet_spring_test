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

}
