package edu.bbardi_softwaredesign.clinic.patient;

import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.patient.mapper.PatientMapper;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.patient.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMATTER;
import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.randomLong;
import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.randomString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository,patientMapper);
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        when(patientRepository.findAll()).thenReturn(patients);
        List<PatientDTO> all = patientService.findAll();
        Assertions.assertEquals(all.size(), patients.size());
    }

    @Test
    void createPatient() {
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        Patient actPatient = Patient.builder()
                .id(patient.getId())
                .name(patient.getName())
                .address(patient.getAddress())
                .idCard(patient.getAddress())
                .personalNumericCode(patient.getPersonalNumericCode())
                .dateOfBirth(LocalDate.parse(patient.getDateOfBirth(),DATE_FORMATTER))
                .build();
        when(patientMapper.fromDto(patient)).thenReturn(actPatient);
        when(patientMapper.toDto(any())).thenReturn(patient);
        when(patientRepository.save(any())).thenReturn(any());
        Assertions.assertEquals(patientService.create(patient), patient);
    }

    @Test
    void deletePatient() {
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        patientService.delete(patient.getId());
    }

    @Test
    void editPatient(){
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        Patient actPatient = Patient.builder()
                .id(patient.getId())
                .name(patient.getName())
                .address(patient.getAddress())
                .idCard(patient.getAddress())
                .personalNumericCode(patient.getPersonalNumericCode())
                .dateOfBirth(LocalDate.parse(patient.getDateOfBirth(),DATE_FORMATTER))
                .build();
        when(patientMapper.fromDto(patient)).thenReturn(actPatient);
        when(patientMapper.toDto(actPatient)).thenReturn(patient);
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(actPatient));
        when(patientRepository.save(actPatient)).thenReturn(actPatient);
        Assertions.assertEquals(patientService.update(patient.getId(),patient),patient);
    }
}
