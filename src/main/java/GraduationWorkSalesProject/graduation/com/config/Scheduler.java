package GraduationWorkSalesProject.graduation.com.config;

import GraduationWorkSalesProject.graduation.com.repository.CertificateRepository;
import GraduationWorkSalesProject.graduation.com.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
//@Component
@RequiredArgsConstructor
public class Scheduler {

    /*private final CertificationRepository certificationRepository;
    private final CertificateRepository certificateRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void clearExpiredTokens(){
        log.info("clear expired tokens!");
        final LocalDateTime before3Minutes = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).minusMinutes(3L).toLocalDateTime();
        final LocalDateTime before30Minutes = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).minusMinutes(30L).toLocalDateTime();

        certificationRepository.deleteByExpirationDateTimeLessThan(before3Minutes);
        certificateRepository.deleteByExpirationDateTimeLessThan(before30Minutes);
    }*/
}
