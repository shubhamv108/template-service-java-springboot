package code.shubham.commons.dao.address;

import code.shubham.commons.dao.address.entities.Country;
import code.shubham.commons.dao.address.repositories.CountryRepository;
import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = "newrelic.config.agent_enabled=false")
class CountryTest extends AbstractSpringBootTest {

	@Autowired
	private CountryRepository repository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("countries");
	}

	@AfterEach
	public void tearDown() {
		truncate("countries");
	}

	@Test
	void getId() {
		final Country bharat = this.repository.save(Country.builder().name("Bharat").build());

		final Country usa = this.repository.save(Country.builder().name("USA").build());

		assertNotNull(bharat);
		assertNotNull(bharat.getId());
		assertNotNull(usa);
		assertNotNull(usa.getId());
	}

}