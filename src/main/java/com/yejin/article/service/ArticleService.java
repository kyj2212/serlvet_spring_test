package com.yejin.article.service;

import com.yejin.annotation.Autowired;
import com.yejin.annotation.Service;
import com.yejin.article.repository.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
}
