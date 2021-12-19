package GraduationWorkSalesProject.graduation.com.service.certificate;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.repository.CertificateRedisRepository;
import GraduationWorkSalesProject.graduation.com.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateRedisService {

    private final CertificateRedisRepository certificateRepository;

    public void save(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    @Cacheable(value = "certificate", key = "#token", cacheManager = "cacheManager")
    public Optional<Certificate> findOne(String token) {
        return certificateRepository.findById(token);
    }

    @CacheEvict(value = "certificate", key = "#token")
    public void delete(String token){
        certificateRepository.deleteById(token);
    }
}
