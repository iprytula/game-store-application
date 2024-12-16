package com.iprytula.store.game;

import com.iprytula.store.category.Category;
import com.iprytula.store.comment.Comment;
import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.wishlist.WishList;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game extends BaseEntity {
	private String title;
	private String description;

	@Enumerated(EnumType.STRING)
	private SupportedPlatforms supportedPlatforms;
	private String coverPicture;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "game")
	private List<Comment> comments;

	@ManyToMany
	@JoinTable(
		name = "game_wishlist",
		joinColumns = {
			@JoinColumn(name = "game_id")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "wishlist_id")
		}
	)
	private List<WishList> wishlists;

}
