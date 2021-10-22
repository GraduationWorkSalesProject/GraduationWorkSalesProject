package GraduationWorkSalesProject.graduation.com.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@RedisHash(value = "certification", timeToLive = 3 * 60)
public class Certification {

    @Id
    private String token;
    private String certificationCode;
    private String expirationDateTime;

    private Certification(String certificationCode, String expirationDateTime, String token) {
        this.certificationCode = certificationCode;
        this.expirationDateTime = expirationDateTime;
        this.token = token;
    }

    public static Certification create(String token){
        return new Certification(
                Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000)),
                LocalDateTime.now().plusMinutes(3).format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss")),
                token);
    }
}
