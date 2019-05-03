package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tensquare.qa.pojo.Problem;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * @Description 最新问题列表
     * @Date 2019/3/21 11:48
     * @Author 李梦楠
     */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1 ) order by p.replytime desc")
    public Page<Problem> findNewListByLabelId(String labelid, Pageable pageable);

    /**
     * @Description 热门问题列表
     * @Date 2019/3/21 11:48
     * @Author 李梦楠
     */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1 ) order by p.reply desc ")
    public Page<Problem> findHotListByLabelId(String labelid, Pageable pageable);

    /**
     * @Description 等待回答问题列表
     * @Date 2019/3/21 11:48
     * @Author 李梦楠
     */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1 ) and p.reply = 0 order by p.createtime desc ")
    public Page<Problem> findWaitListByLabelId(String labelid, Pageable pageable);
}
