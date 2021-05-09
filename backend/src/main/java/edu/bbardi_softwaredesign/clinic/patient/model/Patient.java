package edu.bbardi_softwaredesign.clinic.patient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMAT;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,length = 8)
    private String idCard;

    @Column(nullable = false, length = 13)
    private String personalNumericCode;

    @Column(nullable = false)
    private String address;
}
