package com.mediscreen.servicepatient.it;

import com.mediscreen.servicepatient.model.Patient;
import com.mediscreen.servicepatient.service.IPatientService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientServiceTestIT {
    @Autowired
    private IPatientService patientService;

    @Order(1)
    @Test
    void test_getAllPatient() {
        List<Patient> patients = patientService.findAll();
        assertThat(patients).isNotNull();
        assertThat(patients).isNotEmpty();
    }

    @Order(2)
    @Test
    void test_getPatient() {
        Optional<Patient> patient = patientService.findById(1L);
        assertThat(patient).isPresent();
    }

    @Order(3)
    @Test
    void test_savePatient() {
        Patient patient = new Patient();
        patient.setFamily("Almiya");
        patient.setGiven("Nadine");
        patient.setDob(LocalDate.of(1987,3,25));
        patient.setSex('F');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        Patient patientResult = patientService.save(patient);

        assertThat(patientResult).isNotNull();
        assertThat(patientResult.getAddress()).isEqualTo(patient.getAddress());
    }
}
