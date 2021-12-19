package GraduationWorkSalesProject.graduation.com.service.mail;

public interface MailService {
    String SENDER_GRADU = "GraduProject11@gmail.com";

    void sendMail(String receiver, String subject, String body);
}

