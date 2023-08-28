package com.mediscreen.servicereport.it;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class ReportControllerTestIT {
    private final WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @Order(1)
    void test_getRiskByPatientId() throws Exception {
        mockMvc
                .perform(post("/assess/id").param("patId", String.valueOf(2)))
                .andDo(print())
                .andExpect(content().string("Patient: Test TestBorderline (age 78) diabetes assessment is : None"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void test_getRiskByPatientFamilyName() throws Exception {
        mockMvc
                .perform(post("/assess/familyName").param("familyName", "FamilyUpdated"))
                .andDo(print())
                .andExpect(content().string("Patient: GivenUpdated FamilyUpdated (age 33) diabetes assessment is : None"))
                .andExpect(status().isOk());
    }

}
