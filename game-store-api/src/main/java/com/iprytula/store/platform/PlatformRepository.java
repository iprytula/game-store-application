package com.iprytula.store.platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
	Set<Platform> findAllByAvailablePlatformIn(Set<AvailablePlatform> availablePlatform);
}
