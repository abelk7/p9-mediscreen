package com.mediscreen.serviceclient.proxies;

import com.mediscreen.serviceclient.model.Note;
import org.aspectj.weaver.ast.Not;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Component
@FeignClient(name = "${notes-service.name}", url = "${notes-service.url}")
public interface MicroserviceNotesProxy {

    @GetMapping(value = "/notes/")
    List<Note> gellAllNotes();
//    @GetMapping(value = "/notes/{id}")
//    List<Note> getListHistoryNoteOfPatient(@PathVariable String patId);
    @GetMapping(value = "/notes/{patId}")
    List<Note> getListHistoryNoteOfPatient(@PathVariable String patId);

    @PostMapping(value = "/patHistory/add")
    Note saveNote(@RequestBody Note note);

    @GetMapping(value = "/note/{id}")
    Note findNoteById(@PathVariable String id);
}
