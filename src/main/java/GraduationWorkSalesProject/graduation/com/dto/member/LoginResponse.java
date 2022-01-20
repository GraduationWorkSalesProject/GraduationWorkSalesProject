package GraduationWorkSalesProject.graduation.com.dto.member;

import GraduationWorkSalesProject.graduation.com.entity.member.MemberCertificationStatus;
import GraduationWorkSalesProject.graduation.com.entity.member.MemberRole;
import GraduationWorkSalesProject.graduation.com.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String userid;
    private String username;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private LocalDateTime joinedDate;
    private MemberRole role;
    private MemberCertificationStatus certificationStatus;
    private Address address;
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
