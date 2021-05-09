package edu.bbardi_softwaredesign.clinic.patient;

import edu.bbardi_softwaredesign.clinic.BaseControllerTest;
import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.patient.service.PatientService;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.*;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTest extends BaseControllerTest {
    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void findAllPatients() throws Exception{
        List<PatientDTO> patientDTOList = TestCreationFactory.listOf(PatientDTO.class);
        when(patientService.findAll()).thenReturn(patientDTOList);
        ResultActions result = mockMvc.perform(get(PATIENT));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTOList));

    }

    @Test
    void createPatient() throws Exception{
        PatientDTO patient = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        when(patientService.create(patient)).thenReturn(patient);
        ResultActions result = performPostWithRequestBody(PATIENT,patient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }

    @Test
    void deletePatient() throws Exception{
        PatientDTO patient =(PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        ResultActions result = performDeleteWithPathVariable(PATIENT+ENTITY,patient.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void editPatient() throws Exception{
        PatientDTO patient =(PatientDTO) listOf(PatientDTO.class).get(0);
        when(patientService.update(patient.getId(),patient)).thenReturn(patient);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(PATIENT+ENTITY,patient.getId().toString(),patient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }
}
