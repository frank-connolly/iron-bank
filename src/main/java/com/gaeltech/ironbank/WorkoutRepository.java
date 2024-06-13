package com.gaeltech.ironbank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {

    @Query("SELECT w FROM WorkoutEntity w WHERE " +
            "(:startTime IS NULL OR w.startTime >= :startTime) AND " +
            "(:endTime IS NULL OR w.endTime <= :endTime) AND " +
            "(:text IS NULL OR LOWER(w.exerciseType) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(w.notes) LIKE LOWER(CONCAT('%', :text, '%')))")
    List<WorkoutEntity> searchWorkouts(@Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime,
                                       @Param("text") String text);
}
