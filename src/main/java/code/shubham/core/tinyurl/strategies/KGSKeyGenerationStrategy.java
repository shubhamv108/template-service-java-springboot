package code.shubham.core.tinyurl.strategies;

import code.shubham.core.keygenerationclient.KeyGenerationClient;
import code.shubham.core.keygenerationcommons.strategies.IKeyGenerateStrategy;
import org.springframework.beans.factory.annotation.Autowired;

public class KGSKeyGenerationStrategy implements IKeyGenerateStrategy {

	private final KeyGenerationClient keyGenerationClient;

	@Autowired
	public KGSKeyGenerationStrategy(final KeyGenerationClient keyGenerationClient) {
		this.keyGenerationClient = keyGenerationClient;
	}

	@Override
	public String generate(String input, Integer keyLength) {
		return this.keyGenerationClient.poll();
	}

}
