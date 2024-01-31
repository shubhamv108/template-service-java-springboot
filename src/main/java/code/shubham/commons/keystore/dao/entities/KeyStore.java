package code.shubham.commons.keystore.dao.entities;

import code.shubham.commons.dao.base.entities.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "key_stores")
public class KeyStore extends BaseAbstractAuditableEntity {

	@Column(name = "alias")
	private String alias;

	@Lob
	@Column(name = "p12_key", nullable = false, columnDefinition = "BLOB")
	private byte[] key;

	@Column(name = "password")
	private String password;

	@Column(name = "type")
	private String type;

	@Column(name = "purpose", unique = true, nullable = false)
	private String purpose;

	public PrivateKey getPrivateKeyWithSpec() {
		try {
			final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(this.key);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePrivate(spec);
		}
		catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		ByteArrayInputStream inputStream = null;
		PrivateKey privateKey = null;
		try {
			final java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
			inputStream = new ByteArrayInputStream(this.key);
			ks.load(inputStream, this.password.toCharArray());
			privateKey = (PrivateKey) ks.getKey(this.alias, this.password.toCharArray());
		}
		catch (final Exception exception) {
			log.error("", exception);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (final Exception exception) {
					log.error("", exception);
				}
			}
		}
		return privateKey;
	}

	public PublicKey getPublicKey() {
		ByteArrayInputStream inputStream = null;
		PublicKey publicKey = null;
		try {
			final java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
			inputStream = new ByteArrayInputStream(this.key);
			ks.load(inputStream, this.password.toCharArray());
			publicKey = ks.getCertificate(this.alias).getPublicKey();
		}
		catch (final Exception exception) {
			log.error("", exception);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (final Exception exception) {
					log.error("", exception);
				}
			}
		}
		return publicKey;
	}

	public SecretKey getSecretKey() {
		ByteArrayInputStream inputStream = null;
		SecretKey secretKey = null;
		try {
			final java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
			inputStream = new ByteArrayInputStream(this.key);
			ks.load(inputStream, this.password.toCharArray());
			secretKey = (SecretKey) ks.getKey(this.alias, this.password.toCharArray());
		}
		catch (final Exception exception) {
			log.error("", exception);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (final Exception exception) {
					log.error("", exception);
				}
			}
		}
		return secretKey;
	}

}