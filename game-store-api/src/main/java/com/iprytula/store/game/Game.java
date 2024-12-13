package com.iprytula.store.game;

import com.iprytula.store.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

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
}
