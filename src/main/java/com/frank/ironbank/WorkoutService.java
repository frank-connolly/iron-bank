package com.frank.ironbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final Logger log = LoggerFactory.getLogger(WorkoutService.class);
    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public Workout createWorkout(Workout workout) {
        log.info("Creating workout: {}", workout);
        workoutRepository.saveWorkout(workout);
        log.info("Workout created: {}", workout);
        return workout;
    }

    public Optional<Workout> getWorkout(Long id) {
        log.info("Getting workout with id: {}", id);
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.getWorkout(id));
        log.info("Retrieved workout: {}", workout.orElse(null));
        return workout;
    }

    public Workout updateWorkout(Long id, Workout workout) {
        log.info("Updating workout with id: {} with data: {}", id, workout);
        Optional<Workout> existingWorkout = getWorkout(id);
        if (existingWorkout.isPresent()) {
            workoutRepository.updateWorkout(id, workout);
            log.info("Workout updated: {}", workout);
            return workout;
        } else {
            log.error("Workout with id: {} not found", id);
            throw new WorkoutNotFoundException("Workout with id: " + id + " not found");
        }
    }

    public void deleteWorkout(Long id) {
        log.info("Deleting workout with id: {}", id);
        Optional<Workout> existingWorkout = getWorkout(id);
        if (existingWorkout.isPresent()) {
            workoutRepository.deleteWorkout(id);
            log.info("Workout with id: {} deleted", id);
        } else {
            log.error("Workout with id: {} not found", id);
            throw new WorkoutNotFoundException("Workout with id: " + id + " not found");
        }
    }

    public List<Workout> listWorkouts() {
        log.info("Listing all workouts");
        List<Workout> workouts = workoutRepository.listWorkouts();
        log.info("Retrieved {} workouts", workouts.size());
        return workouts;
    }
}