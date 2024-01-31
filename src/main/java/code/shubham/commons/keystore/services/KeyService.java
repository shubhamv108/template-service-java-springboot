package code.shubham.commons.keystore.services;

import code.shubham.commons.keystore.dao.entities.KeyStore;
import code.shubham.commons.keystore.dao.repositories.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class KeyService {

	private final KeyRepository repository;

	/**
	 * Gets the public key.
	 * @param keyType the key type
	 * @return the encryption key
	 */
	public byte[] getPublicKey(final String keyType) {
		KeyStore keyStore = this.repository.findByPurpose(keyType);
		return keyStore.getPublicKey().getEncoded();
	}

	public PrivateKey getPrivateKey(final String keyType) {
		final KeyStore keyStore = this.repository.findByPurpose(keyType);
		return keyStore.getPrivateKey();
	}

	public PrivateKey getPrivateKeyWithPSec(final String keyType) {
		final KeyStore keyStore = this.repository.findByPurpose(keyType);
		return keyStore.getPrivateKeyWithSpec();
	}

	public byte[] getPrivateKeyAsBytes(final String keyType) {
		final KeyStore keyStore = this.repository.findByPurpose(keyType);
		return keyStore.getPrivateKey().getEncoded();
	}

	public byte[] getSecretKeyAsBytes(final String keyType) {
		final KeyStore keyStore = this.repository.findByPurpose(keyType);
		return keyStore.getSecretKey().getEncoded();
	}

}
