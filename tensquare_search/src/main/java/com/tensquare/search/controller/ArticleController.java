package com.tensquare.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

/**
 * 文章controller
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章  -- 用于测试
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 文章的搜索（根据title或者content内容搜索）,支持分页
     */
    @RequestMapping("/search/{keywords}/{page}/{size}")
    public Result search(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData = articleService.search(keywords,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}
