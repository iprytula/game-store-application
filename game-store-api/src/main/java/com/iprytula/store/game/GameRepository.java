package com.iprytula.store.game;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
	Page<Game> findAllByCategoryId(UUID categoryId, Pageable pageable);

	Page<Game> findAllByCategoryName(String name, Pageable pageable);

	List<Game> findAll(Specification<Game> spec);

	// alternative with JPQL syntax
	@Query("""
		SELECT g FROM Game g
		INNER JOIN g.category c
		ON g.category.id = c.id
		WHERE c.name LIKE :name
	""")
	List<Game> findAllByCatName(@Param("name") String categoryName);

	// alternative with native query
	@Query(value = "SELECT * FROM category WHERE name LIKE :name ORDER BY name ASC", nativeQuery = true)
	List<Game> findAllByCategoryNameNativeQuery(@Param("name") String categoryName);
}
