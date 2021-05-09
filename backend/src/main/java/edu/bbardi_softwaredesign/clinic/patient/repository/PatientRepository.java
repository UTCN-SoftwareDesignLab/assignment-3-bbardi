package edu.bbardi_softwaredesign.clinic.patient.repository;

import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findPatientByName(String name);
}
