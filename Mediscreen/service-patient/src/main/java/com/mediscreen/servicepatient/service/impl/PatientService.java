package com.mediscreen.servicepatient.service.impl;

import com.mediscreen.servicepatient.dao.PatientDao;
import com.mediscreen.servicepatient.model.Patient;
import com.mediscreen.servicepatient.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private PatientDao patientDao;

    @Override
    public List<Patient> findAll() {
        return patientDao.findAll();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientDao.findById(id);
    }

    @Override
    public Patient save(Patient patient) {
        return patientDao.save(patient);
    }

    @Override
    public void delete(Long id) {
        patientDao.deleteById(id);
    }
}
