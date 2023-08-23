package com.mediscreen.servicenotes;

import com.mediscreen.servicenotes.model.Note;
import com.mediscreen.servicenotes.repository.NoteRepository;
import com.mediscreen.servicenotes.service.INoteService;
import com.mediscreen.servicenotes.service.impl.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class NoteServiceTest {
    private INoteService noteService;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private MongoOperations mongoOperations;

    @BeforeEach
    private void setup() {
        noteService = new NoteService(mongoOperations, noteRepository);
    }

    @Order(1)
    @Test
    void test_findAllNotes() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1");
        Note note2 = new Note("2", "1", "TestPatient1", "Test notes exemple...2");
        Note note3 = new Note("3", "2", "TestPatient2", "Test notes exemple...3");

        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);

        when(noteRepository.findAll()).thenReturn(noteList);

        List<Note> resultList = noteService.findAll();

        assertThat(resultList).isNotNull();
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(3);
    }

    @Order(2)
    @Test
    void test_findBySpecificPatId() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1");
        Note note2 = new Note("2", "1", "TestPatient1", "Test notes exemple...2");

        noteList.add(note1);
        noteList.add(note2);

        when(noteRepository.findBySpecificPatId(anyString())).thenReturn(noteList);

        List<Note> resultList = noteService.findBySpecificPatId("1");

        assertThat(resultList).isNotNull();
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(2);
    }

    @Order(3)
    @Test
    void test_findNoteById() {
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1");

        when(mongoOperations.findOne(any(Query.class), any())).thenReturn(note1);

        Note result = noteService.findNoteById("1");

        assertThat(result).isNotNull();
        assertThat(result.getNote()).isEqualTo(note1.getNote());
    }

    @Order(4)
    @Test
    void test_save() {
        Note note1 = new Note();
        note1.setId("1");
        note1.setPatId("1");
        note1.setPatient("TestPatient1");
        note1.setNote("Test notes exemple...1");

        when(noteRepository.save(any(Note.class))).thenReturn(note1);

        Note result = noteService.save(note1);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(note1);
    }
}
