package code.shubham.commons.tree.dao.repositories;

import code.shubham.commons.tree.dao.entities.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer> {

	@Query(value = "WITH RECURSIVE cte(path, id, title) AS (SELECT cast(id as char), id, title FROM trees WHERE parent_id = ? UNION ALL SELECT CONCAT(parent.path,'/',child.id), child.id, child.title FROM cte parent INNER JOIN trees child WHERE child.parent_id = parent.id) SELECT * FROM cte",
			nativeQuery = true)
	List<Object[]> fetchPathsByParentId(Integer rootProductId);

	Optional<Tree> findByParentId(Integer parentId);

}
