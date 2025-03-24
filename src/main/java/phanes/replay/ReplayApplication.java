package phanes.replay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReplayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplayApplication.class, args);

		System.out.println("헬로우 월드");
	}

}
