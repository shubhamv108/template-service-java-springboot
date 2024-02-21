package code.shubham.core.tinyurl.services;

import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.utils.StringUtils;
import code.shubham.core.tinyurl.dao.entities.ShortURL;
import code.shubham.core.tinyurl.dao.repositories.ShortUrlRepository;
import code.shubham.core.keygenerationcommons.strategies.IKeyGenerateStrategy;
import code.shubham.core.tinyurlcommons.ITinyURLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class TinyURLService implements ITinyURLService {

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

	@Override
	public String create(final String url, final String alias, final Long ttlInSeconds) {
		final ShortURL shortURL = ShortURL.builder()
			.url(url)
			.accountId(AccountIDContextHolder.get())
			.keyName(alias)
			.expiryAt(Optional.ofNullable(ttlInSeconds)
				.map(ttl -> new Date(System.currentTimeMillis() + (ttl * 1000)))
				.orElse(null))
			.build();
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

	@Override
	public Optional<String> resolve(final String shortUrl, final Long accountId) {
		return this.repository.findURL(shortUrl, accountId);
	}

}
