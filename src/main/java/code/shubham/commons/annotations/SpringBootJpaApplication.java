package code.shubham.commons.annotations;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.TYPE)
@SpringBootApplication
@ComponentScan("code.shubham")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public @interface SpringBootJpaApplication {

}
