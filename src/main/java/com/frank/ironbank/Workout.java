package com.frank.ironbank;

import java.time.LocalDateTime;

public record Workout(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int numberOfReps,
        ExerciseType exerciseType,
        String notes
) {}