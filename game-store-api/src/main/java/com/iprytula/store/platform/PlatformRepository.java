package com.iprytula.store.platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
	List<Platform> findAllByAvailablePlatformIn(List<AvailablePlatform> availablePlatform);
}
