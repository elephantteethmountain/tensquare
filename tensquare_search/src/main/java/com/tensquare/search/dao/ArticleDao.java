package com.tensquare.search.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tensquare.search.pojo.Article;


/**
 * 文章搜索dao
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String>{

    /**
     * 根据title或content模糊搜索
     */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
