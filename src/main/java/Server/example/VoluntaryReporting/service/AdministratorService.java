package Server.example.VoluntaryReporting.service;

import Server.example.VoluntaryReporting.entity.Administrator;

public interface AdministratorService {
    Administrator findByUsrName(String userName);
}
