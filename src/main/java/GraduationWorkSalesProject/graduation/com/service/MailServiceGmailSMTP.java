package GraduationWorkSalesProject.graduation.com.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceGmailSMTP implements MailService {

    private final JavaMailSender mailSender;

    public void sendMail(String receiver, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setFrom(SENDER_GRADU);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
