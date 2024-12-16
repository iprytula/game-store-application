package com.iprytula.store.notification;

import com.iprytula.store.common.BaseEntity;
import com.iprytula.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
public class Notification extends BaseEntity {
	private String message;
	private String title;
	private NotificationLevel level;
	private NotificationStatus status;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
