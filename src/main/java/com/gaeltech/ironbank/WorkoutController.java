package com.gaeltech.ironbank;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    Logger log = LoggerFactory.getLogger(WorkoutController.class);

    private final WorkoutService service;

    public WorkoutController(WorkoutService service) {
        this.service = service;
    }

    @PostMapping
    public WorkoutDto createWorkout(@Valid @RequestBody WorkoutDto workoutDto) {
        log.info("Creating workout");
        return service.createWorkout(workoutDto);
    }

    @GetMapping("/{id}")
    public WorkoutDto getWorkout(@PathVariable Long id) {
        log.info("Getting workout by id: {}", id);
        return service.getWorkout(id);
    }

    @GetMapping
    public Iterable<WorkoutDto> getAllWorkouts() {
        log.info("Getting all workouts");
        return service.getAllWorkouts();
    }

    @GetMapping("/search")
    public List<WorkoutDto> searchWorkouts(@Valid @ModelAttribute WorkoutSearchRequest searchRequest) {
        log.info("Searching workouts");
        return service.searchWorkouts(searchRequest.getStartDate(), searchRequest.getEndDate(), searchRequest.getText());
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable Long id) {
        log.info("Deleting workout by id: {}", id);
        service.deleteWorkout(id);
    }

    @PutMapping("/{id}")
    public WorkoutDto updateWorkout(@PathVariable Long id, @Valid @RequestBody WorkoutDto workoutDto) {
        log.info("Updating workout by id: {}", id);
        return service.updateWorkout(id, workoutDto);
    }

}
