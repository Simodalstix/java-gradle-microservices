package com.macro.mall.search;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.elasticsearch.uris=http://localhost:9200",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class MallSearchApplicationTests {

    @Test
    void contextLoads() {
        // Spring Boot application context loads successfully
    }

}
