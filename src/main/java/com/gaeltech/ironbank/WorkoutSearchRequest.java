package com.gaeltech.ironbank;

import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class WorkoutSearchRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String text;

    public WorkoutSearchRequest() {
        this.startDate = LocalDate.now().minusMonths(1);
        this.endDate = LocalDate.now();
        this.text = "";
    }

    public WorkoutSearchRequest(LocalDate startDate, LocalDate endDate, String text) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

