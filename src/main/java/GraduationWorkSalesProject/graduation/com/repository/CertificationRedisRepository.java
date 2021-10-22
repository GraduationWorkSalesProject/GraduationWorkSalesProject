package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.redis.Certification;
import org.springframework.data.repository.CrudRepository;

public interface CertificationRedisRepository extends CrudRepository<Certification, String> {
}
