package edu.bbardi_softwaredesign.clinic.planning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
    private Long id;
    private String timestamp;
}
