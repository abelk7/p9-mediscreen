package com.mediscreen.servicenotes.service;

import com.mediscreen.servicenotes.model.Note;

import java.util.List;

public interface INoteService {
    List<Note> findAll();
    List<Note> findBySpecificPatId(String patid);
    Note findNoteById(String id);

    Note save(Note note);

}
