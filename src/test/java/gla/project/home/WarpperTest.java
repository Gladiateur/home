package gla.project.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import gla.project.mybatisplus.mapper.UserMapper;
import gla.project.mybatisplus.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WarpperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("name").isNotNull("email").ge("age",22);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
