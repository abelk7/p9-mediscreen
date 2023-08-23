package com.mediscreen.servicenotes.it;

import com.mediscreen.servicenotes.model.Note;
import com.mediscreen.servicenotes.service.INoteService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteServiceTestIT {
    @Autowired
    private INoteService noteService;

    @Order(1)
    @Test
    void test_findAllNotes() {
        List<Note> noteList = noteService.findAll();

        assertThat(noteList).isNotNull();
        assertThat(noteList).isNotEmpty();
    }

    @Order(2)
    @Test
    void test_findBySpecificPatId() {
        List<Note> noteList = noteService.findBySpecificPatId("1");

        assertThat(noteList).isNotNull();
        assertThat(noteList).isNotEmpty();
    }

    @Order(3)
    @Test
    void test_findNoteById() {
        Note result = noteService.findNoteById("1");

        assertThat(result).isNotNull();
    }

    @Order(4)
    @Test
    void test_save() {
        Note note1 = new Note("1", "1", "TestPatient1", "Test notes exemple...1");
        Note result = noteService.save(note1);
        assertThat(result).isNotNull();
    }
}
