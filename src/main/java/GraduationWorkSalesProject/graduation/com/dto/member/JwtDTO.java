package GraduationWorkSalesProject.graduation.com.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    private String accessToken;
    private String refreshToken;
}
