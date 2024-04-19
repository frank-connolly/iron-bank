package com.frank.ironbank.services;

import com.frank.ironbank.models.Workout;

import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    Workout save(Workout workout);
    Optional<Workout> findById(Long id);
    List<Workout> findAll();
    void deleteById(Long id);
}