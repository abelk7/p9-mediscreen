package com.mediscreen.servicepatient.controller;

import com.mediscreen.servicepatient.dao.PatientDao;
import com.mediscreen.servicepatient.exception.PatientNotFoundException;
import com.mediscreen.servicepatient.exception.UnableToAddNewPatientException;
import com.mediscreen.servicepatient.model.Patient;
import com.mediscreen.servicepatient.service.IPatientService;
import com.mediscreen.servicepatient.service.impl.PatientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping(value =  {"/patient/"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Patient> getPatients() {
        return patientService.findAll();
    }

    @GetMapping(value = "/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Patient getPatient(@PathVariable long id) {
        Optional<Patient> patient = patientService.findById(id);
        if(!patient.isPresent()) {
            throw new PatientNotFoundException("Le patient avec l'id " + id + " n'existe pas");
        }
        return patient.get();
    }

    @PostMapping(value = "/patient/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Patient postPatient(@RequestBody Patient patient) {
        Patient nouveauPatient = patientService.save(patient);

        if(nouveauPatient == null) {
            throw new UnableToAddNewPatientException("Impossible d'ajouter un nouveau patient");
        }
        return nouveauPatient;
    }

    @PutMapping(value = "/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Patient updatePatient(@PathVariable long id, @RequestBody Patient patientNewValues) {
        Optional<Patient> patientFound = patientService.findById(id);
        if(!patientFound.isPresent()) {
            throw new PatientNotFoundException("Le patient avec l'id " + id + " n'existe pas");
        }
        Patient patientToUpdate = patientFound.get();
        patientToUpdate.setFamily(patientNewValues.getFamily());
        patientToUpdate.setGiven(patientNewValues.getGiven());
        patientToUpdate.setSex(patientNewValues.getSex());
        patientToUpdate.setPhone(patientNewValues.getPhone());
        patientToUpdate.setDob(patientNewValues.getDob());
        patientToUpdate.setAddress(patientNewValues.getAddress());

        Patient patientUpdated = patientService.save(patientToUpdate);

        return patientUpdated;
    }

    @DeleteMapping(value = "/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deletePatient(@PathVariable long id) {
        Optional<Patient> patientFound = patientService.findById(id);
        if(!patientFound.isPresent()) {
            throw new PatientNotFoundException("Le patient avec l'id " + id + " n'existe pas");
        }
        patientService.delete(id);
    }
}
