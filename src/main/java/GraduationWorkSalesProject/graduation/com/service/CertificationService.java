package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;

import java.util.Optional;

public interface CertificationService {
    void save(Certification certification);
    Optional<Certification> findOne(String token);
    void delete(String token);
}
