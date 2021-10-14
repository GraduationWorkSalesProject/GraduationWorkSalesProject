package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.mail.MailDTO;

public interface MailService {
    String SENDER_GRADU = "GraduProject11@gmail.com";

    void sendMail(MailDTO mailDTO);
}

