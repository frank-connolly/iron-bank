package com.frank.ironbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    public final Logger log = LoggerFactory.getLogger(WorkoutController.class);

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWorkout(@RequestBody Workout workout) {
        log.info("Creating workout: {}", workout);
        workoutService.createWorkout(workout);
    }

    @GetMapping
    public void listWorkouts() {
        log.info("Listing all workouts");
        workoutService.listWorkouts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Workout>> getWorkout(@PathVariable Long id) {
        log.info("Getting workout with id: {}", id);
        Optional<Workout> workout = workoutService.getWorkout(id);
        if (workout.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workout);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody Workout workout) {
        log.info("Updating workout with id: {} with data: {}", id, workout);
        workoutService.updateWorkout(id, workout);
        return ResponseEntity.ok(workout);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(@PathVariable Long id) {
        log.info("Deleting workout with id: {}", id);
        workoutService.deleteWorkout(id);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
