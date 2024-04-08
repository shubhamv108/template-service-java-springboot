package code.shubham.commons.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncPoolConfiguration {

	@Value("${async.pool.size.core: 2}")
	private Integer asyncCorePoolSize;

	@Value("${async.pool.size.max: 2}")
	private Integer asyncMaxPoolSize;

	@Value("${async.pool.capacity.queue: 500}")
	private Integer queueCapacity;

	@Bean
	public Executor taskExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(this.asyncCorePoolSize);
		executor.setMaxPoolSize(this.asyncMaxPoolSize);
		executor.setQueueCapacity(this.queueCapacity);
		executor.setThreadNamePrefix("TemplateServiceSpringBoot-AsyncPool-");
		executor.initialize();
		return executor;
	}

}
