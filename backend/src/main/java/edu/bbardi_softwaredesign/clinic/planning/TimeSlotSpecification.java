package edu.bbardi_softwaredesign.clinic.planning;

import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class TimeSlotSpecification {
    public static Specification<TimeSlot> freeTimeSlots(User doctor, LocalDate date){
        return (root, query, criteriaBuilder) -> {
            Subquery<Consultation> consultationSubQuery = query.subquery(Consultation.class);
            Root<Consultation> consultationRoot = consultationSubQuery.from(Consultation.class);
            Subquery<Consultation> consultationsOfDay = consultationSubQuery.select(consultationRoot.get("time")).where(
                    criteriaBuilder.and(
                        criteriaBuilder.equal(consultationRoot.get("date"),date),
                        criteriaBuilder.equal(consultationRoot.get("doctor").get("id"),doctor.getId())
                    )
            );
            return criteriaBuilder.not(root.get("id").in(consultationsOfDay));
        };
    }
    public static Specification<TimeSlot> timeSlotLike(String slot){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("timestamp"),slot);
    }
}
