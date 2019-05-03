package com.tensquare.spit.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tensquare.spit.pojo.Spit;

/**
 * 吐槽dao
 */
public interface SpitDao extends MongoRepository<Spit,String>{

    /**
     * 根据parentid查询信息
     */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
