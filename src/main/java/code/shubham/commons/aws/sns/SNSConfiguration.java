package code.shubham.commons.aws.sns;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import io.awspring.cloud.messaging.endpoint.NotificationStatusHandlerMethodArgumentResolver;
import io.awspring.cloud.messaging.endpoint.config.NotificationHandlerMethodArgumentResolverFactoryBean;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@Configuration
public class SNSConfiguration implements WebMvcConfigurer {

	@Value("${aws.default.region}")
	private String region;

	@Value("${AWS_ENDPOINT_URL_SNS}")
	private String snsEndpointUrl;

	@Bean
	public AmazonSNS amazonSNS() {
		return AmazonSNSClientBuilder.standard()
			.withEndpointConfiguration(new EndpointConfiguration(this.snsEndpointUrl, this.region))
			.build();
	}

	@Bean
	public NotificationMessagingTemplate notificationMessagingTemplate(final AmazonSNS amazonSNS) {
		return new NotificationMessagingTemplate(amazonSNS);
	}

	@Bean
	public NotificationStatusHandlerMethodArgumentResolver notificationStatusHandlerMethodArgumentResolver() {
		return new NotificationStatusHandlerMethodArgumentResolver(amazonSNS());
	}

	@Bean
	public NotificationHandlerMethodArgumentResolverFactoryBean notificationHandlerMethodArgumentResolverFactoryBean() {
		return new NotificationHandlerMethodArgumentResolverFactoryBean(amazonSNS());
	}

	@Override
	@SneakyThrows
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(notificationStatusHandlerMethodArgumentResolver());
		resolvers.add(notificationHandlerMethodArgumentResolverFactoryBean().getObject());
	}

}
