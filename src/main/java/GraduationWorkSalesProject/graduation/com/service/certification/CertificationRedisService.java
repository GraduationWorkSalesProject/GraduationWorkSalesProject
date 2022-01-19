package GraduationWorkSalesProject.graduation.com.service.certification;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberCertificationCodeRequest;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRedisRepository;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class CertificationRedisService implements CertificationService{

    private final CertificationRedisRepository certificationRepository;

    public void save(Certification certification) {
        certificationRepository.save(certification);
    }

    @Cacheable(value = "certification", key = "#token", cacheManager = "cacheManager")
    public Optional<Certification> findOne(String token) {
        return certificationRepository.findById(token);
    }

    @CacheEvict(value = "certification", key = "#token")
    public void delete(String token){
        certificationRepository.deleteById(token);
    }

    @Override
    public void validateCertification(MemberCertificationCodeRequest request) {

    }
}
