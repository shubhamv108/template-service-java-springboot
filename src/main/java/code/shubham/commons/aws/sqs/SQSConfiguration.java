package code.shubham.commons.aws.sqs;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.annotation.EnableSqs;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration
public class SQSConfiguration {

	@Value("${aws.default.region}")
	private String region;

	@Value("${AWS_ENDPOINT_URL_SQS}")
	private String sqsEndpointUrl;

	@Primary
	@Bean
	public AmazonSQSAsync amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder.standard()
			.withEndpointConfiguration(new EndpointConfiguration(this.sqsEndpointUrl, this.region))
			.build();
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync,
			SQSMessageConvertor messageConverter) {
		final QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
		queueMessagingTemplate.setMessageConverter(messageConverter);
		return queueMessagingTemplate;

	}

	@Bean
	public QueueMessageHandlerFactory queueMessageHandlerFactory(ObjectMapper objectMapper,
			AmazonSQSAsync amazonSQSAsync) {
		final MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
		messageConverter.setObjectMapper(objectMapper);
		messageConverter.setStrictContentTypeMatch(false);

		final QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
		factory.setAmazonSqs(amazonSQSAsync);

		final List<HandlerMethodArgumentResolver> resolvers = List
			.of(new PayloadMethodArgumentResolver(messageConverter, null, false));
		factory.setArgumentResolvers(resolvers);

		return factory;
	}

}