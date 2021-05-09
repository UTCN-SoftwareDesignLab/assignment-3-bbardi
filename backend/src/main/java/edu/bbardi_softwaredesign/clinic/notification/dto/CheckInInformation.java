package edu.bbardi_softwaredesign.clinic.notification.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckInInformation {
    private String doctorUsername;
    private String patientName;
}
