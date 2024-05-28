package com.frank.ironbank;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkoutRepository {

    private final Logger log = LoggerFactory.getLogger(WorkoutRepository.class);
    private static final String CSV_FILE_PATH = "src/main/resources/workouts.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void saveWorkout(Workout workout) {
        List<Workout> workouts = readWorkoutsFromFile();
        workouts.add(workout);
        writeWorkoutsToFile(workouts);
    }

    public Workout getWorkout(Long id) {
        log.info("Retrieving workout with id: {}", id);
        return readWorkoutsFromFile().stream()
                .filter(workout -> workout.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateWorkout(Long id, Workout updatedWorkout) {
        log.info("Updating workout with id: {} with data: {}", id, updatedWorkout);
        List<Workout> workouts = readWorkoutsFromFile().stream()
                .map(workout -> workout.id().equals(id) ? updatedWorkout : workout)
                .toList();
        writeWorkoutsToFile(workouts);
    }

    public void deleteWorkout(Long id) {
        log.info("Deleting workout with id: {}", id);
        List<Workout> workouts = readWorkoutsFromFile().stream()
                .filter(workout -> !workout.id().equals(id))
                .toList();
        writeWorkoutsToFile(workouts);
    }

    public List<Workout> listWorkouts() {
        log.info("Listing all workouts");
        return readWorkoutsFromFile();
    }

    private List<Workout> readWorkoutsFromFile() {
        List<Workout> workouts = new ArrayList<>();
        try (var reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
             var csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            csvParser.forEach(entry -> workouts.add(convertCSVToWorkout(entry)));
        } catch (IOException e) {
            log.error("Failed to read from CSV file", e);
            throw new RuntimeException("Failed to read from CSV file", e);
        }
        return workouts;
    }

    private void writeWorkoutsToFile(List<Workout> workouts) {
        try (var writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH));
             var csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Workout workout : workouts) {
                csvPrinter.printRecord(List.of(convertWorkoutToCSV(workout)));
            }
        } catch (IOException e) {
            log.error("Failed to write to CSV file", e);
            throw new RuntimeException("Failed to write to CSV file", e);
        }
    }

    private String[] convertWorkoutToCSV(Workout workout) {
        return new String[]{
                String.valueOf(workout.id()),
                workout.startTime().format(formatter),
                workout.endTime().format(formatter),
                String.valueOf(workout.numberOfReps()),
                workout.exerciseType().toString(),
                workout.notes()
        };
    }

    private Workout convertCSVToWorkout(CSVRecord csvData) {
        return new Workout(
                Long.parseLong(csvData.get(0)),
                LocalDateTime.parse(csvData.get(1), formatter),
                LocalDateTime.parse(csvData.get(2), formatter),
                Integer.parseInt(csvData.get(3)),
                ExerciseType.valueOf(csvData.get(4)),
                csvData.get(5)
        );
    }
}