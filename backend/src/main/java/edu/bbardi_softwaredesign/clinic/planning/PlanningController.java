package edu.bbardi_softwaredesign.clinic.planning;

import edu.bbardi_softwaredesign.clinic.planning.model.dto.TimeSlotDTO;
import edu.bbardi_softwaredesign.clinic.planning.service.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(PLANNING)
public class PlanningController {
    private final PlanningService planningService;

    @GetMapping(DOCTORS)
    public List<String> getDoctors(){
        return planningService.getDoctors();
    }

    @GetMapping(SLOTS)
    public List<TimeSlotDTO> getAvailableSlots(@RequestParam String doctorName, @RequestParam String date){
        return planningService.getSlots(doctorName,date);
    }
}
