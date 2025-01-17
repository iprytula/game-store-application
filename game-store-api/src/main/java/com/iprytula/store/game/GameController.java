package com.iprytula.store.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

	private final GameService gameService;

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping("/save")
	public ResponseEntity<GameResponseDTO> saveGameRequest(@RequestBody GameRequestDTO gameRequestDTO) {
		return ResponseEntity.ok().body(gameService.saveGame(gameRequestDTO));
	}

}
