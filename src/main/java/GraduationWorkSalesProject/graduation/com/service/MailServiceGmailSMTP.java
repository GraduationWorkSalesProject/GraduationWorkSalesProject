package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.mail.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceGmailSMTP implements MailService {

    private final JavaMailSender mailSender;

    public void sendMail(MailDTO mailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getReceiver());
        message.setFrom(SENDER_GRADU);
        message.setSubject(mailDTO.getSubject());
        message.setText(mailDTO.getBody());

        mailSender.send(message);
    }
}
