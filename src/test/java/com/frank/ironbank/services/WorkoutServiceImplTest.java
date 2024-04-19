package com.frank.ironbank.services;

import com.frank.ironbank.models.Workout;
import com.frank.ironbank.repositories.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WorkoutServiceImplTest {

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @Mock
    private WorkoutRepository workoutRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWorkout() {
        Workout workout = new Workout();
        workout.setId(1L);
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        Workout savedWorkout = workoutService.save(new Workout());

        assertEquals(workout.getId(), savedWorkout.getId());
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }

    @Test
    public void testFindById() {
        Workout workout = new Workout();
        workout.setId(1L);
        when(workoutRepository.findById(1L)).thenReturn(Optional.of(workout));

        Optional<Workout> returnedWorkout = workoutService.findById(1L);

        assertEquals(workout.getId(), returnedWorkout.get().getId());
        verify(workoutRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        Workout workout1 = new Workout();
        workout1.setId(1L);
        Workout workout2 = new Workout();
        workout2.setId(2L);
        when(workoutRepository.findAll()).thenReturn(Arrays.asList(workout1, workout2));

        List<Workout> workouts = workoutService.findAll();

        assertEquals(2, workouts.size());
        verify(workoutRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        doNothing().when(workoutRepository).deleteById(1L);

        workoutService.deleteById(1L);

        verify(workoutRepository, times(1)).deleteById(1L);
    }
}