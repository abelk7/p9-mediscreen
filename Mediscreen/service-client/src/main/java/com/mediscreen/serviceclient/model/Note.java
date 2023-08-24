package com.mediscreen.serviceclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Note {
    private String id;
    private String patId;
    private String patient;
    private String note;
    private String docteur;
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
