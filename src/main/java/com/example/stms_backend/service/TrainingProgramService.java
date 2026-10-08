package com.example.stms_backend.service;

import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.TrainingProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;

    public TrainingProgramService(
            TrainingProgramRepository trainingProgramRepository) {
        this.trainingProgramRepository = trainingProgramRepository;
    }

    public TrainingProgram addProgram(TrainingProgram program) {
        return trainingProgramRepository.save(program);
    }

    public List<TrainingProgram> getAllPrograms() {
        return trainingProgramRepository.findAll();
    }

    public TrainingProgram getProgramById(Long id) {
        return trainingProgramRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Training program not found"));
    }

    public TrainingProgram updateProgram(
            Long id,
            TrainingProgram updatedProgram) {

        TrainingProgram program = getProgramById(id);

        program.setTitle(updatedProgram.getTitle());
        program.setDescription(updatedProgram.getDescription());
        program.setTrainer(updatedProgram.getTrainer());
        program.setDuration(updatedProgram.getDuration());
        program.setLevel(updatedProgram.getLevel());
        program.setStatus(updatedProgram.getStatus());

        return trainingProgramRepository.save(program);
    }

    public void deleteProgram(Long id) {
        TrainingProgram program = getProgramById(id);
        trainingProgramRepository.delete(program);
    }
}