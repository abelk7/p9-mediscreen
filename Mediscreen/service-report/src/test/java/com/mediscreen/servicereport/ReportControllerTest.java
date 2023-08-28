package com.mediscreen.servicereport;

import com.mediscreen.servicereport.controller.ReportController;
import com.mediscreen.servicereport.model.Patient;
import com.mediscreen.servicereport.service.impl.ReportDiabeteService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {
    @InjectMocks
    ReportController reportController;
    @Mock
    ReportDiabeteService reportDiabeteService;

    @Test
    @Order(1)
    void test_getRiskByPatientId() {
        Patient patient = new Patient();
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1956, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        Map<String, Integer> mapDeclancheurs = new HashMap<>();
        mapDeclancheurs.put("Poids", 1);
        mapDeclancheurs.put("Fumeur", 1);
        mapDeclancheurs.put("Taille", 1);
        mapDeclancheurs.put("Vertige", 1);
        mapDeclancheurs.put("Anticorps", 1);

        when(reportDiabeteService.getPatientByPatId(anyString())).thenReturn(patient);
        when(reportDiabeteService.getOccurenceOfDeclencheur(anyString())).thenReturn(mapDeclancheurs);
        when(reportDiabeteService.determineRiskLevel(any(), any(Patient.class))).thenReturn("Early onset");
        when(reportDiabeteService.generateReport(anyString(), any(Patient.class))).thenReturn("Patient: Jax Rho (age 66) diabetes assessment is : Early onset");

        String result = reportController.getRiskByPatientId("1");

        assertThat(result).isEqualTo("Patient: Jax Rho (age 66) diabetes assessment is : Early onset");
    }

    @Test
    @Order(1)
    void test_getRiskByPatientFamilyName() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setLastName("Rho");
        patient.setFirstName("Jax");
        patient.setDob(LocalDate.of(1956, 12, 25));
        patient.setSex('M');
        patient.setAddress("22 Rue South-Bisto");
        patient.setPhone("562-456-654");

        Map<String, Integer> mapDeclancheurs = new HashMap<>();
        mapDeclancheurs.put("Poids", 1);
        mapDeclancheurs.put("Fumeur", 1);
        mapDeclancheurs.put("Taille", 1);
        mapDeclancheurs.put("Vertige", 1);
        mapDeclancheurs.put("Anticorps", 1);

        when(reportDiabeteService.getPatientByFamilyName(anyString())).thenReturn(patient);
        when(reportDiabeteService.getOccurenceOfDeclencheur(anyString())).thenReturn(mapDeclancheurs);
        when(reportDiabeteService.determineRiskLevel(any(), any(Patient.class))).thenReturn("Early onset");
        when(reportDiabeteService.generateReport(anyString(), any(Patient.class))).thenReturn("Patient: Jax Rho (age 66) diabetes assessment is : Early onset");

        String result = reportController.getRiskByPatientFamilyName("Rho");

        assertThat(result).isEqualTo("Patient: Jax Rho (age 66) diabetes assessment is : Early onset");
    }
}
