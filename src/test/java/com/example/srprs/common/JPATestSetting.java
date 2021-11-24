package com.example.srprs.common;

import com.example.srprs.config.JPATestConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(JPATestConfig.class)
public class JPATestSetting {
}
