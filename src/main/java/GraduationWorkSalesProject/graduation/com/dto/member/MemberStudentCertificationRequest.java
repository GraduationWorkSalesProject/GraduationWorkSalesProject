package GraduationWorkSalesProject.graduation.com.dto.member;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel(value = "학생 인증 모델")
@Getter
public class MemberStudentCertificationRequest {
	private Date certificationDate;
	private String school;
	private String department;
	private String student_id_image_name;
	private String student_id_image_uuid;
	private String student_id_image_type;

}
