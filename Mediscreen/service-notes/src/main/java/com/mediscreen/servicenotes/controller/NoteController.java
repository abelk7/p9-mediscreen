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


//    @PostMapping(value = "/patHistory/add")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public Note postNote(@RequestBody Note note) {
//        if(!StringUtils.hasLength(note.getNote())) {
//            throw new NoteEmptyException("Impossible d'enregistrer une note vide.");
//        }
//        Note note1 = noteRepository.save(note);
//        return note1;
//    }

    @PostMapping(value = "/patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<?> postNote(@RequestBody Note note) {
        try {
            if(!StringUtils.hasLength(note.getNote())) {
                throw new NoteEmptyException("Impossible d'enregistrer une note vide.");
            }
            Note note1 = noteService.save(note);
            return new ResponseEntity<>(note1, HttpStatus.CREATED);
        } catch (NoteEmptyException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

//        @RequestMapping(
//            path = "/patHistory/add",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = {
//                    MediaType.APPLICATION_ATOM_XML_VALUE,
//                    MediaType.APPLICATION_JSON_VALUE
//            })
//    public Note postNote2(Note note) {
//        System.out.println("coucou");
//        System.out.println(note.toString());
//        Note note1 = noteRepository.save(note);
//        System.out.println(note1);
//        return note1;
//    }


}
