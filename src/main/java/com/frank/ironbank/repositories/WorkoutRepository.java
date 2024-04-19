package com.frank.ironbank.repositories;

import com.frank.ironbank.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}