package com.mediscreen.servicereport.service;

import com.mediscreen.servicereport.model.Patient;

import java.util.Map;

public interface IReportDiabeteService {
//    String genererReport(String patId);

    Patient getPatientByPatId(String patId);
    Patient getPatientByFamilyName(String familyName);
    Map<String, Integer> getOccurenceOfDechancheur(String patId);
    String determineRiskLevel (Map<String, Integer> mapDeclencheurs, Patient patient);

    String generateReport(String riskLevel, Patient patient);
}
