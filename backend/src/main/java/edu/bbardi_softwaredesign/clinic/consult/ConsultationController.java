package edu.bbardi_softwaredesign.clinic.consult;

import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import edu.bbardi_softwaredesign.clinic.consult.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.UrlMapping.CONSULT;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.ENTITY;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(CONSULT)
public class ConsultationController {
    private final ConsultationService consultationService;

    @GetMapping
    public List<ConsultationDTO> findAll(){
        return consultationService.findAll();
    }

    @GetMapping(ENTITY)
    public ConsultationDTO getConsultationByID(@PathVariable Long id){
        return consultationService.findByID(id);
    }

    @PostMapping
    public ConsultationDTO createConsultation(@RequestBody ConsultationDTO consultation){
        return consultationService.create(consultation);
    }

    @PostMapping(ENTITY)
    public void checkIn(@PathVariable Long id){
        consultationService.checkIn(id);
    }

    @PatchMapping(ENTITY)
    public ConsultationDTO updateConsultation(@PathVariable Long id, @RequestBody ConsultationDTO consultation){
        return consultationService.update(id,consultation);
    }

    @DeleteMapping(ENTITY)
    public void deleteConsultation(@PathVariable Long id){
        consultationService.delete(id);
    }
}
