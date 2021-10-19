package GraduationWorkSalesProject.graduation.com.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Getter
@RedisHash(value = "certificate", timeToLive = 30 * 60)
public class Certificate {

    @Id
    private String token;
    private String certificate;
    private String expirationDateTime;

    public Certificate(String certificate, String expirationDateTime, String token) {
        this.certificate = certificate;
        this.expirationDateTime = expirationDateTime;
        this.token = token;
    }

    public static Certificate create(String token) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<15;i++){
            if (random.nextBoolean()) {
                stringBuffer.append((char) (int) (random.nextInt(26) + 97));
            } else {
                stringBuffer.append(random.nextInt(10));
            }
        }
        return new Certificate(stringBuffer.toString(),
                LocalDateTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss")),
                token);
    }
}
