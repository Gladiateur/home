package gla.project.home;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import gla.project.mybatisplus.mapper.UserMapper;
import gla.project.mybatisplus.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HomeApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setAge(3);
        user.setEmail("213213@safas.com");
        user.setName("gla");
        int a = userMapper.insert(user);//自动生成id且id回写
        System.out.println(a);
        System.out.println(user);
    }

    //测试乐观锁成功
    @Test
    public void testOptimisticLocker(){
        User user = userMapper.selectById(1);
        user.setName("aaaa");
        user.setAge(99);
        userMapper.updateById(user);
    }

    //测试乐观锁失败
    /*
    ==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=? WHERE id=? AND version=?
    ==> Parameters: cccc(String), 22(Integer), test1@baomidou.com(String), 3(Integer), 1(Long), 2(Integer)
    <==    Updates: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7db791df]
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@278fe428] was not registered for synchronization because synchronization is not active
    JDBC Connection [HikariProxyConnection@994825025 wrapping com.mysql.cj.jdbc.ConnectionImpl@1657b017] will not be managed by Spring
    ==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=? WHERE id=? AND version=?
    ==> Parameters: bbbb(String), 11(Integer), test1@baomidou.com(String), 3(Integer), 1(Long), 2(Integer)
    <==    Updates: 0
     */
    @Test
    public void testOptimisticLocker2(){
        User user = userMapper.selectById(1);
        user.setName("bbbb");
        user.setAge(11);

        //模拟另外一个线程插队操作
        User user2 = userMapper.selectById(1);
        user2.setName("cccc");
        user2.setAge(22);
        userMapper.updateById(user2);

        userMapper.updateById(user);//如果没有乐观锁就会覆盖插队线程的值
    }

    //测试分页
    @Test
    public void testPage(){
        Page<User> page = new Page<>(2,3);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
    }
}
