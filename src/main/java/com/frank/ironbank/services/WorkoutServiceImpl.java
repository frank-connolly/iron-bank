package com.frank.ironbank.services;

import com.frank.ironbank.repositories.WorkoutRepository;
import com.frank.ironbank.models.Workout;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout save(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return workoutRepository.findById(id);
    }

    @Override
    public List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        workoutRepository.deleteById(id);
    }
}
