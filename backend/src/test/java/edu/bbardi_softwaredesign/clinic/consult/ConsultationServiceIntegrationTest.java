package edu.bbardi_softwaredesign.clinic.consult;

import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.consult.mapper.ConsultationMapper;
import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import edu.bbardi_softwaredesign.clinic.consult.repository.ConsultationRepository;
import edu.bbardi_softwaredesign.clinic.consult.service.ConsultationService;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ConsultationServiceIntegrationTest {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @BeforeEach
    @AfterEach
    void setUp(){
        consultationRepository.deleteAll();
        patientRepository.deleteAll();
        userRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<Consultation> consultations = TestCreationFactory.listOf(Consultation.class);
        for(Consultation consultation : consultations){
            consultation.setDoctor(userRepository.save(consultation.getDoctor()));
            consultation.setPatient(patientRepository.save(consultation.getPatient()));
            consultation.setTime(timeSlotRepository.save(consultation.getTime()));
        }
        consultationRepository.saveAll(consultations);
        List<ConsultationDTO> consultationDTOList = consultationService.findAll();
        Assertions.assertEquals(consultationDTOList.size(),consultations.size());
    }

    @Test
    void create(){
        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setId(null);
        consultation.setDoctor(userRepository.save(consultation.getDoctor()));
        consultation.setPatient(patientRepository.save(consultation.getPatient()));
        consultation.setTime(timeSlotRepository.save(consultation.getTime()));
        ConsultationDTO consultationDTO = consultationMapper.toDTO(consultation);
        Assertions.assertNotNull(consultationService.create(consultationDTO).getId());
    }

    @Test
    void delete(){
        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(userRepository.save(consultation.getDoctor()));
        consultation.setPatient(patientRepository.save(consultation.getPatient()));
        consultation.setTime(timeSlotRepository.save(consultation.getTime()));
        consultation.setId(null);
        consultationRepository.save(consultation);
        consultationService.delete(consultation.getId());
    }

    @Test
    void update(){
        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setId(null);
        consultation.setDoctor(userRepository.save(consultation.getDoctor()));
        consultation.setPatient(patientRepository.save(consultation.getPatient()));
        consultation.setTime(timeSlotRepository.save(consultation.getTime()));
        consultation = consultationRepository.save(consultation);
        ConsultationDTO consultationDTO = consultationMapper.toDTO(consultation);
        consultationDTO.setDescription("EDITED");
        Assertions.assertEquals(consultationService.update(consultation.getId(),consultationDTO).getDescription(),"EDITED");
    }

}
