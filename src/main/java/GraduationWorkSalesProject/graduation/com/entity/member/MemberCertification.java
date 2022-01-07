package GraduationWorkSalesProject.graduation.com.entity.member;

import java.sql.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_certifications")
@Entity
@AllArgsConstructor
@Builder
public class MemberCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_certification_id", updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name = "certification_date")
	private Date certificationDate;

    @Column(name = "school")
	private String school;

    @Column(name = "department")
    private String department;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageName", column = @Column(name = "student_id_image_name")),
            @AttributeOverride(name = "imageUuid", column = @Column(name = "student_id_image_uuid")),
            @AttributeOverride(name = "imageType", column = @Column(name = "student_id_image_type")),
            @AttributeOverride(name = "imageHref", column = @Column(name = "student_id_image_href"))
    })
    private Image image;





}
