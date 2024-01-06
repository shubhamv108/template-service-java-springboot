package code.shubham;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("code.shubham")
@SpringBootApplication
public class TemplateServiceJavaSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateServiceJavaSpringBootApplication.class, args);
	}

}
