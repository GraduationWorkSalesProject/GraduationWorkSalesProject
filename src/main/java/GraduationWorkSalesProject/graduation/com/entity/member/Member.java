package GraduationWorkSalesProject.graduation.com.entity.member;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import GraduationWorkSalesProject.graduation.com.dto.member.MemeberProfileResponse;
import GraduationWorkSalesProject.graduation.com.vo.Address;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(name = "member_email", unique = true)
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_userid", unique = true)
    private String userid;

    @Column(name = "member_username", unique = true)
    private String username;

    @Column(name = "member_phone_number")
    private String phoneNumber;

    @CreatedDate
    @Column(name = "member_join_date", updatable = false)
    private LocalDateTime joinedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_certification_status")
    private MemberCertificationStatus certificationStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "member_address")),
            @AttributeOverride(name = "detailAddress", column = @Column(name = "member_detail_address")),
            @AttributeOverride(name = "postcode", column = @Column(name = "member_postcode"))
    })
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageName", column = @Column(name = "member_image_name")),
            @AttributeOverride(name = "imageUuid", column = @Column(name = "member_image_uuid")),
            @AttributeOverride(name = "imageType", column = @Column(name = "member_iamge_Type"))
    })
    private Image image;

    @Column(name = "member_refresh_token")
    private String refreshToken = null;

    @Builder
    public Member(String userid, String email, String password, String username, String phoneNumber,
                  String address, String detailAddress, String postcode) {
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.role = MemberRole.ROLE_USER;
        this.certificationStatus = MemberCertificationStatus.UNCERTIFIED;
        this.address = new Address(address, detailAddress, postcode);
        // TODO: 2021. 10. 5.
        //  이미지 저장소 url 필요
        //  이미지 저장소에 기본 이미지 저장 필요
        //  이미지 타입 논의 필요
        this.image = new Image("init image", "init uuid", "jpg");
    }

    public void encryptPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateRole(MemberRole role) {
        this.role = role;
    }

    public void updateProfile(String phoneNumber, String address, String detailAddress, String postcode) {
    	this.phoneNumber = phoneNumber;
    	this.address = new Address(address, detailAddress, postcode);
    }

    public MemeberProfileResponse toMemeberProfileResponse() {
    	return  MemeberProfileResponse.builder()
				.userName(this.username)
				.email(this.email)
				.address(this.address.getAddress())
				.detailAddress(this.address.getDetailAddress())
				.postcode(this.address.getPostcode())
				.phoneNumber(this.phoneNumber)
			 	.build();
    }
}
