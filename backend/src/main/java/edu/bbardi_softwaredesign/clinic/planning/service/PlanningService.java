package edu.bbardi_softwaredesign.clinic.planning.service;

import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.planning.mapper.TimeSlotMapper;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.model.dto.TimeSlotDTO;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.planning.TimeSlotSpecification;
import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.repository.RoleRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMATTER;

@Service
@RequiredArgsConstructor
public class PlanningService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotMapper timeSlotMapper;

    public List<String> getDoctors() {
        Role role = roleRepository.findByName(ERole.DOCTOR).orElseThrow(()->new EntityNotFoundException("Role not found"));
        return userRepository.findUserByRolesContaining(role).stream()
                .map(User::getFullName)
                .collect(Collectors.toList());
    }

    public List<TimeSlotDTO> getSlots(String name, String date) {
        User doctor = userRepository.findUserByFullName(name)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        LocalDate startDate = LocalDate.parse(date,DATE_FORMATTER);
        List<TimeSlot> timeSlots = timeSlotRepository.findAll(TimeSlotSpecification.freeTimeSlots(doctor,startDate));
        return timeSlots.stream()
                .map(timeSlotMapper::toDTO)
                .collect(Collectors.toList());
    }
}
