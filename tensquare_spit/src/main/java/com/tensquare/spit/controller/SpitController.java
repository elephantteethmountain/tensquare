package com.tensquare.spit.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

/**
 * 吐槽Controller
 */
@RestController //@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用
@RequestMapping("/spit")
@CrossOrigin //处理跨域问题
public class SpitController {

    @Autowired
    private SpitService spitService;


    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 查询一个
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,@PathVariable String id){
        spit.setId(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据吐槽ID查询其评论
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageData = spitService.findByParentid(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 吐槽/评论点赞
     */
    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id){
        //模拟登录用户id
        String userid = "1001";

        //到redis查询该用户是否点赞过
        String flag = (String)redisTemplate.opsForValue().get("thumbup_"+userid +"_"+id);
        if(flag==null){
            //没有点赞过
            spitService.thumbup(id);

            //把该用户对该吐槽的点赞行为记录到redis
            redisTemplate.opsForValue().set("thumbup_"+userid+"_"+id,"1",1, TimeUnit.DAYS);

            return new Result(true,StatusCode.OK,"点赞成功");
        }else{
            //点赞过
            return new Result(false,StatusCode.REPEATE_ERROR,"你今天已经点赞过啦");
        }
    }

}
