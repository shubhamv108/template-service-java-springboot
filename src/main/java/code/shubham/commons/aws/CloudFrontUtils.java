package code.shubham.commons.aws;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.model.KeyPairIds;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Slf4j
public class CloudFrontUtils {

	public static String signedURL(final String distributionDomain, final String s3ObjectKey, final String keyPairId,
			final PrivateKey privateKey, final Date dateLessThan) {
		return CloudFrontUrlSigner.getSignedURLWithCannedPolicy("https://" + distributionDomain + "/" + s3ObjectKey,
				keyPairId, privateKey, dateLessThan);
	}

	public static String keyPair(final String distributionDomain, final String s3ObjectKey, final String keyPairId,
			final PrivateKey privateKey, final Date dateLessThan) {
		return CloudFrontUrlSigner.getSignedURLWithCannedPolicy("https://" + distributionDomain + "/" + s3ObjectKey,
				keyPairId, privateKey, dateLessThan);
	}

	public static PrivateKey readPrivateKeyFromFile(final String filePath)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Security.addProvider(new BouncyCastleProvider());
		File file = new File(filePath);
		FileInputStream fileStream = new FileInputStream(file);

		PrivateKey var10;
		try {
			DataInputStream dataStream = new DataInputStream(fileStream);

			try {
				byte[] keyBytes = new byte[(int) file.length()];
				dataStream.readFully(keyBytes);
				String temp = new String(keyBytes, "UTF-8");
				String header = temp.replace("-----BEGIN PRIVATE KEY-----", "");
				header = header.replace("-----END PRIVATE KEY-----", "");
				header.replace("\n", "");
				byte[] decoded = java.util.Base64.getMimeDecoder().decode(header);
				PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
				KeyFactory kf = KeyFactory.getInstance("RSA");
				var10 = kf.generatePrivate(spec);
			}
			catch (Throwable var13) {
				try {
					dataStream.close();
				}
				catch (Throwable var12) {
					var13.addSuppressed(var12);
				}
				log.error("", var13);
				throw var13;
			}

			dataStream.close();
		}
		catch (Throwable var14) {
			try {
				fileStream.close();
			}
			catch (Throwable var11) {
				var14.addSuppressed(var11);
			}
			log.error("", var14);
			throw var14;
		}
		return var10;
	}

}
