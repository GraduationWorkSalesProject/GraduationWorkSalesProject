package GraduationWorkSalesProject.graduation.com.dto.member;

import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUseridCheckDTO {

    @ApiParam(value = "아이디", example = "example123", required = true)
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String userid;
}
