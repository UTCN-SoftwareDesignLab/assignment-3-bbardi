package edu.bbardi_softwaredesign.clinic.notification.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleInformation {
    private String doctorUsername;
    private String date;
    private String hour;
}
