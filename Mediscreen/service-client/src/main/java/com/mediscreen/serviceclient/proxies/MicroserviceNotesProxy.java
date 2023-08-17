package com.mediscreen.serviceclient.proxies;

import com.mediscreen.serviceclient.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "${notes-service.name}", url = "${notes-service.url}")
public interface MicroserviceNotesProxy {

    @GetMapping(value = "/notes/")
    List<Note> gellAllNotes();
//    @GetMapping(value = "/notes/{id}")
//    List<Note> getListHistoryNoteOfPatient(@PathVariable String patId);
    @GetMapping(value = "/notes/")
    List<Note> getListHistoryNoteOfPatient();
}
