package com.gaeltech.ironbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createWorkout() throws Exception {
        WorkoutDto workoutDto = createTestWorkoutDto();

        String response = mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto createdWorkout = objectMapper.readValue(response, WorkoutDto.class);

        assertThat(createdWorkout).isNotNull();
        assertThat(createdWorkout.id()).isNotNull();
        assertThat(createdWorkout.startTime()).isEqualToIgnoringNanos(workoutDto.startTime());
        assertThat(createdWorkout.endTime()).isEqualToIgnoringNanos(workoutDto.endTime());
        assertThat(createdWorkout.numberOfReps()).isEqualTo(workoutDto.numberOfReps());
        assertThat(createdWorkout.exerciseType()).isEqualTo(workoutDto.exerciseType());
        assertThat(createdWorkout.notes()).isEqualTo(workoutDto.notes());
    }

    @Test
    void getWorkoutById() throws Exception {
        WorkoutDto workoutDto = createTestWorkoutDto();

        String postResponse = mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto createdWorkout = objectMapper.readValue(postResponse, WorkoutDto.class);

        String getResponse = mockMvc.perform(get("/workouts/{id}", createdWorkout.id()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto fetchedWorkout = objectMapper.readValue(getResponse, WorkoutDto.class);

        assertThat(fetchedWorkout).isNotNull();
        assertThat(fetchedWorkout.id()).isEqualTo(createdWorkout.id());
        assertThat(fetchedWorkout.startTime()).isEqualToIgnoringNanos(createdWorkout.startTime());
        assertThat(fetchedWorkout.endTime()).isEqualToIgnoringNanos(createdWorkout.endTime());
        assertThat(fetchedWorkout.numberOfReps()).isEqualTo(createdWorkout.numberOfReps());
        assertThat(fetchedWorkout.exerciseType()).isEqualTo(createdWorkout.exerciseType());
        assertThat(fetchedWorkout.notes()).isEqualTo(createdWorkout.notes());
    }


    @Test
    void getWorkoutById_NotFound() throws Exception {
        mockMvc.perform(get("/workouts/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllWorkouts() throws Exception {
        mockMvc.perform(get("/workouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', nullValues = "null", value = {
            "2024-05-30|2024-05-30|bench|1",
            "2024-05-30|null|bench|1",
            "null|2024-05-30|bench|1",
            "null|null|bench|1",
            "null|null|null|2",
            "2024-05-30|2024-05-30|null|2",
            "2024-05-30|null|null|2",
            "null|2024-05-30|null|2"
    })
    void searchWorkoutsTest(String startDateStr, String endDateStr, String text, int expectedResults) throws Exception {
        String url = "/workouts/search?";

        if (startDateStr != null) {
            url += "startDate=" + startDateStr + "&";
        }
        if (endDateStr != null) {
            url += "endDate=" + endDateStr + "&";
        }
        if (text != null) {
            url += "text=" + text;
        }

        url = url.endsWith("&") ? url.substring(0, url.length() - 1) : url;

        String response = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedResults))
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto[] workouts = objectMapper.readValue(response, WorkoutDto[].class);

        assertThat(workouts).hasSize(expectedResults);
    }


    @Test
    void deleteWorkout() throws Exception {
        WorkoutDto workoutDto = createTestWorkoutDto();
        String response = mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto createdWorkout = objectMapper.readValue(response, WorkoutDto.class);

        mockMvc.perform(delete("/workouts/{id}", createdWorkout.id()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/workouts/{id}", createdWorkout.id()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWorkout() throws Exception {
        WorkoutDto workoutDto = createTestWorkoutDto();
        String response = mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto createdWorkout = objectMapper.readValue(response, WorkoutDto.class);

        WorkoutDto updatedWorkoutDto = new WorkoutDto(
                createdWorkout.id(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                20,
                ExerciseType.SQUAT,
                "Updated workout"
        );

        String putResponse = mockMvc.perform(put("/workouts/{id}", createdWorkout.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedWorkoutDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto updatedWorkout = objectMapper.readValue(putResponse, WorkoutDto.class);

        assertThat(updatedWorkout).isNotNull();
        assertThat(updatedWorkout.id()).isEqualTo(createdWorkout.id());
        assertThat(updatedWorkout.startTime()).isEqualToIgnoringNanos(updatedWorkoutDto.startTime());
        assertThat(updatedWorkout.endTime()).isEqualToIgnoringNanos(updatedWorkoutDto.endTime());
        assertThat(updatedWorkout.numberOfReps()).isEqualTo(updatedWorkoutDto.numberOfReps());
        assertThat(updatedWorkout.exerciseType()).isEqualTo(updatedWorkoutDto.exerciseType());
        assertThat(updatedWorkout.notes()).isEqualTo(updatedWorkoutDto.notes());
    }

    @Test
    void updateWorkout_InvalidData() throws Exception {
        WorkoutDto workoutDto = createTestWorkoutDto();
        String response = mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WorkoutDto createdWorkout = objectMapper.readValue(response, WorkoutDto.class);

        String invalidJson = String.format(
                "{\"id\": %d, \"startTime\": null, \"endTime\": \"%s\", \"numberOfReps\": -10, \"exerciseType\": \"%s\", \"notes\": \"%s\"}",
                createdWorkout.id(),
                LocalDateTime.now().plusHours(2),
                ExerciseType.SQUAT,
                "Updated workout"
        );

        mockMvc.perform(put("/workouts/{id}", createdWorkout.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWorkout_InvalidJson() throws Exception {
        String invalidJson = "{ invalid json }";

        mockMvc.perform(post("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    private WorkoutDto createTestWorkoutDto() {
        return new WorkoutDto(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                10,
                ExerciseType.BENCH_PRESS,
                "Test workout"
        );
    }
}
