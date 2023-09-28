package tech.gaul.noughtsncrosses.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NoughtsncrossesApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.
				run(NoughtsncrossesApiApplication.class, args);
	}

}
