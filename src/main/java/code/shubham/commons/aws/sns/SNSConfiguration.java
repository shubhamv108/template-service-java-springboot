package code.shubham.commons.aws.sns;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SNSConfiguration {

	@Value("${aws.default.region}")
	private String region;

	@Value("${AWS_ENDPOINT_URL_SQS}")
	private String sqsEndpointUrl;

	@Bean
	public AmazonSNS amazonSNS() {
		return AmazonSNSClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(snsEndpoint, region))
			.build();
	}

}
