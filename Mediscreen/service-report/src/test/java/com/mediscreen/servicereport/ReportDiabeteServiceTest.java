package com.mediscreen.servicereport;

import com.mediscreen.servicereport.model.Note;
import com.mediscreen.servicereport.model.Patient;
import com.mediscreen.servicereport.repository.NoteRepository;
import com.mediscreen.servicereport.repository.PatientRepository;
import com.mediscreen.servicereport.service.IReportDiabeteService;
import com.mediscreen.servicereport.service.impl.ReportDiabeteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ReportDiabeteServiceTest {
    @InjectMocks
    private ReportDiabeteService reportDiabeteService;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    private void setup() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        reportDiabeteService = new ReportDiabeteService(noteRepository, patientRepository);
        //call post-constructor
        Method postConstruct =  ReportDiabeteService.class.getDeclaredMethod("setup",null); // methodName,parameters
        postConstruct.setAccessible(true);
        postConstruct.invoke(reportDiabeteService);
    }

    @Order(1)
    @Test
    void test_getOccurenceOfDeclencheur() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note("1", "1", "TestPatient1", "Le patient à une prise de poids et une respiration anormal","docteur2");
        Note note2 = new Note("2", "1", "TestPatient1", "Le patient est un fumeur","docteur2");

        noteList.add(note1);
        noteList.add(note2);

        when(noteRepository.findBySpecificPatId(anyString())).thenReturn(noteList);

        Map<String, Integer> mapResult = reportDiabeteService.getOccurenceOfDeclencheur("1");

        assertThat(mapResult).isNotNull();
        assertThat(mapResult.containsKey("Poids")).isTrue();
        assertThat(mapResult.containsKey("Anormal")).isTrue();
        assertThat(mapResult.containsKey("Fumeur")).isTrue();
    }

    @Order(2)
    @Test
    void test_determineRiskLevel_None() {
        Map<String, Integer> mapDeclancheurs = new HashMap<>();

        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1999, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        mapDeclancheurs.put("Poids", 1);
        mapDeclancheurs.put("Fumeur", 1);

        String result = reportDiabeteService.determineRiskLevel(mapDeclancheurs, patient);

        assertThat(result).isEqualTo("None");

    }

    @Order(3)
    @Test
    void test_determineRiskLevel_Borderline() {
        Map<String, Integer> mapDeclancheurs = new HashMap<>();

        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1956, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        mapDeclancheurs.put("Poids", 1);
        mapDeclancheurs.put("Fumeur", 1);

        String result = reportDiabeteService.determineRiskLevel(mapDeclancheurs, patient);

        assertThat(result).isEqualTo("Borderline");

    }

    @Order(4)
    @Test
    void test_determineRiskLevel_InDanger() {
        Map<String, Integer> mapDeclancheurs = new HashMap<>();

        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1999, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        mapDeclancheurs.put("Poids", 1);
        mapDeclancheurs.put("Fumeur", 1);
        mapDeclancheurs.put("Taille", 1);



        String result = reportDiabeteService.determineRiskLevel(mapDeclancheurs, patient);

        assertThat(result).isEqualTo("In Danger");

    }

    @Order(5)
    @Test
    void test_determineRiskLevel_EarlyOnset() {
        Map<String, Integer> mapDeclancheurs = new HashMap<>();

        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1999, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        mapDeclancheurs.put("Poids", 1);
        mapDeclancheurs.put("Fumeur", 1);
        mapDeclancheurs.put("Taille", 1);
        mapDeclancheurs.put("Vertige", 1);
        mapDeclancheurs.put("Anticorps", 1);


        String result = reportDiabeteService.determineRiskLevel(mapDeclancheurs, patient);

        assertThat(result).isEqualTo("Early onset");

    }

    @Order(6)
    @Test
    void test_ageOfPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1956, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        int age = reportDiabeteService.ageOfPatient(patient);

        assertThat(age).isEqualTo(66);
    }

    @Order(7)
    @Test
    void test_getPatientByPatId() {
        Patient patient = new Patient(1L, "Rho", "Jax", LocalDate.of(1956, 12, 25),
                'M', "22 Rue South-Bisto", "562-456-654");

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        Patient patientResult = reportDiabeteService.getPatientByPatId("1");

        assertThat(patientResult).isNotNull();
        assertThat(patientResult.getAddress()).isEqualTo("22 Rue South-Bisto");
        assertThat(patientResult.getPhone()).isEqualTo("562-456-654");
    }

    @Order(8)
    @Test
    void test_getPatientByFamilyName() {
        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1956, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        when(patientRepository.findByFamilyName(anyString())).thenReturn(patient);

        Patient patientResult = reportDiabeteService.getPatientByFamilyName("Rho");

        assertThat(patientResult).isNotNull();
        assertThat(patientResult.getLastName()).isEqualTo("Rho");
    }


    @Order(8)
    @Test
    void test_generateReport() {
        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1956, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        String report = reportDiabeteService.generateReport("None", patient);

        assertThat(report).isEqualTo("Patient: Jax Rho (age 66) diabetes assessment is : None");
    }
}
