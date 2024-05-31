package com.gaeltech.ironbank;

import org.slf4j.Logger;

import java.util.List;

public class WorkoutMapper {

    static Logger log = org.slf4j.LoggerFactory.getLogger(WorkoutMapper.class);

    private WorkoutMapper() {
    }

    public static WorkoutEntity mapToEntity(WorkoutDto workoutDto) {
        log.info("Mapping WorkoutDto to WorkoutEntity");
        return new WorkoutEntity(
                null,
                workoutDto.startTime(),
                workoutDto.endTime(),
                workoutDto.numberOfReps(),
                workoutDto.exerciseType(),
                workoutDto.notes()
        );
    }

    public static WorkoutDto mapToDto(WorkoutEntity workoutEntity) {
        log.info("Mapping WorkoutEntity to WorkoutDto");
        return new WorkoutDto(
                workoutEntity.getId(),
                workoutEntity.getStartTime(),
                workoutEntity.getEndTime(),
                workoutEntity.getNumberOfReps(),
                workoutEntity.getExerciseType(),
                workoutEntity.getNotes()
        );
    }

    public static List<WorkoutDto> mapToDtos(List<WorkoutEntity> workoutEntities) {
        log.info("Mapping WorkoutEntities to WorkoutDtos");
        return workoutEntities.stream()
                .map(WorkoutMapper::mapToDto)
                .toList();
    }

    public static List<WorkoutEntity> mapToEntities(List<WorkoutDto> workoutDtos) {
        log.info("Mapping WorkoutDtos to WorkoutEntities");
        return workoutDtos.stream()
                .map(WorkoutMapper::mapToEntity)
                .toList();
    }
}
