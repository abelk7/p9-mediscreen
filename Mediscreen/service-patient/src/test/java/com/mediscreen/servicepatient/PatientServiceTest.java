package com.mediscreen.servicepatient;


import com.mediscreen.servicepatient.dao.PatientDao;
import com.mediscreen.servicepatient.model.Patient;
import com.mediscreen.servicepatient.service.IPatientService;
import com.mediscreen.servicepatient.service.impl.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class PatientServiceTest {
    private IPatientService patientService;
    @Mock
    private PatientDao patientDao;

    @BeforeEach
    private void setup() {
        patientService = new PatientService(patientDao);
    }

    @Order(1)
    @Test
    void test_getAllPatient() {
        List<Patient> patientList = new ArrayList<>();

        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1999, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        Patient patient2 = new Patient();
        patient2.setLastName("Almiya");
        patient2.setFirstName("Nadine");
        patient2.setDob(LocalDate.of(1987, 3, 25));
        patient2.setSex('F');
        patient2.setAddress("22 Rue South-Bisto");
        patient2.setPhone("562-456-654");

        patientList.add(patient);
        patientList.add(patient2);

        when(patientDao.findAll()).thenReturn(patientList);

        List<Patient> patients = patientService.findAll();

        assertThat(patients).isNotNull();
        assertThat(patients).isNotEmpty();
        assertThat(patients).hasSize(patientList.size());
    }

    @Order(2)
    @Test
    void test_getPatient() {
        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1999, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue Henri Miller");
        patient.setPhone("846-456-652");

        when(patientDao.findById(anyLong())).thenReturn(Optional.of(patient));

        Optional<Patient> patientFound = patientService.findById(1L);

        assertThat(patientFound).get().isNotNull();
    }

    @Order(3)
    @Test
    void test_savePatient() {
        Patient patient = new Patient();
        patient.setLastName("Almiya");
        patient.setFirstName("Nadine");
        patient.setDob(LocalDate.of(1987, 3, 25));
        patient.setSex('F');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        when(patientDao.save(any(Patient.class))).thenReturn(patient);

        Patient patientResult = patientService.save(patient);

        assertThat(patientResult).isNotNull();
        assertThat(patientResult.getAddress()).isEqualTo(patient.getAddress());
    }

}
