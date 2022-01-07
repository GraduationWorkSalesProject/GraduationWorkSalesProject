package GraduationWorkSalesProject.graduation.com.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberStudentCertificationRequest;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerRegisterRequest;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.member.MemberCertification;
import GraduationWorkSalesProject.graduation.com.entity.member.MemberRole;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.exception.MemberStudentCertificationRegisterException;
import GraduationWorkSalesProject.graduation.com.exception.SellerInvalidException;
import GraduationWorkSalesProject.graduation.com.exception.StudentCertificationNotFoundException;
import GraduationWorkSalesProject.graduation.com.repository.MemberCertificationsRepository;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import GraduationWorkSalesProject.graduation.com.repository.SellerRepository;
import GraduationWorkSalesProject.graduation.com.service.file.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCertificationsService {

    private final MemberCertificationsRepository memberCertificationsRepository;
    private final SellerRepository sellerRepository;
    private final FileUploadService fileUploadService;
    private final MemberRepository memberRepository;



    /**
     * 대학생 인증 등록<br>
     *
     * 판매자 테이블에 판매자 정보와 학생 인증 테이블에 인증 정보를 저장한다.
     * @param memberStudentCertificationRequest
     * @param sellerRegisterRequest
     * @param image
     * @param member
     * @throws IOException
     */
    @Transactional
	public void register(MemberStudentCertificationRequest memberStudentCertificationRequest,
			SellerRegisterRequest sellerRegisterRequest, MultipartFile image, Member member) throws IOException {

    	checkMemberCertification(member);

        //학생 인증 테이블에 등록
    	MemberCertification memberCertification = MemberCertification.builder()
        		.school(memberStudentCertificationRequest.getSchool())
        		.department(memberStudentCertificationRequest.getDepartment())
        		.member(member)
        		.certificationDate(java.sql.Date.valueOf(LocalDate.now()))
        		.image(fileUploadService.saveTest(image))
        		.build();
    	memberCertificationsRepository.save(memberCertification);

    	//임시로 판매자 테이블에 정보 저장
    	Seller seller = sellerRegisterRequest.convert(member);
    	sellerRepository.save(seller);
	}

    /**
     * 학생 인증 등록이 가능한 지 검사
     *
     * @param member
     */
    private void checkMemberCertification(Member member) {
    	//회원 롤이 이미 판매자일 경우 검사
        if (member.getRole().toString().equals("ROLE_SELLER"))
            throw new SellerInvalidException();
        //이미 인증 요청을 한 경우
        memberCertificationsRepository.findByMemberId(member.getId()).ifPresent(memberCertification->{
        	throw new MemberStudentCertificationRegisterException();
        });
    }

    /**
     * 판매자 정보 등록 후 member의 role을 seller로 변경하기
     *
     * @param sellerRegisterRequest
     * @param userName
     */
	@Transactional
    public boolean certify(Member member) {
    	//판매자 등록 여부
        if (member.getRole().toString().equals("ROLE_SELLER"))
            throw new SellerInvalidException();

        //학생 인증 등록 먼저 안한 경우
        memberCertificationsRepository.findByMemberId(member.getId()).orElseThrow(StudentCertificationNotFoundException::new);
        sellerRepository.findByMemberId(member.getId()).orElseThrow(StudentCertificationNotFoundException::new);

    	//학생 인증 정보 삭제
    	memberCertificationsRepository.deleteByMemberId(member.getId());

    	//member role을 user -> seller로 변경
    	member.updateRole(MemberRole.ROLE_SELLER);
    	memberRepository.save(member);
    	return true;
    }

	/**
	 * 회원 인증 거절 <br>
	 * 학생 인증 정보 삭제 & 판매자 정보 삭제
	 *
	 * @param member
	 */
	public void reject(Member member) {
		//판매자 등록 여부
        if (member.getRole().toString().equals("ROLE_SELLER"))
            throw new SellerInvalidException();

        //학생 인증 등록 먼저 안한 경우
        memberCertificationsRepository.findByMemberId(member.getId()).orElseThrow(StudentCertificationNotFoundException::new);
        sellerRepository.findByMemberId(member.getId()).orElseThrow(StudentCertificationNotFoundException::new);

        //학생 인증 정보 삭제
    	memberCertificationsRepository.deleteByMemberId(member.getId());

    	//판매자 정보 삭제
    	sellerRepository.deleteByMemberId(member.getId());

	}

}
