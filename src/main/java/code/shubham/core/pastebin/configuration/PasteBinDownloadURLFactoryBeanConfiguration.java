package code.shubham.core.pastebin.configuration;

import code.shubham.commons.keystore.services.KeyService;
import code.shubham.commons.factories.DownloadURLFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PasteBinDownloadURLFactoryBeanConfiguration {

	@Value("${aws.default.region}")
	private String defaultRegion;

	@Value("${pastebin.bucket.name}")
	private String defaultBucket;

	@Value("${pastebin.cdn.domain}")
	private String cdnDomain;

	@Value("${pastebin.cdn.key.pair}")
	private String cdnKeyPair;

	@Value("${pastebin.cdn.key.type}")
	private String cdnKeyType;

	@Value("${pastebin.download.url.ttl.milliseconds}")
	private Long downloadURLTTLInMilliseconds;

	private final KeyService keyService;

	@Bean("PasteBinDownloadURLFactory")
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
