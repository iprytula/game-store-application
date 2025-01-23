package com.iprytula.store.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameResponseDTO {
	private Long id;
	private String title;
	private List<String> platforms;
	private String imageUrl;
}
