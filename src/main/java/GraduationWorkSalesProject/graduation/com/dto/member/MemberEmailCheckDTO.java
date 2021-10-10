package GraduationWorkSalesProject.graduation.com.dto.member;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEmailCheckDTO {

    @Email(message = "이메일 형식이 잘못되었습니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    String email;
}
