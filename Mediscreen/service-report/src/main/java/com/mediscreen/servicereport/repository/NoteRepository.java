package com.mediscreen.servicereport.repository;

import com.mediscreen.servicereport.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    @Query("{'patId' : ?0 }")
    List<Note> findBySpecificPatId(String patid);

}
