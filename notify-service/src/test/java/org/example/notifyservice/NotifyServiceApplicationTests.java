package org.example.notifyservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.kafka.listener.auto-startup=false",
        "notification.mail.from=test@example.com"
})
class NotifyServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
