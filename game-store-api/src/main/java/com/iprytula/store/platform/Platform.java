package com.iprytula.store.platform;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Platform extends BaseEntity {
	@Enumerated(EnumType.STRING)
	private AvailablePlatform availablePlatform;
	@ManyToMany(mappedBy = "platforms")
	private List<Game> games;
}
