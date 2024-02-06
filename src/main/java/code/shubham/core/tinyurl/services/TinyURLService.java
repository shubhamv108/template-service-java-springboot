package code.shubham.core.tinyurl.services;

import code.shubham.commons.utils.StringUtils;
import code.shubham.core.tinyurl.dao.entities.ShortURL;
import code.shubham.core.tinyurl.dao.repositories.ShortUrlRepository;
import code.shubham.core.keygenerationcommons.strategies.IKeyGenerateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class TinyURLService {

	private final ShortUrlRepository repository;

	private IKeyGenerateStrategy keyGenerateStrategy;

	private IKeyGenerateStrategy randomCharacterKeyGenerateStrategy;

	private static final int DEFAULT_SHORT_URL_LENGTH = 7;

	@Autowired
	public TinyURLService(final ShortUrlRepository repository,
			@Qualifier("KeyGenerateStrategy") final IKeyGenerateStrategy keyGenerateStrategy,
			@Qualifier("RandomCharacterKeyGenerateStrategy") final IKeyGenerateStrategy randomCharacterKeyGenerateStrategy) {
		this.repository = repository;
		this.keyGenerateStrategy = keyGenerateStrategy;
		this.randomCharacterKeyGenerateStrategy = randomCharacterKeyGenerateStrategy;
	}

	public String create(final ShortURL shortURL) {
		ShortURL generatedShortURL = null;
		if (StringUtils.isNotEmpty(shortURL.getKeyName()))
			generatedShortURL = this.repository.save(shortURL);
		else
			generatedShortURL = this.generateShortUrl(shortURL);
		return generatedShortURL.getKeyName();
	}

	private ShortURL generateShortUrl(ShortURL shortURL) {
		String shortUrl = null;
		try {
			shortUrl = this.keyGenerateStrategy.generate(shortURL.getUrl(), DEFAULT_SHORT_URL_LENGTH);
		}
		catch (final Exception exception) {
			shortUrl = this.randomCharacterKeyGenerateStrategy.generate(shortURL.getUrl(), DEFAULT_SHORT_URL_LENGTH);
		}

		shortURL.setKeyName(shortUrl);
		try {
			shortURL = this.repository.save(shortURL);
		}
		catch (final DuplicateKeyException duplicateKeyException) {
			this.generateShortUrl(shortURL);
		}
		return shortURL;
	}

	public Optional<String> resolve(final String shortUrl, final Long accountId) {
		return this.repository.findURL(shortUrl, accountId);
	}

}
