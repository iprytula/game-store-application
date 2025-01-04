package com.iprytula.store.game;

import com.iprytula.store.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
	private final GameRepository gameRepository;

	public void something(UUID categoryId) {
		List<Game> games = gameRepository.findAllByCategoryId(categoryId);
	}


}
