package code.shubham.commons.utils;

import code.shubham.commons.CommonTestEventUtils;
import code.shubham.commons.workers.AbstractWorker;
import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MetricsLoggerTest extends AbstractSpringBootTest {

	@Autowired
	private MetricsLogger metricsLogger;

	@Value("${service.name}")
	private String serviceName;

	@Value("${service.module}")
	private String serviceModule;

	@Test
	void log() {
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/test");
		request.setAttribute("requestStartTimestamp", System.currentTimeMillis());

		this.metricsLogger.log(request);

		final String output = outContent.toString();
		assertTrue(output
			.contains("api.execution.duration service=" + serviceName + ",module=" + serviceModule + ",api=/test "));
	}

	@Test
	void test_Log_event() {
		this.metricsLogger.log(CommonTestEventUtils.getEmptyEvent(), System.currentTimeMillis(), AbstractWorker.class);

		final String output = outContent.toString();
		assertTrue(output.contains("event.handler.execution.duration service=" + serviceName + ",module="
				+ serviceModule + ",handler=AbstractWorker,type=type,name=name "));
	}

}