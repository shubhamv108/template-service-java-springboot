package code.shubham;

import code.shubham.commons.annotations.SpringBootApp;
import code.shubham.commons.aws.CloudFrontUtils;
import code.shubham.commons.keystore.dao.entities.KeyStore;
import code.shubham.commons.keystore.dao.repositories.KeyRepository;
import code.shubham.encryption.keys.asymmetric.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
@SpringBootApp
public class TemplateServiceJavaSpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TemplateServiceJavaSpringBootApplication.class, args);
	}

	@Autowired
	private KeyRepository keyRepository;

	@Override
	public void run(String... args) throws Exception {
		// final PrivateKey privateKey =
		// CloudFrontUtils.readPrivateKeyFromFile("../private.pem");
		// final KeyStore entity =
		// this.keyRepository.findByPurpose("DOCUMENTS-CDN-CLOUD_FRONT-PRIVATE_KEY");
		// if (entity == null)
		// this.keyRepository.save(KeyStore.builder()
		// .key(privateKey.getEncoded())
		// .purpose("DOCUMENTS-CDN-CLOUD_FRONT-PRIVATE_KEY")
		// .build());
		// System.out.println("Key algorithm: " + privateKey.getAlgorithm());
	}

}
