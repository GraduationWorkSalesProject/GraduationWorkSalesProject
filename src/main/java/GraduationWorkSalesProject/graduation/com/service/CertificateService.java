package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;

import java.util.Optional;

public interface CertificateService {
    void save(Certificate certificate);
    Optional<Certificate> findOne(String token);
    void delete(String token);
}
