package code.shubham.core.tinyurlcommons;

import java.util.Optional;

public interface ITinyURLService {

	String create(String url, String alias, Long ttlInSeconds);

	Optional<String> resolve(String shortUrl, Long accountId);

}
