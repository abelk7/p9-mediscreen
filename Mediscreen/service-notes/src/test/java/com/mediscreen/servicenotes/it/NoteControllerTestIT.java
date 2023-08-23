package com.mediscreen.servicenotes.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.servicenotes.exception.NoteEmptyException;
import com.mediscreen.servicenotes.model.Note;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class NoteControllerTestIT {

    private final WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @Order(1)
    void test_getNotes() throws Exception {
        mockMvc
                .perform(get("/notes/"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void test_getAllNoteOfPatient() throws Exception {
        mockMvc
                .perform(get("/notes/{patId}", 1))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void test_getSpecificNote() throws Exception {
        mockMvc
                .perform(get("/note/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void test_postNote() throws Exception {
        Note note1 = new Note(null, "9", "TestPatient", "Test notes exemple...999", "docteur2");
        mockMvc
                .perform(post("/patHistory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note1)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    void test_postNote_shouldThrows_Exception_if_note_Empty() throws Exception {
        Note note1 = new Note("1", "9", "TestPatient", "","docteur2");
       MvcResult mvcResult = mockMvc
                .perform(post("/patHistory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note1)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
                //.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));
        System.out.println("--------------");
        System.out.println(mvcResult.getResolvedException());
        assertTrue(mvcResult.getResolvedException() instanceof NoteEmptyException);

    }

}
