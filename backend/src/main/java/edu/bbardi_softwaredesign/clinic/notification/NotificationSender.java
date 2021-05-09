package edu.bbardi_softwaredesign.clinic.notification;

import edu.bbardi_softwaredesign.clinic.notification.dto.CheckInInformation;
import edu.bbardi_softwaredesign.clinic.notification.dto.NotificationMessage;
import edu.bbardi_softwaredesign.clinic.notification.dto.ScheduleInformation;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static edu.bbardi_softwaredesign.clinic.UrlMapping.MESSAGE;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.USER_PREFIX;

@RequiredArgsConstructor
@Component
public class NotificationSender {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private String scheduleMessageBuilder(ScheduleInformation information){
        return "New patient scheduled for " +
                information.getDate() +
                " at " +
                information.getHour();
    }

    private String checkinMessageBuilder(CheckInInformation information){
        return "Patient: " +
                information.getPatientName() +
                " is here to see you";
    }


    public void sendScheduleNotification(ScheduleInformation information){
        simpMessagingTemplate.convertAndSend(USER_PREFIX + "/" +
                information.getDoctorUsername() +
                MESSAGE,
                new NotificationMessage(scheduleMessageBuilder(information)));
    }

    public void sendCheckInMessage(CheckInInformation information) {
        simpMessagingTemplate.convertAndSend(USER_PREFIX + "/" +
                        information.getDoctorUsername() +
                        MESSAGE,
                new NotificationMessage(checkinMessageBuilder(information)));
    }
}
