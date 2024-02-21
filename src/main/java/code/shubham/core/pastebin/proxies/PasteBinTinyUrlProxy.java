package code.shubham.core.pastebin.proxies;

import code.shubham.core.tinyurlcommons.ITinyURLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasteBinTinyUrlProxy implements ITinyURLService {

	private final ITinyURLService tinyURLService;

	@Override
	public String create(final String url, final String alias, final Long ttlInSeconds) {
		return this.tinyURLService.create(url, alias, ttlInSeconds);
	}

	@Override
	public Optional<String> resolve(final String shortUrl, final Long accountId) {
		return this.tinyURLService.resolve(shortUrl, accountId);
	}

}