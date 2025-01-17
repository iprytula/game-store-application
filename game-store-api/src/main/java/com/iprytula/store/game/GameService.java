package com.iprytula.store.game;

import com.iprytula.store.category.CategoryRepository;
import com.iprytula.store.common.PageResponseDTO;
import com.iprytula.store.platform.AvailablePlatform;
import com.iprytula.store.platform.Platform;
import com.iprytula.store.platform.PlatformRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final GameMapper gameMapper;
	private final PlatformRepository platformRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	GameService(GameRepository gameRepository, GameMapper gameMapper, PlatformRepository platformRepository, CategoryRepository categoryRepository) {
		this.gameRepository = gameRepository;
		this.gameMapper = gameMapper;
		this.platformRepository = platformRepository;
		this.categoryRepository = categoryRepository;
	}

	public GameResponseDTO saveGame(GameRequestDTO gameRequest) {
		if (gameRepository.existsByTitle(gameRequest.getTitle())) {
			log.info("Game already exists: {}", gameRequest.getTitle());
			// TODO: create dedicated exception
			throw new RuntimeException("Game already exists");
		}

		final Set<AvailablePlatform> selectedAvailablePlatforms =
			gameRequest.getPlatforms().stream().map(AvailablePlatform::valueOf).collect(Collectors.toSet());
		final Set<Platform> platforms = platformRepository.findAllByAvailablePlatformIn(selectedAvailablePlatforms);

		if (platforms.size() != selectedAvailablePlatforms.size()) {
			log.warn("Platforms do not match, Received: {} - Stored: {}", selectedAvailablePlatforms, platforms);
			throw new RuntimeException("Platforms do not match");
		}

		if (!categoryRepository.existsById(gameRequest.getCategoryId())) {
			log.warn("Category does not exist: {}", gameRequest.getCategoryId());
			throw new RuntimeException("Category does not exist");
		}

		final Game game = gameMapper.toGame(gameRequest);
		game.setPlatforms(platforms);

		final Game savedGame = gameRepository.save(game);

		return GameResponseDTO.builder()
			.title(savedGame.getTitle())
			.id(savedGame.getId())
			.imageUrl(savedGame.getCoverPicture())
			.platforms(savedGame.getPlatforms().stream().map(platform -> platform.getAvailablePlatform().toString()).collect(Collectors.toSet()))
			.build();
	}

	public GameResponseDTO updateGame(Long gameId, GameRequestDTO gameRequest) {
		return null;
	}

	public GameResponseDTO uploadGameImage(MultipartFile file, Long gameId) {
		return null;
	}

	public PageResponseDTO<GameResponseDTO> findAllGames(Integer page, Integer size) {
		return null;
	}

	public GameResponseDTO deleteGame(Long gameId) {
		return null;
	}

}
