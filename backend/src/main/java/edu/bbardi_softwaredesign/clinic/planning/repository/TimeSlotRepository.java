package edu.bbardi_softwaredesign.clinic.planning.repository;

import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long>, JpaSpecificationExecutor<TimeSlot> {
}
