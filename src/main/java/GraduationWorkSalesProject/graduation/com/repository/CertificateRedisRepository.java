package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import org.springframework.data.repository.CrudRepository;

public interface CertificateRedisRepository extends CrudRepository<Certificate, String> {
}
