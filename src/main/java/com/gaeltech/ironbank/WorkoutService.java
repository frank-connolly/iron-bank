package com.gaeltech.ironbank;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    Logger log = org.slf4j.LoggerFactory.getLogger(WorkoutService.class);

    private final WorkoutRepository repository;

    public WorkoutService(WorkoutRepository repository) {
        this.repository = repository;
    }

    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        log.info("Creating workout");
        var workoutEntity = WorkoutMapper.mapToEntity(workoutDto);
        var savedWorkoutEntity = repository.save(workoutEntity);
        return WorkoutMapper.mapToDto(savedWorkoutEntity);
    }

    public WorkoutDto getWorkout(Long id) {
        log.info("Getting workout by id: {}", id);
        var workoutEntity = repository.findById(id).orElseThrow();
        return WorkoutMapper.mapToDto(workoutEntity);
    }

    public Iterable<WorkoutDto> getAllWorkouts() {
        log.info("Getting all workouts");
        var workoutEntities = repository.findAll();
        return WorkoutMapper.mapToDtos(workoutEntities);
    }

    public void deleteWorkout(Long id) {
        log.info("Deleting workout by id: {}", id);
        repository.deleteById(id);
    }

    public WorkoutDto updateWorkout(Long id, WorkoutDto workoutDto) {
        log.info("Updating workout by id: {}", id);
        var workoutEntity = repository.findById(id).orElseThrow();
        workoutEntity.setStartTime(workoutDto.startTime());
        workoutEntity.setEndTime(workoutDto.endTime());
        workoutEntity.setNumberOfReps(workoutDto.numberOfReps());
        workoutEntity.setExerciseType(workoutDto.exerciseType());
        workoutEntity.setNotes(workoutDto.notes());
        var savedWorkoutEntity = repository.save(workoutEntity);
        return WorkoutMapper.mapToDto(savedWorkoutEntity);
    }
}
