package edu.bbardi_softwaredesign.clinic.consult.service;

import edu.bbardi_softwaredesign.clinic.consult.mapper.ConsultationMapper;
import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import edu.bbardi_softwaredesign.clinic.consult.repository.ConsultationRepository;
import edu.bbardi_softwaredesign.clinic.notification.NotificationSender;
import edu.bbardi_softwaredesign.clinic.notification.dto.CheckInInformation;
import edu.bbardi_softwaredesign.clinic.notification.dto.ScheduleInformation;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.repository.PatientRepository;
import edu.bbardi_softwaredesign.clinic.planning.TimeSlotSpecification;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.repository.TimeSlotRepository;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final ConsultationMapper consultationMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final NotificationSender notificationSender;

    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }


    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }

    public ConsultationDTO findByID(Long id) {
        return consultationMapper.toDTO(consultationRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Consultation not found")));
    }

    public ConsultationDTO create(ConsultationDTO consultation) {
        Consultation actConsult = consultationMapper.fromDTO(consultation);
        User doctor = userRepository.findUserByFullName(consultation.getDoctorName())
                .orElseThrow(()->new EntityNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findPatientByName(consultation.getPatientName())
                .orElseThrow(()->new EntityNotFoundException("Patient not found"));
        TimeSlot timeSlot = timeSlotRepository.findOne(TimeSlotSpecification.timeSlotLike(consultation.getTime()))
                .orElseThrow(()->new EntityNotFoundException("Timeslot not found"));
        actConsult.setDoctor(doctor);
        actConsult.setPatient(patient);
        actConsult.setTime(timeSlot);
        notificationSender.sendScheduleNotification(ScheduleInformation.builder()
                .date(consultation.getDate())
                .hour(consultation.getTime())
                .doctorUsername(doctor.getUsername())
                .build());
        return consultationMapper.toDTO(consultationRepository.save(actConsult));
    }

    public ConsultationDTO update(Long id, ConsultationDTO consultation) {
        Consultation actConsult = consultationRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Consultation not found"));
        Consultation mappedConsult = consultationMapper.fromDTO(consultation);
        User doctor = userRepository.findUserByFullName(consultation.getDoctorName())
                .orElseThrow(()->new EntityNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findPatientByName(consultation.getPatientName())
                .orElseThrow(()->new EntityNotFoundException("Patient not found"));
        TimeSlot timeSlot = timeSlotRepository.findOne(TimeSlotSpecification.timeSlotLike(consultation.getTime()))
                .orElseThrow(()->new EntityNotFoundException("Timeslot not found"));
        actConsult.setDescription(consultation.getDescription());
        actConsult.setDoctor(doctor);
        actConsult.setPatient(patient);
        actConsult.setTime(timeSlot);
        return consultationMapper.toDTO(consultationRepository.save(actConsult));
    }

    public void checkIn(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Consultation not found"));
        notificationSender.sendCheckInMessage(
                CheckInInformation.builder()
                        .doctorUsername(consultation.getDoctor().getUsername())
                        .patientName(consultation.getPatient().getName())
                        .build()
        );
    }
}
