package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUsernameCheckDTO {

    @ApiParam(value = "닉네임", example = "만두", required = true)
    @Length(min = 2, max = 10, message = "닉네임은 2자리 이상, 10자리 이하로 입력해주세요.")
    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String username;
}
