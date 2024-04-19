package com.frank.ironbank.controllers;

import com.frank.ironbank.models.WorkoutSet;
import com.frank.ironbank.services.WorkoutSetService;
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
public class WorkoutSetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutSetService workoutSetService;

    @BeforeEach
    public void setup() {
        WorkoutSet workoutSet = new WorkoutSet();
        workoutSet.setId(1L);

        when(workoutSetService.findById(1L)).thenReturn(Optional.of(workoutSet));
        when(workoutSetService.findAll()).thenReturn(Collections.singletonList(workoutSet));
    }

    @Test
    public void testGetWorkoutSet() throws Exception {
        mockMvc.perform(get("/workoutsets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllWorkoutSets() throws Exception {
        mockMvc.perform(get("/workoutsets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateWorkoutSet() throws Exception {
        WorkoutSet workoutSet = new WorkoutSet();
        workoutSet.setId(1L);

        when(workoutSetService.save(any(WorkoutSet.class))).thenReturn(workoutSet);

        mockMvc.perform(post("/workoutsets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteWorkoutSet() throws Exception {
        doNothing().when(workoutSetService).deleteById(1L);

        mockMvc.perform(delete("/workoutsets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
