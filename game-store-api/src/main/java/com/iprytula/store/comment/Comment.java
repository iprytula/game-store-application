package com.iprytula.store.comment;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {
	private String content;

	@ManyToOne
	@JoinColumn(name = "game_id", nullable = false)
	private Game game;
}
