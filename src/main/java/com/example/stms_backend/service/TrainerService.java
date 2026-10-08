package com.example.stms_backend.service;

import com.example.stms_backend.entity.Trainer;
import com.example.stms_backend.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer addTrainer(Trainer trainer) {

        if (trainerRepository.existsByTrainerId(trainer.getTrainerId())) {
            throw new RuntimeException("Trainer ID already exists");
        }

        return trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer getTrainerById(Long id) {

        return trainerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found"));
    }

    public Trainer updateTrainer(Long id, Trainer updatedTrainer) {

        Trainer trainer = getTrainerById(id);

        trainer.setTrainerId(updatedTrainer.getTrainerId());
        trainer.setName(updatedTrainer.getName());
        trainer.setEmail(updatedTrainer.getEmail());
        trainer.setPhone(updatedTrainer.getPhone());
        trainer.setSpecialization(updatedTrainer.getSpecialization());
        trainer.setExperience(updatedTrainer.getExperience());

        return trainerRepository.save(trainer);
    }

    public void deleteTrainer(Long id) {

        Trainer trainer = getTrainerById(id);

        trainerRepository.delete(trainer);
    }
}