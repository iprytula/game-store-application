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
@NamedQuery(
	name = "Category.findByName",
	query = """
		SELECT c FROM Category c
		WHERE c.name LIKE lower(:name)
		ORDER BY c.name ASC
	"""
)
public class Category extends BaseEntity {
	private String name;
	private String description;

	@OneToMany(mappedBy = "category")
	private List<Game> games;
}
