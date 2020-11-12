package easyon.dating.app;

import easyon.dating.app.controllers.AdminController;
import easyon.dating.app.controllers.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppApplicationTests {

    @Autowired
    private DemoController frontController;
    @Autowired
    private AdminController adminController;

    @Test
    void contextLoads() {
        assertThat(frontController).isNotNull();
        assertThat(adminController).isNotNull();
    }

}
