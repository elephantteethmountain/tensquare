package com.tensquare.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;

import util.IdWorker;

/**
 *  文章service
 */
@Service
public class ArticleService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleDao articleDao;

    /**
     * 添加文章 -- 用于测试
     */
    public void add(Article article){
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    /**
     * 文章搜索
     */
    public Page<Article> search(String keywords, int page, int size){
        //1.根据keywords查询Elasticsearch数据库
        return articleDao.findByTitleOrContentLike(keywords,keywords, PageRequest.of(page-1,size));
    }
}
