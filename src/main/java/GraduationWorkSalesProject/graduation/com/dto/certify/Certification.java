package GraduationWorkSalesProject.graduation.com.dto.certify;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {

    private String token;
    private String certificationCode;
    private LocalDateTime expirationDateTime;

    private Certification(String certificationCode, LocalDateTime expirationDateTime, String token) {
        this.certificationCode = certificationCode;
        this.expirationDateTime = expirationDateTime;
        this.token = token;
    }

    public static Certification create(String token){
        return new Certification(
                Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000)),
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(3).toLocalDateTime(),
                token);
    }
}
