package com.mediscreen.servicereport.repository;

import com.mediscreen.servicereport.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.lastName = ?1")
    Patient findByFamilyName(String familyName);
}
