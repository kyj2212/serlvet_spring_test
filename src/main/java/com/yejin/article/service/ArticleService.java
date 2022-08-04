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

    public ArticleDto getArticleById(long id) {
        return articleRepository.getArticleById(id);
    }

    public long getArticlesCount() {
        return articleRepository.getArticlesCount();
    }

    public long write(String title, String body, boolean isBlind) {
        return articleRepository.write(title,body,isBlind);
    }

    public long modify(int id, String title, String body, boolean isBlind) {
        return articleRepository.modify(id,title,body,isBlind);
    }

    public long delete(long id) {
        return articleRepository.delete(id);
    }

    public ArticleDto getBeforeArticleById(long id) {
        return articleRepository.getBeforeArticleByid(id);
    }
    public ArticleDto getNextArticleById(long id) {
        return articleRepository.getNextArticleByid(id);
    }
}
