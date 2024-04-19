package com.frank.ironbank.controllers;

import com.frank.ironbank.models.WorkoutSet;
import com.frank.ironbank.services.WorkoutSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workoutsets")
public class WorkoutSetController {

    private final WorkoutSetService workoutSetService;

    @Autowired
    public WorkoutSetController(WorkoutSetService workoutSetService) {
        this.workoutSetService = workoutSetService;
    }

    @PostMapping
    public WorkoutSet createWorkoutSet(@RequestBody WorkoutSet workoutSet) {
        return workoutSetService.save(workoutSet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSet> getWorkoutSet(@PathVariable Long id) {
        return workoutSetService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<WorkoutSet> getAllWorkoutSets() {
        return workoutSetService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSet(@PathVariable Long id) {
        workoutSetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}