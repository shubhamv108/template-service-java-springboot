package code.shubham.core.document.services.factories;

import code.shubham.commons.aws.CloudFrontUtils;
import code.shubham.commons.aws.S3Utils;
import code.shubham.commons.enums.DownloadURLSource;
import code.shubham.commons.keystore.services.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DocumentDownloadURLFactory {

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

	public String get(final DownloadURLSource source, final String key) {
		switch (source) {
			case S3 -> {
				return S3Utils.createPresignedGetUrl(this.defaultRegion, this.defaultBucket, key,
						this.downloadURLTTLInMilliseconds);
			}
			case CLOUD_FRONT -> {
				return CloudFrontUtils.signedURL(this.cdnDomain, key, this.cdnKeyPair,
						this.keyService.getPrivateKeyWithPSec(this.cdnKeyType),
						new Date(System.currentTimeMillis() + this.downloadURLTTLInMilliseconds));
			}
		}

		return null;
	}

}
