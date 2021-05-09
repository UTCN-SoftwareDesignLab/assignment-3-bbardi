package edu.bbardi_softwaredesign.clinic.consult.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ConsultationDTO {
    private Long id;
    private String description;
    private String patientName;
    private String doctorName;
    private String time;
    private String date;
}
