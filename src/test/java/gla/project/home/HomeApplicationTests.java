package gla.project.home;

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
}
