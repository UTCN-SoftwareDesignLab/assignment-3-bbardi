package edu.bbardi_softwaredesign.clinic.consult.repository;

import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findConsultationByDoctorAndTimeBetween(User doctor,LocalDateTime start, LocalDateTime end);
    List<Consultation> findConsultationByPatient(Patient patient);
}
