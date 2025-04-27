package phanes.replay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import phanes.replay.config.properties.*;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties({
		DomainProperties.class,
		JwtProperties.class,
		NaverProperties.class,
		GoogleProperties.class,
		KakaoProperties.class
})
public class ReplayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplayApplication.class, args);
	}
}