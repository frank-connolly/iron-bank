package com.frank.ironbank.services;

import com.frank.ironbank.repositories.WorkoutSetRepository;
import com.frank.ironbank.models.WorkoutSet;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutSetServiceImpl implements WorkoutSetService {

    private final WorkoutSetRepository workoutSetRepository;

    @Autowired
    public WorkoutSetServiceImpl(WorkoutSetRepository workoutSetRepository) {
        this.workoutSetRepository = workoutSetRepository;
    }

    @Override
    public WorkoutSet save(WorkoutSet workoutSet) {
        return workoutSetRepository.save(workoutSet);
    }

    @Override
    public Optional<WorkoutSet> findById(Long id) {
        return workoutSetRepository.findById(id);
    }

    @Override
    public List<WorkoutSet> findAll() {
        return workoutSetRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        workoutSetRepository.deleteById(id);
    }
}