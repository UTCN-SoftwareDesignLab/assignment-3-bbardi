package edu.bbardi_softwaredesign.clinic.patient;

import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.UrlMapping.ENTITY;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.PATIENT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(PATIENT)
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> findAllPatients(){
        return patientService.findAll();
    }

    @GetMapping(ENTITY)
    public PatientDTO getPatientByID(@PathVariable Long id){
        return patientService.findByID(id);
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientDTO patient){
        return patientService.create(patient);
    }

    @PatchMapping(ENTITY)
    public PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO patient){
        return patientService.update(id,patient);
    }

    @DeleteMapping(ENTITY)
    public void deletePatient(@PathVariable Long id){
        patientService.delete(id);
    }
}
