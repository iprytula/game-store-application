package com.iprytula.store.game;

import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GameSpecifications {
	public static Specification<Game> byGameTitle(String gameTitle) {
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("gameTitle"), gameTitle);
	}

	public static Specification<Game> byGameSupportedPlatform(SupportedPlatforms platform) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("platform"), platform);
	}
}
