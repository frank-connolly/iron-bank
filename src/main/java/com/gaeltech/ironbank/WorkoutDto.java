package com.gaeltech.ironbank;

import java.time.LocalDateTime;

public record WorkoutDto(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int numberOfReps,
        ExerciseType exerciseType,
        String notes
) {}