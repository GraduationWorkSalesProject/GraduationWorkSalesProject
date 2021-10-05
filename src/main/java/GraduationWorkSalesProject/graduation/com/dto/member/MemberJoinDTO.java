package GraduationWorkSalesProject.graduation.com.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class MemberJoinDTO {

    @Email
    private String email;

    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")
    private String password;

    @Length(min = 2, max = 10)
    private String nickname;

    @Pattern(regexp = "\t^\\\\d{3}-\\\\d{3,4}-\\\\d{4}$")
    private String phoneNumber;

    @NotEmpty
    private String address;

    @NotEmpty
    private String detailAddress;

    @NotEmpty
    private String postcode;
}
