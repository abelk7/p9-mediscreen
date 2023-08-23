package com.mediscreen.servicenotes;

import com.mediscreen.servicenotes.controller.NoteController;
import com.mediscreen.servicenotes.model.Note;
import com.mediscreen.servicenotes.service.INoteService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {
    @InjectMocks
    NoteController noteController;
    @Mock
    INoteService noteService;
    @Mock
    private MongoOperations mongoOperations;

    @Test
    @Order(1)
    void test_getNotes() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1", "docteur1");
        Note note2 = new Note("4", "1", "TestPatient2", "Test notes exemple...2", "docteur1");
        Note note3 = new Note("6", "2", "TestPatient3", "Test notes exemple...3", "docteur1");

        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);

        when(noteService.findAll()).thenReturn(noteList);

        List<Note> resultList = noteController.getNotes();

        assertThat(resultList).isNotNull();
        assertThat(resultList).isNotEmpty();
    }

    @Test
    @Order(2)
    void test_getAllNoteOfPatient() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1", "docteur1");
        Note note2 = new Note("2", "1", "TestPatient1", "Test notes exemple...2", "docteur1");

        noteList.add(note1);
        noteList.add(note2);

        when(noteService.findBySpecificPatId(anyString())).thenReturn(noteList);

        List<Note> resultList = noteController.getAllNoteOfPatient("4");

        assertThat(resultList).isNotNull();
        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(2);
    }

    @Test
    @Order(3)
    void test_getSpecificNote() {
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1", "docteur1");

        when(noteService.findNoteById(anyString())).thenReturn(note1);

        Note result = noteController.getSpecificNote("1");

        assertThat(result).isNotNull();
        assertThat(result.getNote()).isEqualTo(note1.getNote());
    }

    @Test
    @Order(4)
    void test_postNote() {
        Note note1 = new Note(null, "1", "TestPatient1", "Test notes exemple...1", "docteur1");

        when(noteService.save(any(Note.class))).thenReturn(note1);

        ResponseEntity<?> result = noteController.postNote(note1);

        assertThat(result).isNotNull();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(5)
    void test_updateNote() {
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1", "docteur1");

        when(noteService.save(any(Note.class))).thenReturn(note1);

        ResponseEntity<?> result = noteController.postNote(note1);

        assertThat(result).isNotNull();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
