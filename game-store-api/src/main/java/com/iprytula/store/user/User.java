package com.iprytula.store.user;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.game_demand.GameDemand;
import com.iprytula.store.notification.Notification;
import com.iprytula.store.wishlist.WishList;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User extends BaseEntity {
	private String firstName;
	private String lastName;
	private String email;
	private String profilePictureURL;

	@OneToOne(mappedBy = "user")
	private WishList wishList;

	@OneToMany(mappedBy = "user")
	private List<Notification> notifications;

	@OneToMany(mappedBy = "user")
	private List<GameDemand> gameRequests;
}
