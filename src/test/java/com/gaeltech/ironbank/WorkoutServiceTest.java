package com.gaeltech.ironbank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository repository;

    private WorkoutService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new WorkoutService(repository);
    }

    @Test
    void createWorkoutTest() {
        WorkoutDto workoutDto = createTestWorkoutDto();
        WorkoutEntity workoutEntity = new WorkoutEntity();
        when(repository.save(any(WorkoutEntity.class))).thenReturn(workoutEntity);

        WorkoutDto result = service.createWorkout(workoutDto);

        assertThat(result).isNotNull();
        verify(repository, times(1)).save(any(WorkoutEntity.class));
    }

    @Test
    void getWorkoutTest() {
        WorkoutEntity workoutEntity = new WorkoutEntity();
        when(repository.findById(anyLong())).thenReturn(Optional.of(workoutEntity));

        WorkoutDto result = service.getWorkout(1L);

        assertThat(result).isNotNull();
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getAllWorkoutsTest() {
        WorkoutEntity workoutEntity1 = new WorkoutEntity();
        WorkoutEntity workoutEntity2 = new WorkoutEntity();
        when(repository.findAll()).thenReturn(Arrays.asList(workoutEntity1, workoutEntity2));

        Iterable<WorkoutDto> result = service.getAllWorkouts();

        assertThat(result).isNotNull();
        verify(repository, times(1)).findAll();
    }

    @Test
    void deleteWorkoutTest() {
        doNothing().when(repository).deleteById(anyLong());

        service.deleteWorkout(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void updateWorkoutTest() {
        WorkoutEntity workoutEntity = new WorkoutEntity();
        when(repository.findById(anyLong())).thenReturn(Optional.of(workoutEntity));
        when(repository.save(any(WorkoutEntity.class))).thenReturn(workoutEntity);

        WorkoutDto workoutDto = createTestWorkoutDto();
        WorkoutDto result = service.updateWorkout(1L, workoutDto);

        assertThat(result).isNotNull();
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(WorkoutEntity.class));
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
}