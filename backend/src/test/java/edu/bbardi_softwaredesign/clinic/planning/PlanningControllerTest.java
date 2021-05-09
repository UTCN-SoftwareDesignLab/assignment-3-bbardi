package edu.bbardi_softwaredesign.clinic.planning;

import edu.bbardi_softwaredesign.clinic.BaseControllerTest;
import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.patient.PatientController;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.patient.service.PatientService;
import edu.bbardi_softwaredesign.clinic.planning.model.dto.TimeSlotDTO;
import edu.bbardi_softwaredesign.clinic.planning.service.PlanningService;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.randomString;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlanningControllerTest extends BaseControllerTest {
    @InjectMocks
    private PlanningController planningController;

    @Mock
    private PlanningService planningService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        planningController = new PlanningController(planningService);
        mockMvc = MockMvcBuilders.standaloneSetup(planningController).build();
    }

    @Test
    void findAllDoctors() throws Exception{
        List<String> doctorNames = TestCreationFactory.listOf(String.class);
        when(planningService.getDoctors()).thenReturn(doctorNames);
        ResultActions result = mockMvc.perform(get(PLANNING + DOCTORS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(doctorNames));

    }
    @Test
    void findAllAvailableSlots() throws Exception{
        List<TimeSlotDTO> timeSlotDTOList = TestCreationFactory.listOf(TimeSlotDTO.class);
        when(planningService.getSlots(any(),any())).thenReturn(timeSlotDTOList);
        ResultActions result = mockMvc.perform(get(PLANNING + SLOTS).param("doctorName",randomString()).param("date",randomString()));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(timeSlotDTOList));

    }
}
