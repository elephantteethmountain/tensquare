package com.tensquare.spit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;

import util.IdWorker;

/**
 * 吐槽service
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 查询一个
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 添加吐槽/添加评论
     */
    public void add(Spit spit){
        spit.setId(idWorker.nextId()+"");
        spitDao.save(spit);

        //判断该数据是否为评论
        if(spit.getParentid()!=null && !spit.getParentid().equals("")){
            //修改该评伦对应的吐槽的comment，加1

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment",1);

            mongoTemplate.updateFirst(query,update,"spit");
        }


    }

    /**
     * 修改
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 根据吐槽id查询其下面的评论
     */
    public Page<Spit> findByParentid(String parentid,int page,int size){
        return spitDao.findByParentid(parentid,PageRequest.of(page-1,size));
    }

    //方案一: 效率相对比较低（不建议）
    /**
     * 吐槽（评论）点赞
     */
   /* public void thumbup(String id){
        //1.根据id查询该吐槽数据
        Spit spit = findById(id);

        //2.修改点赞数
        spit.setThumbup(spit.getThumbup()+1);

        //3.保存修改记录
        update(spit);
    }*/


    @Autowired
    private MongoTemplate mongoTemplate;

    //方案二： 只需要修改thumbp字段值
    public void thumbup(String id){
        //1.构建条件
        Query query = new Query();
        //Criteria.where("_id").is(id): 类似于 where _id = xxx   {"_id":xxx}
        query.addCriteria(Criteria.where("_id").is(id));

        //2.构建修改对象
        Update update = new Update();
        // {$inc:{"thumbuup",1}}
        update.inc("thumbup",1);

        //3.基于条件进行修改
        // db.spit.update({{"_id":xxx}},{$inc:{"thumbuup",1}})
        mongoTemplate.updateFirst(query,update,"spit");
    }


}
