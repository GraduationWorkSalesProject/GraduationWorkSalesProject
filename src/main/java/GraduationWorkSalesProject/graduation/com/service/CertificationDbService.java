package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificationDbService implements CertificationService{

    private final CertificationRepository certificationRepository;

    @Transactional
    public void save(Certification certification) {
        certificationRepository.save(certification);
    }

    public Optional<Certification> findOne(String token) {
        return certificationRepository.findById(token);
    }

    @Transactional
    public void delete(String token) {
        certificationRepository.deleteById(token);
    }
}
