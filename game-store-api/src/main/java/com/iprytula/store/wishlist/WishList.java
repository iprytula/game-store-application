package com.iprytula.store.wishlist;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.game.Game;
import com.iprytula.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WishList extends BaseEntity {

	private String name;

	@OneToOne(optional = false)
	private User user;

	@ManyToMany(mappedBy = "wishlists")
	private List<Game> games;

}
