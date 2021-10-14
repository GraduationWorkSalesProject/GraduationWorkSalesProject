package GraduationWorkSalesProject.graduation.com.dto.member;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginDTO {

    @Email(message = "이메일 형식이 잘못되었습니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 숫자, 문자, 특수문자를 포함한 8자리 이상, 15자리 이하로 입력해주세요.")
    private String password;
}
