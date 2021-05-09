package edu.bbardi_softwaredesign.clinic.patient;

import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.patient.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceIntegrationTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    @AfterEach
    void setUp(){
        patientRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        patients.forEach(u-> u.setId(null));
        patientRepository.saveAll(patients);
        List<PatientDTO> patientDTOS = patientService.findAll();
        Assertions.assertEquals(patientDTOS.size(),patients.size());
    }

    @Test
    void create(){
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        patient.setId(null);
        Assertions.assertNotNull(patientService.create(patient).getId());
    }

    @Test
    void delete(){
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        patient.setId(null);
        patient = patientRepository.save(patient);
        patientService.delete(patient.getId());
    }

    @Test
    void update(){
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        patient.setId(null);
        patient = patientService.create(patient);
        patient.setAddress("ALTERED_ADDRESS");
        Assertions.assertEquals(patientService.update(patient.getId(),patient).getAddress(),"ALTERED_ADDRESS");

    }
}
