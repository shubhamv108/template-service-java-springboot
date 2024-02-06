package code.shubham.core.keygenerationcommons.configurations;

import code.shubham.core.keygenerationclient.KeyGenerationClient;
import code.shubham.core.keygenerationcommons.strategies.IKeyGenerateStrategy;
import code.shubham.core.tinyurl.strategies.KGSKeyGenerationStrategy;
import code.shubham.core.keygenerationcommons.strategies.RandomCharacterKeyGenerateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyBeans {

	@Bean("KeyGenerateStrategy")
	@Autowired
	public IKeyGenerateStrategy keyGenerateStrategy(final KeyGenerationClient keyGenerationClient) {
		return new KGSKeyGenerationStrategy(keyGenerationClient);
	}

	@Bean("RandomCharacterKeyGenerateStrategy")
	@Autowired
	public IKeyGenerateStrategy randomKeyGenerateStrategy() {
		return new RandomCharacterKeyGenerateStrategy();
	}

}