package com.mediscreen.servicenotes.repository.impl;

import com.mediscreen.servicenotes.model.Note;
import com.mediscreen.servicenotes.repository.NoteRepository;
import com.mediscreen.servicenotes.service.INoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> findBySpecificPatId(String patid) {
        return noteRepository.findBySpecificPatId(patid);
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }
}
