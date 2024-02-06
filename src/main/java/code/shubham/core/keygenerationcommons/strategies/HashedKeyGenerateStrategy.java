package code.shubham.core.keygenerationcommons.strategies;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.stream.IntStream;

public class HashedKeyGenerateStrategy implements IKeyGenerateStrategy {

	private final String algorithm;

	public HashedKeyGenerateStrategy(String algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public String generate(String input, Integer keyLength) {
		MessageDigest md5MessageDigest = null;
		try {
			md5MessageDigest = MessageDigest.getInstance(this.algorithm);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		final var digest = md5MessageDigest.digest(input.getBytes());
		final var encoded = Base64.getEncoder().encodeToString(digest);
		final var chrs = encoded.toCharArray();

		final var random = new Random();
		return IntStream.range(0, keyLength)
			.map(i -> chrs[random.nextInt(22) - 1])
			.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
			.toString();
	}

}
