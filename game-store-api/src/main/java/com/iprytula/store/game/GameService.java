package com.iprytula.store.game;

import com.iprytula.store.category.Category;
import com.iprytula.store.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
	private final GameRepository gameRepository;

	public PageResponse<Game> pagedResult(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(
			page,
			size,
			Sort.by(
				Sort.Direction.DESC,
				"title",
				"createTime"
			)
		);

		Page<Game> pagedResult = gameRepository.findAllByCategoryName("anyCategory", pageable);

		return PageResponse.<Game>builder()
			.content(pagedResult.getContent())
			.totalPages(pagedResult.getTotalPages())
			.totalElements(pagedResult.getTotalElements())
			.isLast(pagedResult.isLast())
			.isFirst(pagedResult.isFirst())
			.build();
	}

	public void queryByExample() {
		Game game = new Game();
		game.setTitle("witcher");
		Set<SupportedPlatforms> supportedPlatforms = new HashSet<>();
		supportedPlatforms.add(SupportedPlatforms.PC);
		game.setSupportedPlatforms(supportedPlatforms);

		ExampleMatcher matcher = ExampleMatcher.matchingAny()
			.withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			.withMatcher("supportedPlatforms", ExampleMatcher.GenericPropertyMatchers.contains().exact());

		Example<Game> example = Example.of(game, matcher);

		Optional<Game> myGame = gameRepository.findOne(example);
	}
}
