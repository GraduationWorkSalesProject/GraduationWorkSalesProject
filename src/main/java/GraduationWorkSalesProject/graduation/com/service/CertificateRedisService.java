package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.redis.Certificate;
import GraduationWorkSalesProject.graduation.com.repository.CertificateRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateRedisService {

    private final CertificateRedisRepository certificateRedisRepository;

    public void save(Certificate certificate) {
        certificateRedisRepository.save(certificate);
    }

    @Cacheable(value = "certificate", key = "#token", cacheManager = "cacheManager")
    public Optional<Certificate> findOne(String token) {
        return certificateRedisRepository.findById(token);
    }

    @CacheEvict(value = "certificate", key = "#token")
    public void delete(String token){
        certificateRedisRepository.deleteById(token);
    }
}
