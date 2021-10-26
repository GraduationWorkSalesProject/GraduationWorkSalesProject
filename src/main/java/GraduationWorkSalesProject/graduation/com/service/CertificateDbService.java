package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class CertificateDbService implements CertificateService{

    private final CertificateRepository certificateRepository;

    @Override
    public void save(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    @Override
    public Optional<Certificate> findOne(String token) {
        return certificateRepository.findById(token);
    }

    @Override
    public void delete(String token) {
        certificateRepository.deleteById(token);
    }
}
