package code.shubham.core.document.configurations;

import code.shubham.commons.factories.DownloadURLFactory;
import code.shubham.commons.keystore.services.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DocumentDownloadURLFactoryBeanConfiguration {

	@Value("${aws.default.region}")
	private String defaultRegion;

	@Value("${documents.bucket.name}")
	private String defaultBucket;

	@Value("${documents.cdn.domain}")
	private String cdnDomain;

	@Value("${documents.cdn.key.pair}")
	private String cdnKeyPair;

	@Value("${documents.cdn.key.type}")
	private String cdnKeyType;

	@Value("${documents.download.url.ttl.milliseconds}")
	private Long downloadURLTTLInMilliseconds;

	private final KeyService keyService;

	@Bean("DocumentDownloadURLFactory")
	public DownloadURLFactory get() {
		return DownloadURLFactory.builder()
			.defaultRegion(defaultRegion)
			.defaultBucket(defaultBucket)
			.cdnDomain(cdnDomain)
			.cdnKeyPair(cdnKeyPair)
			.cdnKeyType(cdnKeyType)
			.keyService(keyService)
			.downloadURLTTLInMilliseconds(downloadURLTTLInMilliseconds)
			.build();
	}

}
