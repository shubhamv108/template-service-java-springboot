package code.shubham.core.keygenerationcommons.strategies;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomCharacterKeyGenerateStrategy implements IKeyGenerateStrategy {

	private static final String CHARACTERS_STRING = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

	private static final char[] CHARACTERS = CHARACTERS_STRING.toCharArray();

	@Override
	public String generate(final String input, final Integer keyLength) {
		final char[] result = new char[keyLength];
		final Random random = new Random();
		IntStream.range(0, keyLength).forEach(i -> result[i] = CHARACTERS[random.nextInt(61 - 1) + 1]);
		return new String(result);
	}

}
