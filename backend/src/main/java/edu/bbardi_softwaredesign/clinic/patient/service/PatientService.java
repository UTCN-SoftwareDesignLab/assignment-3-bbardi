package edu.bbardi_softwaredesign.clinic.patient.service;

import edu.bbardi_softwaredesign.clinic.patient.mapper.PatientMapper;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> findAll(){
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO findByID(Long id){
        Patient patient = patientRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Patient not found"));
        return patientMapper.toDto(patient);
    }

    public PatientDTO create(PatientDTO patient){
        return patientMapper.toDto(patientRepository.save(patientMapper.fromDto(patient)));
    }

    public PatientDTO update(Long id, PatientDTO patient){
        Patient patientOrig = patientRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Patient not found"));
        Patient patientModif = patientMapper.fromDto(patient);
        patientOrig.setIdCard(patientModif.getIdCard());
        patientOrig.setName(patientModif.getName());
        patientOrig.setDateOfBirth(patientModif.getDateOfBirth());
        patientOrig.setAddress(patientModif.getAddress());
        patientOrig.setPersonalNumericCode(patientModif.getPersonalNumericCode());
        return patientMapper.toDto(patientRepository.save(patientOrig));
    }

    public void delete(Long id){
        patientRepository.deleteById(id);
    }
}
