package com.frank.ironbank;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSet> workoutSets;

    public List<WorkoutSet> getWorkoutSets() {
        return workoutSets;
    }

    public void setWorkoutSets(List<WorkoutSet> workoutSets) {
        this.workoutSets = workoutSets;
    }
}
