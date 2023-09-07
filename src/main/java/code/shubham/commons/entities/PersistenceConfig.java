package code.shubham.commons.entities;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

	@org.springframework.context.annotation.Bean
	public org.springframework.data.domain.AuditorAware<Integer> auditorProvider() {
		return new code.shubham.commons.entities.UserIdAuditorAware();
	}

}
