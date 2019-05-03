package com.tensquare.recruit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Enterprise;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    /**
     * @Description 热门企业列表
     * @Date 2019/3/20 21:49
     * @Author 李梦楠
     */
    public List<Enterprise> findByIshot(String ishot);
}
