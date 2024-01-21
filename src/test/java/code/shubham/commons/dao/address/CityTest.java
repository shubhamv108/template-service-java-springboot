package code.shubham.commons.dao.address;

import code.shubham.commons.dao.address.entities.City;
import code.shubham.commons.dao.address.entities.Country;
import code.shubham.commons.dao.address.repositories.CityRepository;
import code.shubham.commons.dao.address.repositories.CountryRepository;
import code.shubham.test.AbstractSpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CityTest extends AbstractSpringBootTest {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CityRepository repository;

	@BeforeEach
	protected void setUp() {
		super.setUp();
		truncate("cities", "countries");
	}

	@AfterEach
	public void tearDown() {
		truncate("cities", "countries");
	}

	@Test
	void getId() {
		final Country bharat = this.countryRepository.save(Country.builder().name("Bharat").build());

		final City bengaluru = this.repository.save(City.builder().name("Bengaluru").countryId(bharat.getId()).build());

		final City pune = this.repository.save(City.builder().name("Pune").countryId(bharat.getId()).build());

		assertNotNull(bharat);
		assertNotNull(bharat.getId());
		assertNotNull(bengaluru);
		assertNotNull(bengaluru.getId());
		assertNotNull(pune);
		assertNotNull(pune.getId());
	}

}