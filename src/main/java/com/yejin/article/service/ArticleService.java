package com.yejin.article.service;

import com.yejin.annotation.Autowired;
import com.yejin.annotation.Service;
import com.yejin.article.dto.ArticleDto;
import com.yejin.article.repository.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleDto> getArticles() {


        return articleRepository.getArticles();

    }
}
