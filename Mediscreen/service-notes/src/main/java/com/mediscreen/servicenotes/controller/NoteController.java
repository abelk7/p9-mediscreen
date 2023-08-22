package com.mediscreen.servicenotes.controller;

import com.mediscreen.servicenotes.exception.NoteEmptyException;
import com.mediscreen.servicenotes.model.Note;
import com.mediscreen.servicenotes.repository.NoteRepository;
import com.mediscreen.servicenotes.service.INoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class NoteController {

    private final INoteService noteService;
    private NoteRepository noteRepository;

    @GetMapping(value = "/notes/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Note> getNotes() {
        return noteService.findAll();
    }

    @GetMapping(value = "/notes/{patId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Note> getAllNoteOfPatient(@PathVariable String patId) {
        //        return new ResponseEntity<>(noteList, HttpStatus.OK);
        return noteService.findBySpecificPatId(patId);
    }

    @GetMapping(value = "/note/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Note getSpecificNote(@PathVariable String id){
        return noteService.findNoteById(id);
    }


    @PostMapping(value = "/patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<?> postNote(@RequestBody Note note) {
        try {
            if(!StringUtils.hasLength(note.getNote())) {
                throw new NoteEmptyException("Impossible d'enregistrer une note vide.");
            }
            Note note1 = noteService.save(note);
            if(note.getId() == null) {
                return new ResponseEntity<>(note1, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(note1, HttpStatus.OK);
            }

        } catch (NoteEmptyException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
