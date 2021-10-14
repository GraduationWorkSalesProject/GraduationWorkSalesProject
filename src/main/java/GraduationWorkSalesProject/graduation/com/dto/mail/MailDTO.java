package GraduationWorkSalesProject.graduation.com.dto.mail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailDTO {

    private String receiver;
    private String subject;
    private String body;
}
