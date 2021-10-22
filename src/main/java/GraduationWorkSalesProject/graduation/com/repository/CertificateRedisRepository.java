package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.redis.Certificate;
import org.springframework.data.repository.CrudRepository;

public interface CertificateRedisRepository extends CrudRepository<Certificate, String> {
}
