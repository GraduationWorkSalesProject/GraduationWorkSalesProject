package GraduationWorkSalesProject.graduation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import GraduationWorkSalesProject.graduation.com.entity.member.MemberCertification;

public interface MemberCertificationsRepository extends JpaRepository<MemberCertification, Long> {
    void deleteByMemberId(String username);
}
