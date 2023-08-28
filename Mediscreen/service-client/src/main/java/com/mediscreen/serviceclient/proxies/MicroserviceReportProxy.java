package com.mediscreen.serviceclient.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "${report-service.name}", url = "${report-service.url}")
public interface MicroserviceReportProxy {
    @PostMapping(value = "/assess/id")
    String getRiskByPatientId(@RequestParam(name = "patId") String patId);

    @PostMapping(value = "/assess/familyName")
    String getRiskByPatientFamilyName(@RequestParam(name = "familyName") String familyName);
}
