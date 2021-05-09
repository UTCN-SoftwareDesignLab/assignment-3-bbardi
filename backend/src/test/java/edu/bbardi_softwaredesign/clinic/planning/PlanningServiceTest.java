package edu.bbardi_softwaredesign.clinic.planning;

import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.planning.mapper.TimeSlotMapper;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.model.dto.TimeSlotDTO;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.planning.service.PlanningService;
import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.repository.RoleRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import edu.bbardi_softwaredesign.clinic.user.service.UserManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMATTER;
import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PlanningServiceTest {
    @InjectMocks
    private PlanningService planningService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private TimeSlotMapper timeSlotMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        planningService = new PlanningService(userRepository,roleRepository,timeSlotRepository,timeSlotMapper);
    }

    @Test
    void findDoctors(){
        Role role = new Role(randomBoundedInt(10), ERole.DOCTOR);
        List<User> userList = TestCreationFactory.listOf(User.class);
        when(roleRepository.findByName(ERole.DOCTOR)).thenReturn(Optional.of(role));
        when(userRepository.findUserByRolesContaining(any())).thenReturn(userList);
        Assertions.assertEquals(userList.size(),planningService.getDoctors().size());
    }

    @Test
    void findTimeslots(){
        User user = (User) TestCreationFactory.listOf(User.class).get(0);
        when(userRepository.findUserByFullName(user.getFullName())).thenReturn(Optional.of(user));
        List<TimeSlot> timeSlots = TestCreationFactory.listOf(TimeSlot.class);
        when(timeSlotRepository.findAll(TimeSlotSpecification.freeTimeSlots(user,any()))).thenReturn(timeSlots);
        when(timeSlotMapper.toDTO(any())).thenReturn(new TimeSlotDTO(randomLong(),randomTimeStamp()));
        Assertions.assertEquals(timeSlots.size(),planningService.getSlots(user.getFullName(),"2000-11-11").size());
    }
}
