package code.shubham;

import code.shubham.commons.annotations.SpringBootApp;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApp
public class TemplateServiceJavaSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateServiceJavaSpringBootApplication.class, args);
	}

}
