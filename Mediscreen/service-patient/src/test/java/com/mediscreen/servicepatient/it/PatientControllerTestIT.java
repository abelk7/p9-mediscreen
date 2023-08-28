package com.mediscreen.servicepatient.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.servicepatient.exception.PatientNotFoundException;
import com.mediscreen.servicepatient.model.Patient;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class PatientControllerTestIT {
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

    @Order(1)
    @Test
    void test_getAllPatient() throws Exception {
        mockMvc
                .perform(get("/patient/"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void test_getPatient() throws Exception {
        mockMvc
                .perform(get("/patient/{id}", 1))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("TestNone"))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void test_getPatient_should_throw_Exception() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/patient/{id}", 50))
                .andDo(print())
                .andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResolvedException() instanceof PatientNotFoundException);
    }

    @Test
    @Order(4)
    void test_postPatient() throws Exception {
        Patient patient1 = new Patient(null, "Family1", "Given1", LocalDate.of(1989, 10, 18), 'M', null, null);
        mockMvc
                .perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient1)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    void test_updatePatient() throws Exception {
        Patient patient1 = new Patient(1L, "FamilyUpdated", "GivenUpdated", LocalDate.of(1989, 10, 18), 'M', "33 Rue Stertarlin", "456-321-789");
        mockMvc
                .perform(put("/patient/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient1)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void test_deletePatient() throws Exception {
        mockMvc
                .perform(delete("/patient/{id}", 4))
                .andExpect(status().isOk());
    }

}
