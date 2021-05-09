package edu.bbardi_softwaredesign.clinic.planning;

import edu.bbardi_softwaredesign.clinic.TestCreationFactory;
import edu.bbardi_softwaredesign.clinic.consult.repository.ConsultationRepository;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.planning.mapper.TimeSlotMapper;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.planning.service.PlanningService;
import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.repository.RoleRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMATTER;
import static edu.bbardi_softwaredesign.clinic.TestCreationFactory.*;

@SpringBootTest
public class PlanningServiceIntegrationTest {
    @Autowired
    private PlanningService planningService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;


    @BeforeEach
    @AfterEach
    void setUp(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }

    @Test
    void findDoctors(){
        Role doc = roleRepository.save(Role.builder().name(ERole.DOCTOR).build());
        List<User> userList =TestCreationFactory.listOf(User.class);
        userList.forEach(u -> {
            u.setRoles(Set.of(doc));
            u.setId(null);
        });
        userRepository.saveAll(userList);
        Assertions.assertEquals(userList.size(),planningService.getDoctors().size());
    }
    @Test
    void findFreeSlots(){
        List<TimeSlot> timeSlots = TestCreationFactory.listOf(TimeSlot.class);
        Role doc = roleRepository.save(Role.builder().name(ERole.DOCTOR).build());
        List<User> userList =TestCreationFactory.listOf(User.class);
        userList.forEach(u -> {
            u.setRoles(Set.of(doc));
            u.setId(null);
        });
        userRepository.saveAll(userList);
        timeSlotRepository.saveAll(timeSlots);
        Assertions.assertEquals(timeSlots.size(),planningService.getSlots(userList.get(randomBoundedInt(userList.size())).getFullName(),randomDate().format(DATE_FORMATTER)).size());
    }
}
