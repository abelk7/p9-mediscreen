package com.mediscreen.serviceclient.controller;

import com.mediscreen.serviceclient.model.Note;
import com.mediscreen.serviceclient.model.Patient;
import com.mediscreen.serviceclient.proxies.MicroserviceNotesProxy;
import com.mediscreen.serviceclient.proxies.MicroservicePatientProxy;
import com.mediscreen.serviceclient.proxies.MicroserviceReportProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {

    private final MicroservicePatientProxy microservicePatientProxy;
    private  final MicroserviceNotesProxy microserviceNotesProxy;
    private  final MicroserviceReportProxy microserviceReportProxy;

    public PatientController(MicroservicePatientProxy microservicePatientProxy, MicroserviceNotesProxy microserviceNotesProxy, MicroserviceReportProxy microserviceReportProxy) {
        this.microservicePatientProxy = microservicePatientProxy;
        this.microserviceNotesProxy = microserviceNotesProxy;
        this.microserviceReportProxy = microserviceReportProxy;
    }

    /**
     * Get list if patients
     * @param model
     * @return
     */
    @GetMapping({"/", "/patient/", "/list"})
    public ModelAndView index(ModelMap model) {
        List<Patient> patients = microservicePatientProxy.listPatients();
        model.addAttribute("listPatients", patients);
        return new ModelAndView("list", model);
    }

    /**
     * Get detail of patient
     * @param model
     * @param id id of patient
     * @return
     */
    @GetMapping(value = "/patient/{id}")
    public ModelAndView getPatient(ModelMap model, @PathVariable long id) {
        Patient patient = microservicePatientProxy.getPatient(id);
        List<Note> notesList =microserviceNotesProxy.getListHistoryNoteOfPatient(Long.toString(id));
        model.addAttribute("patient", patient);
        model.addAttribute("notes", notesList);
        model.addAttribute("newNote", new Note(null ,Long.toString(patient.getId()), patient.getLastName(), "", "docteur1"));
        return new ModelAndView("patient", model);
    }

    /**
     * get form post patient
     * @param model
     * @return
     */
    @GetMapping(value = "/postPatient")
    public ModelAndView showFormPostPatient(ModelMap model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return new ModelAndView("post-patient", model);
    }

    /**
     * get form update patient
     * @param model
     * @param id id patient
     * @return
     */
    @GetMapping(value = "/patient/update/{id}")
    public ModelAndView showFormUpdatePatient(ModelMap model, @PathVariable long id) {
        Patient patient = microservicePatientProxy.getPatient(id);
        model.addAttribute("patient", patient);
        return new ModelAndView("update-patient", model);
    }

    /**
     * Save patient informations
     * @param patientNewValues
     * @param result
     * @param model
     * @return
     */
    @PostMapping(value = "/patient/save")
    public ModelAndView savePatient(@Valid Patient patientNewValues, BindingResult result, ModelMap model) {
        if(patientNewValues.getId() == null) {
            if(result.hasErrors()) {
                model.addAttribute("patient", patientNewValues);
                return new ModelAndView("post-patient", model);
            }
            microservicePatientProxy.postPatient(patientNewValues);
            model.addAttribute("message", "Un nouveau patient à été ajouté");
        } else {
            if(result.hasErrors()) {
                model.addAttribute("patient", patientNewValues);
                return new ModelAndView("update-patient", model);
            }
            microservicePatientProxy.updatePatient(patientNewValues.getId(), patientNewValues);
            model.addAttribute("message", "Le patient à été mis à jour");
        }
        return new ModelAndView("redirect:/list", model);
    }

    /**
     * Add note to patient
     * @param newNote
     * @param result
     * @param model
     * @return
     */
    @PostMapping(value = "/note/save")
    public ModelAndView saveNotePatient(@Valid Note newNote, BindingResult result, ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = microservicePatientProxy.getPatient( Long.parseLong(newNote.getPatId()));
        List<Note> notesList = microserviceNotesProxy.getListHistoryNoteOfPatient(newNote.getPatId());

        if(result.hasErrors()) {
            model.addAttribute("newNote", newNote);
            model.addAttribute("patient", patient);
            model.addAttribute("message", "La note ne peut pas être vide...");
            return new  ModelAndView("redirect:/patient/"+patient.getId(), model);

        }

        newNote.setDocteur(auth.getName());
        Note note = microserviceNotesProxy.saveNote(newNote);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notesList);
        model.addAttribute("newNote", new Note(null,Long.toString(patient.getId()), patient.getLastName(), "", auth.getName()));
        if(newNote.getId() == null) {
            model.addAttribute("message", "La note à été ajouté au patient");
        }else {
            model.addAttribute("message", "La note à été mis à jour");
        }

        return new ModelAndView("redirect:/patient/"+patient.getId(), model);
    }


    /**
     * get form update note of patient
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/patient/note/{id}")
    public ModelAndView showFormUpdateNotePatient(ModelMap model, @PathVariable String id) {
        Note note = microserviceNotesProxy.findNoteById(id);
        model.addAttribute("note", note);
        return new ModelAndView("patient-note", model);
    }

    /**
     * Delete a patient
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/patient/delete/{id}")
    public ModelAndView deletePatient(@PathVariable long id, ModelMap model) {
        microservicePatientProxy.deletePatient(id);
        model.addAttribute("message", "Le patient à été supprimé");
        return new ModelAndView("redirect:/list", model);
    }

    /**
     * Generate report diabete of patient
     * @param patient
     * @param model
     * @return
     */
    @PostMapping(value = "/probadiabete/patId")
    public ModelAndView determineProbaDiabete(Patient patient, ModelMap model) {
        String result = microserviceReportProxy.getRiskByPatientId(patient.getId().toString());
        if(result.contains(" None")) {
            model.addAttribute("color", "green");
        } else if(result.contains(" Borderline")) {
            model.addAttribute("color", "blue");
        } else if(result.contains(" In Danger")) {
            model.addAttribute("color", "yellow");
        } else if(result.contains(" Early onset")) {
            model.addAttribute("color", "red");
        }
        model.addAttribute("analyse", result);
        return new ModelAndView("proba-diabete", model);
    }
}
