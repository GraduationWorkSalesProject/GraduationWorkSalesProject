package GraduationWorkSalesProject.graduation.com.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemeberProfileResponse {

	private String userName;

	private String email;

    private String phoneNumber;

    private String address;

    private String detailAddress;

    private String postcode;

}
