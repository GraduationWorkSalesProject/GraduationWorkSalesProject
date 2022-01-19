package GraduationWorkSalesProject.graduation.com.service.certification;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberCertificationCodeRequest;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import GraduationWorkSalesProject.graduation.com.exception.CertificationCodeNotMatchException;
import GraduationWorkSalesProject.graduation.com.exception.ExpiredCertificationCodeException;
import GraduationWorkSalesProject.graduation.com.exception.InvalidTokenException;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CertificationDbService implements CertificationService{

    private final CertificationRepository certificationRepository;

    @Override
    @Transactional
    public void save(Certification certification) {
        certificationRepository.save(certification);
    }

    @Override
    @Transactional
    public void delete(String token) {
        certificationRepository.deleteById(token);
    }

    public void validateCertification(MemberCertificationCodeRequest request) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Certification findCertification = certificationRepository.findById(request.getToken())
                .orElseThrow(InvalidTokenException::new);

        if (!findCertification.getCertificationCode().equals(request.getCertificationCode()))
            throw new CertificationCodeNotMatchException();

        LocalDateTime expirationDateTime = findCertification.getExpirationDateTime();

        if (now.toLocalDateTime().isAfter(expirationDateTime)) {
            certificationRepository.deleteById(findCertification.getToken());
            throw new ExpiredCertificationCodeException();
        }
    }
}
