package GraduationWorkSalesProject.graduation.com.dto.certify;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certificate {

    private String token;
    private LocalDateTime expirationDateTime;

    public Certificate(String token, LocalDateTime expirationDateTime) {
        this.token = token;
        this.expirationDateTime = expirationDateTime;
    }

    public static Certificate create() {
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (random.nextBoolean()) {
                stringBuffer.append((char) (random.nextInt(26) + 97));
            } else {
                stringBuffer.append(random.nextInt(10));
            }
        }
        return new Certificate(stringBuffer.toString(),
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(30).toLocalDateTime());
    }
}
