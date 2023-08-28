package com.mediscreen.servicereport.service.impl;

import com.mediscreen.servicereport.model.Note;
import com.mediscreen.servicereport.model.Patient;
import com.mediscreen.servicereport.repository.NoteRepository;
import com.mediscreen.servicereport.repository.PatientRepository;
import com.mediscreen.servicereport.service.IReportDiabeteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ReportDiabeteService implements IReportDiabeteService {
    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;

    private List<String> termesDeclencheurs;


    @PostConstruct
    void setup() {
        termesDeclencheurs = new ArrayList<>();

        termesDeclencheurs.addAll(Arrays.asList(
                "Hémoglobine A1C",
                "Microalbumine",
                "Taille",
                "Poids",
                "Fumeur",
                "Anormal",
                "Cholestérol",
                "Vertige",
                "Rechute",
                "Réaction",
                "Anticorps"
        ));

//        Patient patient = getPatient(1L);
//         String risk = determineRiskLevel(getOccurenceOfDechancheur(patient.getId().toString()), patient);
//        System.out.println("---------RISK DETERMINED-----------");
//        System.out.println("##### " + risk + " #####");
//        System.out.println("-----------------------------------");
    }

    @Override
    public Map<String, Integer> getOccurenceOfDechancheur(String patId) {
        List<Note> noteList = noteRepository.findBySpecificPatId(patId);
        Map<String, Integer> mapOccurence = new HashMap<>();

        for (Note note : noteList) {
            for (String declancheur : termesDeclencheurs) {
                int occurrence = 0;
                occurrence = StringUtils.countOccurrencesOf(note.getNote().toLowerCase(), declancheur.toLowerCase());
                if (occurrence != 0) {
                    if (mapOccurence.containsKey(declancheur)) {
                        mapOccurence.put(declancheur, mapOccurence.get(declancheur) + occurrence);
                    } else {
                        mapOccurence.put(declancheur, occurrence);
                    }
                }
            }
        }
        return mapOccurence;
    }

    @Override
    public String determineRiskLevel (Map<String, Integer> mapDeclencheurs, Patient patient) {
        //Si patient est homme et a moins de 30ans et a 5 term declencheurs
        //Si patient est femme et a moins de 30ans et a 7 term declencheurs
        //SI patient a plus de 30ans alors et a 8 term declencheurs
        if(patient.getSex() == 'M' && ageOfPatient(patient) < 30 && mapDeclencheurs.size() >= 5
                || (patient.getSex() == 'F' && ageOfPatient(patient) < 30 && mapDeclencheurs.size() >= 7)
                || (ageOfPatient(patient) >= 30 && mapDeclencheurs.size() >= 8)) {
            return "Early onset";
        }

        //Si le patient est un homme et a moins de 30ans et a 3 term declencheurs
        //Si le patient est une femme et a moins de 30ans et a 4 term declencheurs
        //Si le patient a plus de 30ans, il faut 6 term declencheurs
        if(patient.getSex() == 'M' && ageOfPatient(patient) < 30 && mapDeclencheurs.size() >= 3
                || (patient.getSex() == 'F' && ageOfPatient(patient) < 30 && mapDeclencheurs.size() >= 4)
                || (ageOfPatient(patient) >= 30 && mapDeclencheurs.size() >= 6)) {
            return "In Danger";
        }

        //Si le patient à 2 term déclencheurs et age de plus de 30ans #Borderline
        if(mapDeclencheurs.size() >= 2 && ageOfPatient(patient) > 30) {
            return "Borderline";
        }

        return "None";
    }


    private int ageOfPatient(Patient patient) {
        return Period.between(patient.getDob(), LocalDate.now()).getYears();
    }

    @Override
    public Patient getPatientByPatId(String patId) {
        Optional<Patient> patient = patientRepository.findById(Long.parseLong(patId));
        return patient.orElse(null);
    }

    @Override
    public Patient getPatientByFamilyName(String familyName) {
        return patientRepository.findByFamilyName(familyName);
    }

    @Override
    public String generateReport(String riskLevel, Patient patient) {
        return "Patient: " + patient.getFirstName() + " " + patient.getLastName() + " (age " + ageOfPatient(patient)+ ")"
                + " diabetes assessment is : " + riskLevel;
    }
}
