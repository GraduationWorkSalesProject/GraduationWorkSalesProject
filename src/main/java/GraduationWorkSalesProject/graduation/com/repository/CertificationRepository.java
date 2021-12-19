package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CertificationRepository extends JpaRepository<Certification, String> {
    void deleteByExpirationDateTimeLessThan(LocalDateTime now);
}
