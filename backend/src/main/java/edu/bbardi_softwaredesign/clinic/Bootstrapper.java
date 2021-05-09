package edu.bbardi_softwaredesign.clinic;


import edu.bbardi_softwaredesign.clinic.consult.repository.ConsultationRepository;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.repository.RoleRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            consultationRepository.deleteAll();
            timeSlotRepository.deleteAll();
            patientRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            List<TimeSlot> timeSlots = IntStream.range(8,18)
                                                .mapToObj(i->String.format("%02d:00",i))
                                                .map(s-> TimeSlot.builder().timestamp(s).build())
                                                .collect(Collectors.toList());
            timeSlotRepository.saveAll(timeSlots);
        }
    }
}