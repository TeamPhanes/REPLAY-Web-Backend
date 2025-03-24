package REPLAY_Web_Backend.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);

		System.out.println("헬로우 월드");
	}

}
