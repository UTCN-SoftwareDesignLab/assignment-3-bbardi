package edu.bbardi_softwaredesign.clinic.consult;

import edu.bbardi_softwaredesign.clinic.BaseControllerTest;
import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import edu.bbardi_softwaredesign.clinic.consult.service.ConsultationService;
import edu.bbardi_softwaredesign.clinic.user.UserController;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultationControllerTest extends BaseControllerTest {
    @InjectMocks
    private ConsultationController consultationController;

    @Mock
    private ConsultationService consultationService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        consultationController = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();
    }

    @Test
    void findAll() throws Exception{
        List<ConsultationDTO> consultationDTOList = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findAll()).thenReturn(consultationDTOList);
        ResultActions result = mockMvc.perform(get(CONSULT));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTOList));

    }

    @Test
    void create() throws Exception{
        ConsultationDTO consultationDTO = (ConsultationDTO) TestCreationFactory.listOf(ConsultationDTO.class).get(0);
        when(consultationService.create(consultationDTO)).thenReturn(consultationDTO);
        ResultActions result = performPostWithRequestBody(CONSULT,consultationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));
    }

    @Test
    void delete() throws Exception{
        ConsultationDTO consultationDTO = (ConsultationDTO) TestCreationFactory.listOf(ConsultationDTO.class).get(0);
        ResultActions result = performDeleteWithPathVariable(CONSULT+ENTITY,consultationDTO.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception{
        ConsultationDTO consultationDTO = (ConsultationDTO) TestCreationFactory.listOf(ConsultationDTO.class).get(0);
        when(consultationService.update(consultationDTO.getId(),consultationDTO)).thenReturn(consultationDTO);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(CONSULT+ENTITY,consultationDTO.getId().toString(),consultationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));
    }
}
