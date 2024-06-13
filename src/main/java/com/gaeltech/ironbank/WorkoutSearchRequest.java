package com.gaeltech.ironbank;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record WorkoutSearchRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        String text
) {}
