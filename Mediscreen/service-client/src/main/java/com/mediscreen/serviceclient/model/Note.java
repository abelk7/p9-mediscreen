package com.mediscreen.serviceclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private String id;
    private String patId;
    private String patient;
    private String note;
    private String docteur;

}
