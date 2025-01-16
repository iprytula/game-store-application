package com.iprytula.store.category;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.game.Game;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {
	private String name;
	private String description;

	@OneToMany(mappedBy = "category")
	private List<Game> games;
}
