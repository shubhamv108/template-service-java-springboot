package code.shubham.core.keygenerationclient;

import code.shubham.core.keygenerationcommons.service.IKeyGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeyGenerationClient {

	private final IKeyGenerationService keyGenerationService;

	public KeyGenerationClient(final IKeyGenerationService keyGenerationService) {
		this.keyGenerationService = keyGenerationService;
	}

	// @Value("${host.keygeneration:127.0.0.1}")
	// private String host = "127.0.0.1";
	//
	// @Value("${port.authorization: #{8091}}")
	// private Integer port = 8084;
	// private final String baseUrl;
	// private static final String CONTEXT_PATH = "/v1/keygeneration";
	//
	// private final HttpClientUtils httpClientUtils;
	//
	// @Autowired
	// public KeyGenerationClient(final HttpClientUtils httpClientUtils) {
	// this.httpClientUtils = httpClientUtils;
	// this.baseUrl = "http://" + this.host + ":" + this.port + CONTEXT_PATH;
	// }
	//
	// public String poll() {
	// HttpRequest request = this.httpClientUtils.createRequest(
	// "GET",
	// this.baseUrl + "/",
	// null,
	// null);
	// return this.httpClientUtils.sendRequest(request, String.class, "KeyGeneration");
	// }

	public String poll() {
		return this.keyGenerationService.poll();
	}

}
