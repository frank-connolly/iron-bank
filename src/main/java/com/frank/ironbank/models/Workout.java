package com.frank.ironbank.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSet> workoutSets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WorkoutSet> getWorkoutSets() {
        return workoutSets;
    }

    public void setWorkoutSets(List<WorkoutSet> workoutSets) {
        this.workoutSets = workoutSets;
    }
}
