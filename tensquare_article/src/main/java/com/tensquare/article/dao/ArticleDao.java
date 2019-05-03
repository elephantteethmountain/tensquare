package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tensquare.article.pojo.Article;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /**
     * 审核
     * @param id
     */
    @Modifying //修改操作必须加上
    @Query("update Article set state = '1' where id = ?1")
    public void exam(String id);

    /**
     * 点赞
     * @param id
     */
    @Modifying //coalesce 函数作用是将返回传入的参数中第一个非null的值
    @Query("update Article set thumbup = coalesce(thumbup,0) + 1  where id = ?1")
    public void thumbup(String id);
}
