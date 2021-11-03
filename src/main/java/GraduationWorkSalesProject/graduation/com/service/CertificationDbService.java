package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class CertificationDbService implements CertificationService{

    private final CertificationRepository certificationRepository;

    @Override
    public void save(Certification certification) {
        certificationRepository.save(certification);
    }

    @Override
    public Optional<Certification> findOne(String token) {
        return certificationRepository.findById(token);
    }

    @Override
    public void delete(String token) {
        certificationRepository.deleteById(token);
    }
}
