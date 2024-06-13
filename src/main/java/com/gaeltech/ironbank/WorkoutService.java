package com.gaeltech.ironbank;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutService {

    Logger log = LoggerFactory.getLogger(WorkoutService.class);

    private final WorkoutRepository repository;

    public WorkoutService(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Transactional
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

    public List<WorkoutDto> searchWorkouts(LocalDateTime startTime, LocalDateTime endTime, String text) {
        log.info("Searching workouts with startTime: {}, endTime: {}, text: {}", startTime, endTime, text);
        var workoutEntities = repository.searchWorkouts(startTime, endTime, text);
        return WorkoutMapper.mapToDtos(workoutEntities);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        log.info("Deleting workout by id: {}", id);
        repository.deleteById(id);
    }

    @Transactional
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
