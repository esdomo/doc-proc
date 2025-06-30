package org.kinetic.repository;

import org.kinetic.entity.TextStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
TextStatsRepository extends JpaRepository<TextStatsEntity, Long> {
}
