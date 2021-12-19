package GraduationWorkSalesProject.graduation.com.service.certificate;

import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.exception.InvalidCertificateException;
import GraduationWorkSalesProject.graduation.com.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificateDbService implements CertificateService {

    private final CertificateRepository certificateRepository;

    @Override
    @Transactional
    public void save(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    @Override
    @Transactional
    public void delete(String token) {
        certificateRepository.deleteById(token);
    }

    public void validateCertificate(String token) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Optional<Certificate> findCertificate = certificateRepository.findById(token);

        if (findCertificate.isEmpty())
            throw new InvalidCertificateException();

        LocalDateTime expirationDateTime = findCertificate.get().getExpirationDateTime();

        if (now.toLocalDateTime().isAfter(expirationDateTime)) {
            certificateRepository.deleteById(findCertificate.get().getToken());
            throw new InvalidCertificateException();
        }
    }
}
