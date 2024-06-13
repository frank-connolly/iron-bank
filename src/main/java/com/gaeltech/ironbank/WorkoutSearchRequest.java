package com.gaeltech.ironbank;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record WorkoutSearchRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
        String text
) {}
