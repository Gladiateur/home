package gla.project.redis.controller;

import gla.project.mybatisplus.mapper.UserMapper;
import gla.project.mybatisplus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test1")
    public void test(){
        stringRedisTemplate.opsForValue().set("springboot21","hello world");
        String val = stringRedisTemplate.opsForValue().get("springboot21");
        System.out.println(val);
    }

    //redis做缓存
    /*
    插入操作，直接操作数据库
     */
    @GetMapping("/test2")
    public void newItem(){
        User user = new User();
        user.setAge(3);
        user.setEmail("aaaa@safas.com");
        user.setName("aaa");
        userMapper.insert(user);//自动生成id且id回写
        System.out.println(user);
    }

    /*
    读取，先读redis,若有则返回，若无则读数据库，存到缓存中，再返回
    redis存<id,email>
     */
    @GetMapping("/test3/{id}")
    public void findItem(@PathVariable String id){
        String val = stringRedisTemplate.opsForValue().get(id);
        /* 若缓存中无则从数据库读取后存入缓存 */
        if(val == null){
            User user = userMapper.selectById(id);
            val = user.getEmail();
            stringRedisTemplate.opsForValue().set(id,val);
        }
        //返回值
        System.out.println(val);
    }

    /*
    更新，先删除redis中的缓存，再更新数据库的值不回写到缓存中。
     */
    @GetMapping("/test4/{id}/{email}")
    public void updateItem(@PathVariable Long id,@PathVariable String email){
        if(stringRedisTemplate.delete(id+"")){
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            userMapper.updateById(user);
        }else{
            System.out.println("redis删除key="+id+" 失败");
        }
    }
}
