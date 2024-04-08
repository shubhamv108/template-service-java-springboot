package code.shubham.test;

import code.shubham.TemplateServiceJavaSpringBootApplication;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.AccountIDContextHolder;
import code.shubham.commons.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Set;

@SpringBootTest(classes = TemplateServiceJavaSpringBootApplication.class,
		properties = { "newrelic.config.agent_enabled", "false", "NEWRELIC_API_KEY", "TEST_KEY" })
public abstract class AbstractSpringBootTest extends AbstractTest {

	protected TestKafkaConsumer kafkaConsumer;

	@Autowired
	protected KafkaPublisher kafkaPublisher;

	@Autowired
	private EntityManagerRepository entityManagerRepository;

	protected void setUp() {
		super.setUp();
		RoleContextHolder.set(Set.of());
		AccountIDContextHolder.set(null);
	}

	protected void truncate(final String... tables) {
		Arrays.stream(tables).forEach(this.entityManagerRepository::truncateTable);
	}

}
