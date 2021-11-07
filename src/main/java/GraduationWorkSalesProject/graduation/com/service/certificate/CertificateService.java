package GraduationWorkSalesProject.graduation.com.service.certificate;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;

public interface CertificateService {
    void save(Certificate certificate);
    void delete(String token);
    void validateCertificate(String token);
}
