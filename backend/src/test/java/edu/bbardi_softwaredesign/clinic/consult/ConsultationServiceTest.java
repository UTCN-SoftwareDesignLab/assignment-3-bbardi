package edu.bbardi_softwaredesign.clinic.consult;

import edu.bbardi_softwaredesign.clinic.TestCreationFactory;

import edu.bbardi_softwaredesign.clinic.consult.mapper.ConsultationMapper;
import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import edu.bbardi_softwaredesign.clinic.consult.repository.ConsultationRepository;
import edu.bbardi_softwaredesign.clinic.consult.service.ConsultationService;
import edu.bbardi_softwaredesign.clinic.notification.NotificationSender;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.planning.TimeSlotSpecification;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.repository.RoleRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMATTER;
import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.randomLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConsultationServiceTest {

    @InjectMocks
    private ConsultationService consultationService;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private NotificationSender notificationSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consultationService = new ConsultationService(consultationRepository,userRepository,patientRepository,consultationMapper,timeSlotRepository,notificationSender);
    }

    @Test
    void findAll() {
        List<Consultation> consultations = TestCreationFactory.listOf(Consultation.class);
        when(consultationRepository.findAll()).thenReturn(consultations);
        List<ConsultationDTO> all = consultationService.findAll();
        Assertions.assertEquals(all.size(), consultations.size());
    }

    @Test
    void create() {
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        User user = (User) TestCreationFactory.listOf(User.class).get(0);
        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(user);
        consultation.setPatient(patient);
        ConsultationDTO consultationDTO = ConsultationDTO.builder()
                .id(consultation.getId())
                .patientName(consultation.getPatient().getName())
                .doctorName(consultation.getDoctor().getFullName())
                .time(consultation.getTime().getTimestamp())
                .date(DATE_FORMATTER.format(consultation.getDate()))
                .build();
        when(consultationMapper.fromDTO(consultationDTO)).thenReturn(consultation);
        when(consultationMapper.toDTO(consultation)).thenReturn(consultationDTO);
        when(userRepository.findUserByFullName(consultationDTO.getDoctorName())).thenReturn(Optional.of(user));
        when(patientRepository.findPatientByName(consultationDTO.getPatientName())).thenReturn(Optional.of(patient));
        when(timeSlotRepository.findOne(TimeSlotSpecification.timeSlotLike(any()))).thenReturn(Optional.of(new TimeSlot(randomLong(),consultationDTO.getTime())));
        when(consultationRepository.save(any())).thenReturn(consultation);
        Assertions.assertEquals(consultationService.create(consultationDTO), consultationDTO);
    }

    @Test
    void delete() {
        ConsultationDTO patient = (ConsultationDTO) TestCreationFactory.listOf(ConsultationDTO.class).get(0);
        consultationService.delete(patient.getId());
    }

    @Test
    void edit(){
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        User user = (User) TestCreationFactory.listOf(User.class).get(0);
        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(user);
        consultation.setPatient(patient);
        ConsultationDTO consultationDTO = ConsultationDTO.builder()
                .id(consultation.getId())
                .patientName(consultation.getPatient().getName())
                .doctorName(consultation.getDoctor().getFullName())
                .time(consultation.getTime().getTimestamp())
                .date(DATE_FORMATTER.format(consultation.getDate()))
                .build();
        when(userRepository.findUserByFullName(consultationDTO.getDoctorName())).thenReturn(Optional.of(user));
        when(patientRepository.findPatientByName(consultationDTO.getPatientName())).thenReturn(Optional.of(patient));
        when(timeSlotRepository.findOne(TimeSlotSpecification.timeSlotLike(any()))).thenReturn(Optional.of(consultation.getTime()));
        when(consultationMapper.fromDTO(consultationDTO)).thenReturn(consultation);
        when(consultationMapper.toDTO(consultation)).thenReturn(consultationDTO);
        when(consultationRepository.findById(consultation.getId())).thenReturn(Optional.of(consultation));
        when(consultationRepository.save(consultation)).thenReturn(consultation);
        Assertions.assertEquals(consultationService.update(consultationDTO.getId(),consultationDTO),consultationDTO);
    }
}
