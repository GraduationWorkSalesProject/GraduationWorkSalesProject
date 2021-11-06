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
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RedisHash(value = "certificate", timeToLive = 30 * 60)
@Table(name = "certificates")
public class Certificate {

//    @Id
    @Id
    @Column(name = "certificate_token")
    private String token;
    @Column(name = "certificate_expiration_date")
    private LocalDateTime expirationDateTime;

    public Certificate(String token, LocalDateTime expirationDateTime) {
        this.token = token;
        this.expirationDateTime = expirationDateTime;
    }

    public static Certificate create() {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<16;i++){
            if (random.nextBoolean()) {
                stringBuffer.append((char)(random.nextInt(26) + 97));
            } else {
                stringBuffer.append(random.nextInt(10));
            }
        }
        return new Certificate(stringBuffer.toString(),
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(30).toLocalDateTime());
    }
}
