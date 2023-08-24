package com.mediscreen.servicereport.model;

import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String patId;
    private String patient;
    private String note;
    private String docteur;
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    public Note(String id, String patId, String patient, String note, String docteur) {
        this.id = id;
        this.patId = patId;
        this.patient = patient;
        this.note = note;
        this.docteur = docteur;
    }
}
