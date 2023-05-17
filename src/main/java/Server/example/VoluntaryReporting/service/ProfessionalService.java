package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.Professional;

import java.util.List;

public interface ProfessionalService {
    int addProfessional(Professional professional);

    Professional findById(Integer proId);

    List<Professional> findAll();

    List<Professional> findByName(String proName);
}
