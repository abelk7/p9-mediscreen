package com.mediscreen.serviceclient.proxies;

import com.mediscreen.serviceclient.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Component
@FeignClient(name = "${patient-service.name}", url = "${patient-service.url}")
public interface MicroservicePatientProxy {
    @GetMapping(value = "/patient/")
    List<Patient> listPatients();

    @GetMapping(value = "/patient/{id}")
    Patient getPatient(@PathVariable long id);

    @PostMapping(value = "/patient/add")
    Patient postPatient(@RequestBody Patient patient);

    @PutMapping(value = "/patient/{id}")
    Patient updatePatient(@PathVariable long id, @RequestBody Patient patientNewValues);

    @DeleteMapping(value = "/patient/{id}")
    void deletePatient(@PathVariable long id);
}
