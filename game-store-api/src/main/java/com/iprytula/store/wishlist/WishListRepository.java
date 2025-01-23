package com.iprytula.store.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
	@Query("""
	SELECT COUNT(*)
	FROM WishList w
	JOIN w.games g
	WHERE g.id = :gameId
	""")
	Long countByGameId(Long gameId);

	@Query("""
	SELECT w
	FROM WishList w
	JOIN w.games g
	WHERE g.id = :gameId
	""")
	List<WishList> findAllByGameId(Long gameId);
}
