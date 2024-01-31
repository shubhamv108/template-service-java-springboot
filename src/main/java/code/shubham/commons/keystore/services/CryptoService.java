package code.shubham.commons.keystore.services;

import code.shubham.CryptoUtil;
import code.shubham.commons.utils.StringUtils;
import code.shubham.hash.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoService {

	private final KeyService keyService;

	/**
	 * Time in milliseconds when captured auth value will be expired. default value 30
	 * minutes
	 */
	@Value("${auth.value.expiry.milli.seconds:1800000}")
	private long timeForCaptureAuthValueExpiry;

	/**
	 * flag to skip the check for captured auth value expiry time
	 */
	@Value("${skip.auth.value.expiry.milli.seconds.check:-1}")
	private int skipExpiryFlag;

	private final Map<String, String> authTypeEncryptionKeyMap = new HashMap<>();

	public String encryptUsingSecretKey(final String dataValue, final String authType) {
		String encryptionKey = this.authTypeEncryptionKeyMap.get(authType);
		byte[] secretKey = this.keyService.getSecretKeyAsBytes(encryptionKey);
		return new String(CryptoUtil.encryptUsingSecretKey(secretKey, dataValue.getBytes()));
	}

	public String decryptUsingSecretKey(final String dataValue, final String authType) {
		String encryptionKey = this.authTypeEncryptionKeyMap.get(authType);
		byte[] secretKey = this.keyService.getSecretKeyAsBytes(encryptionKey);
		return new String(CryptoUtil.decryptUsingSecretKey(secretKey, dataValue.getBytes()));
	}

	public String decryptUsingPrivateKey(final String encodedAuthValue, final String authType) {
		String encryptionKey = this.authTypeEncryptionKeyMap.get(authType);
		byte[] decodedAuthValue = HashUtil.decodeBase64(encodedAuthValue);
		byte[] privateKey = this.keyService.getPrivateKeyAsBytes(encryptionKey);
		return new String(CryptoUtil.decryptUsingPrivateKey(privateKey, decodedAuthValue));
	}

	public String parseAndGetAuthValue(final String decryptedAuthValue, final String authType) {
		String authValue = "";
		long authValueCaptureTimestamp = 0;

		if (StringUtils.isNotEmpty(decryptedAuthValue)) {
			String[] parts = decryptedAuthValue.split("_");
			if (parts.length > 1) {
				authValue = parts[1].trim();
			}
			if (parts.length > 0) {
				authValueCaptureTimestamp = Long.valueOf(parts[0].trim());
			}
		}

		if (this.skipExpiryFlag != -1 && authValueCaptureTimestamp > 0
				&& (System.currentTimeMillis() - authValueCaptureTimestamp > this.timeForCaptureAuthValueExpiry)) {
			log.error("" + authType + " capture very old (captured at : " + new Date(authValueCaptureTimestamp)
					+ " => returning blank value");
			authValue = "";
		}
		return authValue;
	}

}