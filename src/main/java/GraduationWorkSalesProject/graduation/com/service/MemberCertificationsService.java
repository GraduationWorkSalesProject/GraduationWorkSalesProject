package GraduationWorkSalesProject.graduation.com.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberStudentCertificationRequest;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.member.MemberCertification;
import GraduationWorkSalesProject.graduation.com.exception.MemberStudentCertificationRegisterException;
import GraduationWorkSalesProject.graduation.com.repository.MemberCertificationsRepository;
import GraduationWorkSalesProject.graduation.com.service.file.FileUploadService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCertificationsService {

    private final MemberCertificationsRepository memberCertificationsRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public void save(MemberStudentCertificationRequest request, Member member) throws IOException {
        //회원 롤이 이미 판매자일 경우 검사
        if (member.getRole().toString().equals("ROLE_SELLER"))
            throw new MemberStudentCertificationRegisterException();

    	MemberCertification memberCertification = MemberCertification.builder()
        		.school(request.getSchool())
        		.department(request.getDepartment())
        		.member(member)
        		.image(fileUploadService.saveTest(request.getImage()))
        		.build();
        memberCertificationsRepository.save(memberCertification);
    }

}
