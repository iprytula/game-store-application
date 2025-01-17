package com.iprytula.store.game_demand;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GameDemand extends BaseEntity {
	private String title;

	@Enumerated(EnumType.STRING)
	private DemandStatus status;

	@ManyToOne(optional = false)
	private User user;
}
