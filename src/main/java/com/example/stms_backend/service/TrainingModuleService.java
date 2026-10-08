package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.TrainingModule;
import com.example.stms_backend.entity.TrainingProgram;
import com.example.stms_backend.repository.TrainingModuleRepository;
import com.example.stms_backend.repository.TrainingProgramRepository;

@Service
public class TrainingModuleService {

    private final TrainingModuleRepository trainingModuleRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public TrainingModuleService(
            TrainingModuleRepository trainingModuleRepository,
            TrainingProgramRepository trainingProgramRepository) {

        this.trainingModuleRepository = trainingModuleRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    public TrainingModule addModule(
            TrainingModule module,
            Long trainingProgramId) {

        TrainingProgram trainingProgram =
                trainingProgramRepository.findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        module.setTrainingProgram(trainingProgram);

        return trainingModuleRepository.save(module);
    }

    public List<TrainingModule> getAllModules() {
        return trainingModuleRepository.findAll();
    }

    public List<TrainingModule> getModulesByProgram(
            Long trainingProgramId) {

        return trainingModuleRepository
                .findByTrainingProgramId(trainingProgramId);
    }

    public TrainingModule getModuleById(Long id) {

        return trainingModuleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Training module not found"));
    }

    public TrainingModule updateModule(
            Long id,
            TrainingModule updatedModule,
            Long trainingProgramId) {

        TrainingModule module = getModuleById(id);

        TrainingProgram trainingProgram =
                trainingProgramRepository.findById(trainingProgramId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Training program not found"));

        module.setModuleTitle(updatedModule.getModuleTitle());
        module.setDescription(updatedModule.getDescription());
        module.setModuleOrder(updatedModule.getModuleOrder());
        module.setDuration(updatedModule.getDuration());
        module.setStatus(updatedModule.getStatus());
        module.setTrainingProgram(trainingProgram);

        return trainingModuleRepository.save(module);
    }

    public void deleteModule(Long id) {

        TrainingModule module = getModuleById(id);

        trainingModuleRepository.delete(module);
    }
}