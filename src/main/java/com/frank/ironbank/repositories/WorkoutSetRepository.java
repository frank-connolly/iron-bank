package com.frank.ironbank.repositories;

import com.frank.ironbank.models.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
}