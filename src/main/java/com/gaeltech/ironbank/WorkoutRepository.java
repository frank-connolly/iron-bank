package com.gaeltech.ironbank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {

    @Query("""
            SELECT w FROM WorkoutEntity w WHERE
            (:startDate IS NULL OR w.startTime >= :startDate) AND
            (:endDate IS NULL OR w.endTime <= :endDate) AND
            (:text IS NULL OR LOWER(w.exerciseType) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(w.notes) LIKE LOWER(CONCAT('%', :text, '%')))""")
    List<WorkoutEntity> searchWorkouts(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("text") String text);
}
