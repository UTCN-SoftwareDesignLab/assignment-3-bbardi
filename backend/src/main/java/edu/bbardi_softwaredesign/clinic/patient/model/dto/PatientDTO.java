package edu.bbardi_softwaredesign.clinic.patient.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long id;
    private String dateOfBirth;
    private String name;
    private String idCard;
    private String personalNumericCode;
    private String address;
}
