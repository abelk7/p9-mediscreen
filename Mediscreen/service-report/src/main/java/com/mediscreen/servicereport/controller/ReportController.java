package com.mediscreen.servicereport.controller;

import com.mediscreen.servicereport.model.Patient;
import com.mediscreen.servicereport.service.IReportDiabeteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@AllArgsConstructor
@Controller
public class ReportController {
    private final IReportDiabeteService reportDiabeteService;

    @PostMapping(value = "/assess/id")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRiskByPatientId(@RequestParam(name = "patId") String patId) {
        Patient patient = reportDiabeteService.getPatientByPatId(patId);
        if (patient != null) {
            Map<String, Integer> mapTermesDeclencheurPatient = reportDiabeteService.getOccurenceOfDeclencheur(patId);
            String riskLevel = reportDiabeteService.determineRiskLevel(mapTermesDeclencheurPatient, patient);
            return reportDiabeteService.generateReport(riskLevel, patient);
        }
        return null;
    }

    @PostMapping(value = "/assess/familyName")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRiskByPatientFamilyName(@RequestParam(name = "familyName") String familyName) {
        Patient patient = reportDiabeteService.getPatientByFamilyName(familyName);
        if (patient != null) {
            Map<String, Integer> mapTermesDeclencheurPatient = reportDiabeteService.getOccurenceOfDeclencheur(patient.getId().toString());
            String riskLevel = reportDiabeteService.determineRiskLevel(mapTermesDeclencheurPatient, patient);
            return reportDiabeteService.generateReport(riskLevel, patient);
        }
        return null;
    }
}
