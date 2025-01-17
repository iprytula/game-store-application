package com.iprytula.store.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRequestDTO {
	private String title;
	private Long categoryId;
	private Set<String> platforms;
}
