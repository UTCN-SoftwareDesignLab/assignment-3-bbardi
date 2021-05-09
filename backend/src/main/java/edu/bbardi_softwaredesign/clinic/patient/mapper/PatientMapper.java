package edu.bbardi_softwaredesign.clinic.patient.mapper;

import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMAT;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "dateOfBirth", dateFormat = DATE_FORMAT)
    PatientDTO toDto(Patient patient);
    @Mapping(target = "dateOfBirth", dateFormat = DATE_FORMAT)
    Patient fromDto(PatientDTO patient);
}
