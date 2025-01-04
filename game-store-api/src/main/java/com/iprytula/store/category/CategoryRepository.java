package com.iprytula.store.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

	List<Category> findAllByNameStartingWithIgnoreCaseOrderByNameAsc(String name);

	// alternative with JPQL syntax
	@Query("""
		SELECT c FROM Category c
		WHERE c.name LIKE lower(:name)
		ORDER BY c.name ASC
	""")
	List<Category> findAllByName(@Param("name") String categoryName);

	// alternative with named query
	@Query(name = "Category.findByName")
	List<Category> findAllWithNamedQuery(@Param("name") String categoryName);
}
