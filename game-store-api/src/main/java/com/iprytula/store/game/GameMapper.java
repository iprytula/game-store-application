package com.iprytula.store.game;

import com.iprytula.store.category.Category;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
	public Game toGame(GameRequestDTO gameRequest) {
		return Game.builder()
			.title(gameRequest.getTitle())
			.category(Category.builder().id(gameRequest.getCategoryId()).build())
			.build();
	}

	public GameResponseDTO toGameResponse(Game game) {
		return GameResponseDTO.builder()
			.id(game.getId())
			.title(game.getTitle())
//			FIXME set the CDN URL
			.imageUrl("FIXME")
			.platforms(
				game.getPlatforms().stream()
					.map(platform -> platform.getAvailablePlatform().name())
					.toList()
			)
			.build();
	}
}
