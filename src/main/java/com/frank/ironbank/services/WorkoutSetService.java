package com.frank.ironbank.services;

import com.frank.ironbank.models.WorkoutSet;

import java.util.List;
import java.util.Optional;

public interface WorkoutSetService {
    WorkoutSet save(WorkoutSet workoutSet);
    Optional<WorkoutSet> findById(Long id);
    List<WorkoutSet> findAll();
    void deleteById(Long id);
}