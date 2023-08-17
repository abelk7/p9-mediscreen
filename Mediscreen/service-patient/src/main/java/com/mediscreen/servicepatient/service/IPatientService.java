package com.mediscreen.servicepatient.service;

import com.mediscreen.servicepatient.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
    Patient save(Patient patient);
    void delete(Long id);
}
