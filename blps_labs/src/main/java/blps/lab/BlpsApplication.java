package blps.lab;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class BlpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlpsApplication.class, args);
	}


}
