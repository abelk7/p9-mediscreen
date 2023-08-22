package com.mediscreen.servicenotes.service.impl;

import com.mediscreen.servicenotes.model.Note;
import com.mediscreen.servicenotes.repository.NoteRepository;
import com.mediscreen.servicenotes.service.INoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService implements INoteService {

    @Autowired
    private MongoOperations mongoOperations;
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
    public Note findNoteById(String id) {
//        return noteRepository.findNoteById(id);
        return mongoOperations.findOne(
                Query.query(Criteria.where("_id").is(id)),Note.class
        );
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }
}
