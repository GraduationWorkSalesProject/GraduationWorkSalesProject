package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.redis.Certification;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificationRedisService {

    private final CertificationRedisRepository certificationRedisRepository;

    public void save(Certification certification) {
        certificationRedisRepository.save(certification);
    }

    @Cacheable(value = "certification", key = "#token", cacheManager = "cacheManager")
    public Optional<Certification> findOne(String token) {
        return certificationRedisRepository.findById(token);
    }

    @CacheEvict(value = "certification", key = "#token")
    public void delete(String token){
        certificationRedisRepository.deleteById(token);
    }
}
