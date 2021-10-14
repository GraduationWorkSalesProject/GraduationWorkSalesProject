package GraduationWorkSalesProject.graduation.com.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailTemplate {

    MAIL_CERTIFICATION("[그라듀] 이메일 인증 코드 안내"),

    ;

    private String subject;
    private String body;

    MailTemplate(String subject){
        this.subject = subject;
    }
}