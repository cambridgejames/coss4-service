package cn.cambridge.hexohero.basic.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class LimitConfigTest {

    @Autowired
    private LimitConfig limitConfig;

    @Test
    void selectUserById() {
    }

    @Test
    void selectRoleById() {
    }

    @Test
    void selectPermissionById() {
    }

    @Test
    void selectRolesByIds() {
    }

    @Test
    void selectPermissionsByIds() {
    }

    @Test
    void testToString() {
        System.out.println(limitConfig.toString());
    }
}