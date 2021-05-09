package edu.bbardi_softwaredesign.clinic.user;

import edu.bbardi_softwaredesign.clinic.BaseControllerTest;
import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import edu.bbardi_softwaredesign.clinic.user.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.*;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.ENTITY;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.USER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController usersController;

    @Mock
    private UserManagementService userManagementService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        usersController = new UserController(userManagementService);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void findAllUsers() throws Exception{
        List<UserDTO> userDTOList = TestCreationFactory.listOf(UserDTO.class);
        when(userManagementService.findAll()).thenReturn(userDTOList);
        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTOList));

    }

    @Test
    void createUser() throws Exception{
        UserDTO user = (UserDTO) TestCreationFactory.listOf(UserDTO.class).get(0);
        when(userManagementService.createUser(user)).thenReturn(user);
        ResultActions result = performPostWithRequestBody(USER,user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void deleteUser() throws Exception{
        UserDTO user = (UserDTO) TestCreationFactory.listOf(UserDTO.class).get(0);
        ResultActions result = performDeleteWithPathVariable(USER+ENTITY,user.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void editUser() throws Exception{
        UserDTO user = (UserDTO) TestCreationFactory.listOf(UserDTO.class).get(0);
        when(userManagementService.editUser(user.getId(),user)).thenReturn(user);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(USER+ENTITY,user.getId().toString(),user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }
}
