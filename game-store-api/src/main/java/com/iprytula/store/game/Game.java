package com.iprytula.store.game;

import com.iprytula.store.category.Category;
import com.iprytula.store.comment.Comment;
import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.platform.Platform;
import com.iprytula.store.wishlist.WishList;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game extends BaseEntity {
	private String title;

	private String description;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private List<Platform> platforms;

	private String coverPicture;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "game", orphanRemoval = true)
	@OrderBy(value = "content")
	private List<Comment> comments;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
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

	public void addWishlist(WishList wishlist) {
		this.wishlists.add(wishlist);
		wishlist.getGames().add(this);
	}

	public void removeWishlist(WishList wishlist) {
		this.wishlists.remove(wishlist);
		wishlist.getGames().remove(this);
	}

	public void addPlatform(Platform platform) {
		this.platforms.add(platform);
		platform.getGames().add(this);
	}

	public void removePlatform(Platform platform) {
		this.platforms.remove(platform);
		platform.getGames().remove(this);
	}

}
