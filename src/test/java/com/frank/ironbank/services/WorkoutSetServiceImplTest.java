package com.frank.ironbank.services;

import com.frank.ironbank.models.WorkoutSet;
import com.frank.ironbank.repositories.WorkoutSetRepository;
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

public class WorkoutSetServiceImplTest {

    @InjectMocks
    private WorkoutSetServiceImpl workoutSetService;

    @Mock
    private WorkoutSetRepository workoutSetRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWorkoutSet() {
        WorkoutSet workoutSet = new WorkoutSet();
        workoutSet.setId(1L);
        when(workoutSetRepository.save(any(WorkoutSet.class))).thenReturn(workoutSet);

        WorkoutSet savedWorkoutSet = workoutSetService.save(new WorkoutSet());

        assertEquals(workoutSet.getId(), savedWorkoutSet.getId());
        verify(workoutSetRepository, times(1)).save(any(WorkoutSet.class));
    }

    @Test
    public void testFindById() {
        WorkoutSet workoutSet = new WorkoutSet();
        workoutSet.setId(1L);
        when(workoutSetRepository.findById(1L)).thenReturn(Optional.of(workoutSet));

        Optional<WorkoutSet> returnedWorkoutSet = workoutSetService.findById(1L);

        assertEquals(workoutSet.getId(), returnedWorkoutSet.get().getId());
        verify(workoutSetRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        WorkoutSet workoutSet1 = new WorkoutSet();
        workoutSet1.setId(1L);
        WorkoutSet workoutSet2 = new WorkoutSet();
        workoutSet2.setId(2L);
        when(workoutSetRepository.findAll()).thenReturn(Arrays.asList(workoutSet1, workoutSet2));

        List<WorkoutSet> workoutSets = workoutSetService.findAll();

        assertEquals(2, workoutSets.size());
        verify(workoutSetRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        doNothing().when(workoutSetRepository).deleteById(1L);

        workoutSetService.deleteById(1L);

        verify(workoutSetRepository, times(1)).deleteById(1L);
    }
}