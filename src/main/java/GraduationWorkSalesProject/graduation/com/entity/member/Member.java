package GraduationWorkSalesProject.graduation.com.entity.member;

import GraduationWorkSalesProject.graduation.com.vo.Address;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "member_username")
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

    @Builder
    public Member(String email, String password, String username, String phoneNumber,
                  String address, String detailAddress, String postcode) {
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
}
