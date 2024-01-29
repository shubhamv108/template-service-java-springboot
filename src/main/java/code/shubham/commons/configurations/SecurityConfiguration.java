package code.shubham.commons.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		return http.cors(Customizer.withDefaults())
			.authorizeHttpRequests(a -> a
				.requestMatchers("/*/api-docs", "/api-docs", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
						"/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
						"/swagger-ui/**", "/craft/swagger-ui/**", "/webjars/**", "/swagger-ui.html",
						"/*/api-docs/swagger-config", "/api-docs/swagger-config", "/internal/**", "/actuator/**")
				.permitAll())
			.authorizeHttpRequests(a -> a.requestMatchers("/**").fullyAuthenticated())
			.sessionManagement(a -> a.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.oauth2ResourceServer(a -> a.jwt(Customizer.withDefaults()))
			.cors(Customizer.withDefaults())
			.csrf(CsrfConfigurer::disable)
			.build();
	}

}