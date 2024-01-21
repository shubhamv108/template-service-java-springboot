package code.shubham.commons.dao.address.repositories;

import code.shubham.commons.dao.address.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
