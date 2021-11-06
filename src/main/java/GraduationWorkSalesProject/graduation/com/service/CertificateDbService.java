package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificateDbService implements CertificateService{

    private final CertificateRepository certificateRepository;

    @Transactional
    public void save(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    public Optional<Certificate> findOne(String token) {
        return certificateRepository.findById(token);
    }

    @Transactional
    public void delete(String token) {
        certificateRepository.deleteById(token);
    }
}
