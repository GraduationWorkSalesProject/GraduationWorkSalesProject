package GraduationWorkSalesProject.graduation.com.dto.mail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailCertificationResponse {
    
    private String expirationDateTime;
    private String certificationCode;
}
