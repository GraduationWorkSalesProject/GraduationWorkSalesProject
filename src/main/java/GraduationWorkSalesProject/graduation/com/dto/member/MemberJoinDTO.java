package GraduationWorkSalesProject.graduation.com.dto.member;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinDTO {

    @Email(message = "이메일 형식이 잘못되었습니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 숫자, 문자, 특수문자를 포함한 8자리 이상, 15자리 이하로 입력해주세요.")
    private String password;

    @Length(min = 2, max = 10, message = "닉네임은 2자리 이상, 10자리 이하로 입력해주세요.")
    private String username;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호는 010-1234-5678 형태로 입력해주세요.")
    private String phoneNumber;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String address;

    @NotEmpty(message = "상세주소를 입력해주세요.")
    private String detailAddress;

    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String postcode;

    public Member convert() {
        return Member.builder()
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
