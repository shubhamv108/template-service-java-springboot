package code.shubham.core.openai.proxies;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class OpenAIClient {

	@Value("{openai.organisation: org-AITu7WjEt7SFztL6pNNTLqKX}")
	private String organization;

	@Value("{OPENAI_API_KEY}")
	private String apiKey;

	@Value("openai.chatcompleteness: https://api.openai.com/v1/chat/completions")
	private String chatCompleteness;

	private final RestTemplate restTemplate;

	// public void invokeAPI(Map<String,>) throws URISyntaxException {
	// this.restTemplate.exchange(new URI(chatCompleteness), HttpMethod.GET, )
	// }

}
