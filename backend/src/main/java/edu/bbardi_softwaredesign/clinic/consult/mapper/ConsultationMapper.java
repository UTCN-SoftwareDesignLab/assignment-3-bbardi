package edu.bbardi_softwaredesign.clinic.consult.mapper;

import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMAT;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    @Mapping(target = "date", dateFormat = DATE_FORMAT)
    @Mapping(target = "patient.name", source = "patientName")
    @Mapping(target = "doctor.fullName", source = "doctorName")
    @Mapping(target = "time.timestamp",source = "time")
    Consultation fromDTO(ConsultationDTO consultation);
    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "doctor.fullName", target = "doctorName")
    @Mapping(target = "date", dateFormat = DATE_FORMAT)
    @Mapping(source = "time.timestamp", target = "time")
    ConsultationDTO toDTO(Consultation consultation);
}
