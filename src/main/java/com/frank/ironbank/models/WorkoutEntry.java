package com.frank.ironbank.models;

import java.time.LocalDateTime;

public record WorkoutEntry(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int numberOfReps,
        ExerciseType exerciseType,
        String notes
) {}