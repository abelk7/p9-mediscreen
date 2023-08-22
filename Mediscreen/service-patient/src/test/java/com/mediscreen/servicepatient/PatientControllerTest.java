package com.mediscreen.servicepatient;

import com.mediscreen.servicepatient.controller.PatientController;
import com.mediscreen.servicepatient.exception.PatientNotFoundException;
import com.mediscreen.servicepatient.exception.UnableToAddNewPatientException;
import com.mediscreen.servicepatient.model.Patient;
import com.mediscreen.servicepatient.service.impl.PatientService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
    @InjectMocks
    PatientController patientController;
    @Mock
    PatientService patientService;

    @Test
    @Order(1)
    void test_getAllPatient() {
        List<Patient> patientList = new ArrayList<>();
        Patient patient1 = new Patient(null, "Family1", "Given1", LocalDate.of(1989, 10, 18), 'M', null, null);
        Patient patient2 = new Patient(null, "Family2", "Given2", LocalDate.of(1959, 4, 21), 'M', null, null);
        Patient patient3 = new Patient(null, "Family3", "Given3", LocalDate.of(1997, 3, 9), 'F', null, null);
        patientList.add(patient1);
        patientList.add(patient2);
        patientList.add(patient3);

        when(patientService.findAll()).thenReturn(patientList);

        List<Patient> result = patientController.getPatients();

        assertThat(result).isNotEmpty();
    }

    @Test
    @Order(2)
    void test_getPatient() {
        Patient patient1 = new Patient(1L, "Family1", "Given1", LocalDate.of(1989, 10, 18), 'M', null, null);

        when(patientService.findById(anyLong())).thenReturn(Optional.of(patient1));

        Patient result = patientController.getPatient(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test()
    @Order(3)
    void test_getPatient_should_throws_PatientNotFoundException() {
        Optional<Patient> patient = Optional.empty();

        when(patientService.findById(anyLong())).thenReturn(patient);

        Throwable exception = assertThrows(PatientNotFoundException.class, () -> patientController.getPatient(1L));

        assertThat("Le patient avec l'id 1 n'existe pas").isEqualTo(exception.getMessage());
    }

    @Test
    @Order(4)
    void test_postPatient() {
        Patient patient1 = new Patient(1L, "Family1", "Given1", LocalDate.of(1989, 10, 18), 'M', null, "565-798-456");

        when(patientService.save(any(Patient.class))).thenReturn(patient1);

        Patient result = patientController.postPatient(patient1);

        assertThat(result).isNotNull();
    }

    @Test()
    @Order(5)
    void test_postPatient_should_throws_UnableToAddNewPatientException() {
        Patient patient1 = new Patient(1L, "Family1", "Given1", LocalDate.of(1989, 10, 18), 'M', null, "565-798-456");

        when(patientService.save(any(Patient.class))).thenReturn(null);

        Throwable exception = assertThrows(UnableToAddNewPatientException.class, () -> patientController.postPatient(patient1));

        assertThat("Impossible d'ajouter un nouveau patient").isEqualTo(exception.getMessage());
    }

    @Test
    @Order(6)
    void test_updatePatient() {
        Patient patient = new Patient(1L, "Family1", "Given1", LocalDate.of(1989, 10, 18), 'M', null, "565-798-456");
        Patient patient1 = new Patient(1L, "FamilyFamily1", "GivenGiven1", LocalDate.of(1989, 10, 18), 'M', null, "565-798-456");

        when(patientService.findById(anyLong())).thenReturn(Optional.of(patient));
        when(patientService.save(any(Patient.class))).thenReturn(patient1);

        Patient result = patientController.updatePatient(1L, patient1);

        assertThat(result).isNotNull();
        assertThat(result.getFamily()).isEqualTo(patient1.getFamily());
    }

    @Test
    @Order(7)
    void test_updatePatient_should_throws_PatientNotFoundException() {
        Optional<Patient> patient = Optional.empty();
        Patient patient1 = new Patient(1L, "FamilyFamily1", "GivenGiven1", LocalDate.of(1989, 10, 18), 'M', null, "565-798-456");

        when(patientService.findById(anyLong())).thenReturn(patient);

        Throwable exception = assertThrows(PatientNotFoundException.class, () -> patientController.updatePatient(1L, patient1));

        assertThat("Le patient avec l'id 1 n'existe pas").isEqualTo(exception.getMessage());
    }

    @Test
    @Order(8)
    void test_deletePatient_should_throws_PatientNotFoundException() {
        Optional<Patient> patient = Optional.empty();

        when(patientService.findById(anyLong())).thenReturn(patient);

        Throwable exception = assertThrows(PatientNotFoundException.class, () -> patientController.deletePatient(1L));
        assertThat("Le patient avec l'id 1 n'existe pas").isEqualTo(exception.getMessage());
    }
}
