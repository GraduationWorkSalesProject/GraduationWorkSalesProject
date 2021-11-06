package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface CertificateRepository extends JpaRepository<Certificate, String> {
    void deleteByExpirationDateTimeLessThan(LocalDateTime now);
}

