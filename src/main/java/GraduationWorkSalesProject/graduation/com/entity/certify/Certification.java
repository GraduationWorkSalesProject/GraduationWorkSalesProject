package GraduationWorkSalesProject.graduation.com.entity.certify;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RedisHash(value = "certification", timeToLive = 3 * 60)
@Table(name = "certifications")
public class Certification {

//    @Id
    @Id
    @Column(name = "certificate_token")
    private String token;
    @Column(name = "certificate_code")
    private String certificationCode;
    @Column(name = "certificate_expiration_date")
    private String expirationDateTime;

    private Certification(String certificationCode, String expirationDateTime, String token) {
        this.certificationCode = certificationCode;
        this.expirationDateTime = expirationDateTime;
        this.token = token;
    }

    public static Certification create(String token){
        return new Certification(
                Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000)),
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(3).format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss")),
                token);
    }
}
