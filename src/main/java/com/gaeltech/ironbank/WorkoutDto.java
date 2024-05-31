package com.gaeltech.ironbank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record WorkoutDto(
        Long id,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @Positive int numberOfReps,
        @NotNull ExerciseType exerciseType,
        @Size(max = 200) String notes
) {}