package code.shubham.commons.factories;

import code.shubham.commons.aws.CloudFrontUtils;
import code.shubham.commons.aws.S3Utils;
import code.shubham.commons.enums.DownloadURLSource;
import code.shubham.commons.keystore.services.KeyService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class DownloadURLFactory {

	private String defaultRegion;

	private String defaultBucket;

	private String cdnDomain;

	private String cdnKeyPair;

	private String cdnKeyType;

	private Long downloadURLTTLInMilliseconds;

	@Autowired
	private KeyService keyService;

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
