package com.frank.ironbank.controllers;

import com.frank.ironbank.models.Workout;
import com.frank.ironbank.services.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutService workoutService;

    @BeforeEach
    public void setup() {
        Workout workout = new Workout();
        workout.setId(1L);

        when(workoutService.findById(1L)).thenReturn(Optional.of(workout));
        when(workoutService.findAll()).thenReturn(Collections.singletonList(workout));
    }

    @Test
    public void testGetWorkout() throws Exception {
        mockMvc.perform(get("/workouts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllWorkouts() throws Exception {
        mockMvc.perform(get("/workouts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateWorkout() throws Exception {
        Workout workout = new Workout();
        workout.setId(1L);

        when(workoutService.save(any(Workout.class))).thenReturn(workout);

        mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteWorkout() throws Exception {
        doNothing().when(workoutService).deleteById(1L);

        mockMvc.perform(delete("/workouts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
