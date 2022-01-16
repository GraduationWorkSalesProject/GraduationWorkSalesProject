package GraduationWorkSalesProject.graduation.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import GraduationWorkSalesProject.graduation.com.entity.member.MemberCertification;

public interface MemberCertificationsRepository extends JpaRepository<MemberCertification, Long> {
    void deleteByMemberId(Long memberId);

	Optional<MemberCertification> findByMemberId(Long memberId);
}
