package GraduationWorkSalesProject.graduation.com.service.certification;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberCertificationCodeRequest;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;

public interface CertificationService {
    void save(Certification certification);
    void delete(String token);
    void validateCertification(MemberCertificationCodeRequest request);
}
