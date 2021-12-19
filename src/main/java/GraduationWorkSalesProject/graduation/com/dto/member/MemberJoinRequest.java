package GraduationWorkSalesProject.graduation.com.dto.member;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel(description = "회원 가입 데이터 모델")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinRequest {

    @ApiModelProperty(value = "아이디", example = "example123", required = true)
    @Pattern(regexp = "^[a-z0-9+]{5,20}$", message = "아이디는 5~20자의 영소문자, 숫자만 사용 가능합니다.")
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String userid;

    @ApiModelProperty(value = "이메일", example = "example@gmail.com", required = true)
    @Email(message = "이메일 형식이 잘못되었습니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @ApiModelProperty(value = "비밀번호(숫자, 문자, 특수문자를 포함한 8자리 이상, 15자리 이하)", example = "a1B2C!d#4ss", required = true)
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 숫자, 문자, 특수문자를 포함한 8자리 이상, 15자리 이하로 입력해주세요.")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @ApiModelProperty(value = "닉네임", example = "만두", required = true)
    @Length(min = 2, max = 10, message = "닉네임은 2자리 이상, 10자리 이하로 입력해주세요.")
    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String username;

    @ApiModelProperty(value = "휴대폰번호", example = "010-1234-5678", required = true)
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호는 010-1234-5678 형태로 입력해주세요.")
    @NotEmpty(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    @ApiModelProperty(value = "주소", example = "경기 성남시 분당구 판교역로 235", required = true)
    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;

    @ApiModelProperty(value = "상세주소", example = "에이치스퀘어 N동 7층", required = true)
    @NotEmpty(message = "상세주소를 입력해주세요.")
    private String detailAddress;

    @ApiModelProperty(value = "우편번호", example = "30872", required = true)
    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String postcode;

    @ApiModelProperty(value = "인증 토큰(hidden)", example = "wjRMbgPxtlKklzV2", required = true)
    @Length(min = 16, max = 16, message = "인증 토큰은 16자리입니다.")
    @NotEmpty(message = "인증 토큰은 필수입니다.")
    private String token;

    public Member convert() {
        return Member.builder()
                .userid(getUserid())
                .email(getEmail())
                .password(getPassword())
                .username(getUsername())
                .phoneNumber(getPhoneNumber())
                .address(getAddress())
                .detailAddress(getDetailAddress())
                .postcode(getPostcode())
                .build();
    }
}
