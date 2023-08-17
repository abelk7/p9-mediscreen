package com.mediscreen.serviceclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Long id;
    @NotEmpty(message = "Le nom du patient ne peut pas être vide.")
    private String family;
    @NotEmpty(message = "Le prenom du patient ne peut pas être vide.")
    private String given;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La date de naissance du patient ne peut pas être vide")
    private LocalDate dob;
    @NotNull(message = "La Sexe du patient ne peut pas être vide")
    private char sex;
    private String address;
    private String phone;
}
