package test.testcode.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// 기존에 SpringBootApplication 위에 해당 어노테이션이 붙어있어서,
// WebMvcTest시 문제가 발생했음
// 그래서 이를 Configuration으로 주입받게 함
@Configuration
public class JpaAuditingConfig {

}
