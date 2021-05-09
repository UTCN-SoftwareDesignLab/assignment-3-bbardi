package edu.bbardi_softwaredesign.clinic.planning.mapper;

import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.model.dto.TimeSlotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {
    TimeSlot fromDTO(TimeSlotDTO timeSlotDTO);
    TimeSlotDTO toDTO(TimeSlot timeSlot);
}
