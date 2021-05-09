package edu.bbardi_softwaredesign.clinic;


import edu.bbardi_softwaredesign.clinic.consult.model.Consultation;
import edu.bbardi_softwaredesign.clinic.consult.model.dto.ConsultationDTO;
import edu.bbardi_softwaredesign.clinic.patient.model.Patient;
import edu.bbardi_softwaredesign.clinic.patient.model.dto.PatientDTO;
import edu.bbardi_softwaredesign.clinic.planning.model.TimeSlot;
import edu.bbardi_softwaredesign.clinic.planning.model.dto.TimeSlotDTO;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.bbardi_softwaredesign.clinic.Constants.DATE_FORMATTER;
import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserDTO;
        }else if (cls.equals(Patient.class)) {
            supplier = TestCreationFactory::newPatient;
        }else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newPatientDTO;
        }else if (cls.equals(Consultation.class)) {
            supplier = TestCreationFactory::newConsultation;
        }else if (cls.equals(ConsultationDTO.class)) {
            supplier = TestCreationFactory::newConsultationDTO;
        }else if (cls.equals(TimeSlot.class)) {
            supplier = TestCreationFactory::newTimeSlot;
        }else if (cls.equals(TimeSlotDTO.class)) {
            supplier = TestCreationFactory::newTimeSlotDTO;
        }else if (cls.equals(String.class)){
            supplier = TestCreationFactory::randomString;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static TimeSlot newTimeSlot(){
        return new TimeSlot(randomLong(),randomTimeStamp());
    }

    private static TimeSlotDTO newTimeSlotDTO(){
        return new TimeSlotDTO(randomLong(),randomTimeStamp());
    }

    private static Consultation newConsultation(){
        return Consultation.builder()
                .id(randomLong())
                .date(randomDate())
                .patient(newPatient())
                .doctor(newUser())
                .time(newTimeSlot())
                .build();
    }
    private static ConsultationDTO newConsultationDTO(){
        return ConsultationDTO.builder()
                .id(randomLong())
                .date(DATE_FORMATTER.format(randomDate()))
                .patientName(randomString())
                .doctorName(randomString())
                .time(randomTimeStamp())
                .build();
    }

    private static Patient newPatient(){
        return Patient.builder()
                .id(randomLong())
                .name(randomString())
                .address(randomString())
                .personalNumericCode(randomString(13))
                .idCard(randomString(6))
                .dateOfBirth(randomDate())
                .build();
    }

    private static PatientDTO newPatientDTO(){
        return PatientDTO.builder()
                .id(randomLong())
                .name(randomString())
                .address(randomString())
                .personalNumericCode(randomString(13))
                .idCard(randomString(6))
                .dateOfBirth(DATE_FORMATTER.format(randomDate()))
                .build();
    }


    private static User newUser() {
        return User.builder()
                .id(randomLong())
                .fullName(randomString())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
    }

    private static UserDTO newUserDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .fullName(randomString())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
    }

    public static LocalDate randomDate(){
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        Random random = new Random();
        List<Long> longList = random.longs(minDay,maxDay).limit(10).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
        return LocalDate.ofEpochDay(longList.get(0));
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static Float randomBoundedFloat(float upperBound) {
        return new Random().nextFloat() * upperBound;
    }

    public static String randomTimeStamp(){
        Random random = new Random();
        int Hour = random.nextInt(23);
        int Minute = random.nextInt(59);
        return Hour + ":" + Minute;
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    public static String randomString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}