package com.gaeltech.ironbank;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WorkoutMapperTest {

    @Test
    void mapToEntityTest() {
        var workoutDto = createTestWorkoutDto();
        var workoutEntity = WorkoutMapper.mapToEntity(workoutDto);

        assertThat(workoutEntity).isNotNull();
        assertThat(workoutEntity.getStartTime()).isEqualTo(workoutDto.startTime());
        assertThat(workoutEntity.getEndTime()).isEqualTo(workoutDto.endTime());
        assertThat(workoutEntity.getNumberOfReps()).isEqualTo(workoutDto.numberOfReps());
        assertThat(workoutEntity.getExerciseType()).isEqualTo(workoutDto.exerciseType());
        assertThat(workoutEntity.getNotes()).isEqualTo(workoutDto.notes());
    }

    @Test
    void mapToDtoTest() {
        var workoutEntity = createTestWorkoutEntity();
        var workoutDto = WorkoutMapper.mapToDto(workoutEntity);

        assertThat(workoutDto).isNotNull();
        assertThat(workoutDto.startTime()).isEqualTo(workoutEntity.getStartTime());
        assertThat(workoutDto.endTime()).isEqualTo(workoutEntity.getEndTime());
        assertThat(workoutDto.numberOfReps()).isEqualTo(workoutEntity.getNumberOfReps());
        assertThat(workoutDto.exerciseType()).isEqualTo(workoutEntity.getExerciseType());
        assertThat(workoutDto.notes()).isEqualTo(workoutEntity.getNotes());
    }

    @Test
    void mapToDtosTest() {
        var workoutEntity1 = createTestWorkoutEntity();
        var workoutEntity2 = createTestWorkoutEntity();
        var workoutEntities = Arrays.asList(workoutEntity1, workoutEntity2);

        var workoutDtos = WorkoutMapper.mapToDtos(workoutEntities);

        assertThat(workoutDtos).isNotNull().hasSize(2);
    }

    @Test
    void mapToEntitiesTest() {
        var workoutDto1 = createTestWorkoutDto();
        var workoutDto2 = createTestWorkoutDto();
        var workoutDtos = Arrays.asList(workoutDto1, workoutDto2);

        var workoutEntities = WorkoutMapper.mapToEntities(workoutDtos);

        assertThat(workoutEntities).isNotNull().hasSize(2);
    }

    private WorkoutDto createTestWorkoutDto() {
        return new WorkoutDto(
                null,
                LocalDateTime.of(2024, 5, 30, 8, 0),
                LocalDateTime.of(2024, 5, 30, 9, 0),
                10,
                ExerciseType.BENCH_PRESS,
                "Test workout"
        );
    }

    private WorkoutEntity createTestWorkoutEntity() {
        return new WorkoutEntity(
                null,
                LocalDateTime.of(2024, 5, 30, 8, 0),
                LocalDateTime.of(2024, 5, 30, 9, 0),
                10,
                ExerciseType.BENCH_PRESS,
                "Test workout"
        );
    }
}
