package com.iprytula.store.game;

import com.iprytula.store.category.CategoryRepository;
import com.iprytula.store.comment.CommentRepository;
import com.iprytula.store.common.PageResponseDTO;
import com.iprytula.store.platform.AvailablePlatform;
import com.iprytula.store.platform.Platform;
import com.iprytula.store.platform.PlatformRepository;
import com.iprytula.store.wishlist.WishList;
import com.iprytula.store.wishlist.WishListRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final GameMapper gameMapper;
	private final PlatformRepository platformRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	GameService(
		GameRepository gameRepository,
		GameMapper gameMapper,
		PlatformRepository platformRepository,
		CategoryRepository categoryRepository,
		CommentRepository commentRepository,
		WishListRepository wishListRepository
	) {
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

		final List<AvailablePlatform> selectedAvailablePlatforms =
			gameRequest.getPlatforms().stream().map(AvailablePlatform::valueOf).toList();
		final List<Platform> platforms = platformRepository.findAllByAvailablePlatformIn(selectedAvailablePlatforms);

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
			.platforms(savedGame.getPlatforms().stream().map(platform -> platform.getAvailablePlatform().toString()).toList())
			.build();
	}

	public GameResponseDTO updateGame(Long gameId, GameRequestDTO gameRequest) {
		final Game game = gameRepository.findById(gameId)
			.orElseThrow(() -> new RuntimeException("Game not found"));

		if (!game.getTitle().equals(gameRequest.getTitle()) && gameRepository.existsByTitle(gameRequest.getTitle())) {
			log.info("Game already exists: {}", gameRequest.getTitle());
			throw new RuntimeException("Game already exists");
		}

		final List<AvailablePlatform> selectedPlatforms =
			gameRequest.getPlatforms().stream().map(AvailablePlatform::valueOf).toList();

		final List<Platform> platforms =
			platformRepository.findAllByAvailablePlatformIn(selectedPlatforms);
		final Set<Long> platformIds = platforms.stream().map(Platform::getId).collect(Collectors.toSet());

		List<Platform> currentPlatforms = game.getPlatforms();
		List<Platform> newPlatforms = platformRepository.findAllById(platformIds);

		List<Platform> platformsToAdd = new ArrayList<>(newPlatforms);
		platformsToAdd.removeAll(currentPlatforms);

		List<Platform> platformsToRemove = new ArrayList<>(currentPlatforms);
		platformsToRemove.removeAll(newPlatforms);

		for (Platform platform : platformsToAdd) {
			game.addPlatform(platform);
		}

		for (Platform platform : platformsToRemove) {
			game.removePlatform(platform);
		}

		return GameResponseDTO.builder()
			.title(game.getTitle())
			.id(game.getId())
			.imageUrl(game.getCoverPicture())
			.platforms(game.getPlatforms().stream().map(platform -> platform.getAvailablePlatform().toString()).toList())
			.build();
	}

	public GameResponseDTO uploadGameImage(MultipartFile file, Long gameId) {
		return null;
	}

	public PageResponseDTO<GameResponseDTO> findAllGames(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Game> gamesPage = gameRepository.findAll(pageable);
		List<GameResponseDTO> gameResponseDTOS = gamesPage.stream()
			.map(gameMapper::toGameResponse)
			.toList();

		return PageResponseDTO.<GameResponseDTO>builder()
			.content(gameResponseDTOS)
			.number(gamesPage.getNumber())
			.totalPages(gamesPage.getTotalPages())
			.totalElements(gamesPage.getTotalElements())
			.isFirst(gamesPage.isFirst())
			.isLast(gamesPage.isLast())
			.number(gamesPage.getNumber())
			.build();
	}

	@Transactional
	public GameResponseDTO deleteGame(Long gameId, Boolean confirm) {
		final Game gameToDelete = gameRepository.findById(gameId)
			.orElseThrow(() -> new RuntimeException("There is no game with such id"));
		final int wishListCount = gameToDelete.getWishlists().size();
		final int commentsCount = gameToDelete.getComments().size();
		final List<String> warnings = new ArrayList<>();

		if (commentsCount > 0) warnings.add("There are related comments for this game");
		if (wishListCount > 0) warnings.add("There are wishlists that are have this game in them");

		if (!warnings.isEmpty() && !confirm) {
			throw new RuntimeException("There are warnings about deleting this game");
		}

		gameToDelete.getWishlists().forEach(wishList -> wishList.removeGame(gameToDelete));

		gameRepository.save(gameToDelete);
		gameRepository.delete(gameToDelete);

		return gameMapper.toGameResponse(gameToDelete);
	}

}
