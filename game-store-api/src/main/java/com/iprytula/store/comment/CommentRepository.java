package com.iprytula.store.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Long countByGameId(Long gameId);
}
