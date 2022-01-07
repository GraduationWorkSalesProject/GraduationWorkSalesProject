package GraduationWorkSalesProject.graduation.com.controller;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.CERTIFY_EMAIL_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.CERTIFY_STUDENT_ENROLL_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.CERTIFY_STUDENT_REJECT;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.CERTIFY_STUDENT_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.CHANGE_PASSWORD_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.CHANGE_PROFILE_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.EMAIL_DUPLICATION;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.EMAIL_VALID;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.FIND_USERID_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.JOIN_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.LEAVE_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.LOGIN_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.REISSUE_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.SEND_MAIL_SUCCESS;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.USERID_DUPLICATION;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.USERID_VALID;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.USERNAME_DUPLICATION;
import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.USERNAME_VALID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import GraduationWorkSalesProject.graduation.com.dto.certification.CertificateResponse;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificationCodeResponse;
import GraduationWorkSalesProject.graduation.com.dto.member.LoginResponse;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberCertificationCodeRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberEmailCertificationRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberEmailCheckRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberFindUseridResponse;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberHelpFindPasswordRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberHelpFindUseridRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJwtTokenRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberLoginRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberStudentCertificationRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberUseridCheckRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberUsernameCheckRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemeberProfileEditRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemeberProfileResponse;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerRegisterRequest;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.EmailNotFoundException;
import GraduationWorkSalesProject.graduation.com.exception.InvalidCertificateException;
import GraduationWorkSalesProject.graduation.com.service.MemberCertificationsService;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import GraduationWorkSalesProject.graduation.com.service.certificate.CertificateService;
import GraduationWorkSalesProject.graduation.com.service.certification.CertificationService;
import GraduationWorkSalesProject.graduation.com.service.mail.MailServiceGmailSMTP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberCertificationsService memberCertificationsService;
    private final MailServiceGmailSMTP mailService;
    private final CertificationService certificationService;
    private final CertificateService certificateService;

    @ApiOperation(value = "로그인", notes = "로그인 성공 시, JWT 토큰을 Response Header에 넣어서 반환합니다")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> login(@Validated @RequestBody MemberLoginRequest request) {
        final LoginResponse response = memberService.checkUseridPassword(request.getUserid(), request.getPassword());

        final String accessToken = memberService.createAccessTokenByUserid(request.getUserid());
        final String refreshToken = memberService.updateRefreshToken(request);

        return ResponseEntity.ok()
                .header("access-token", accessToken)
                .header("refresh-token", refreshToken)
                .body(ResultResponse.of(LOGIN_SUCCESS, response));
    }

    @ApiOperation(value = "JWT 토큰 재발급", notes = "재발급 성공 시, JWT 토큰을 Response Header에 넣어서 반환합니다")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/reissue", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> reIssueAccessToken(@Validated @RequestBody MemberJwtTokenRequest request) {
        memberService.validateJwts(request.getAccessToken(), request.getRefreshToken());

        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final String accessToken = memberService.createAccessTokenByUsername(username);

        return ResponseEntity.ok()
                .header("access-token", accessToken)
                .body(ResultResponse.of(REISSUE_SUCCESS, null));
    }

    @ApiOperation(value = "이메일 인증 코드 발송")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/verification/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> sendCertificationCode(@Validated @RequestBody MemberEmailCertificationRequest request) {
        final String subject = "[그라듀] 이메일 인증코드 안내";
        final Certification certification = Certification.create(request.getToken());
        certificationService.save(certification);

        mailService.sendMail(request.getEmail(), subject, certification.getCertificationCode());
        final CertificationCodeResponse response = new CertificationCodeResponse(
                certification.getCertificationCode(),
                certification.getExpirationDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")));

        return ResponseEntity.ok(ResultResponse.of(SEND_MAIL_SUCCESS, response));
    }

    @ApiOperation(value = "인증 코드 검증")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/verification/code", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> verifyCertificationCode(@Validated @RequestBody MemberCertificationCodeRequest request) {
        certificationService.validateCertification(request);
        certificationService.delete(request.getToken());

        final Certificate certificate = Certificate.create();
        certificateService.save(certificate);
        final CertificateResponse response = new CertificateResponse(
                certificate.getToken(),
                certificate.getExpirationDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")));

        return ResponseEntity.ok(ResultResponse.of(CERTIFY_EMAIL_SUCCESS, response));
    }

    @ApiOperation(value = "이메일 중복 체크")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/overlap/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkEmailExists(@Validated @RequestBody MemberEmailCheckRequest request) {
        final Optional<Member> findMember = memberService.findOneByEmail(request.getEmail());
        final ResultCode resultCode = (findMember.isEmpty() ? EMAIL_VALID : EMAIL_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "아이디 중복 체크")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/overlap/userid", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkUseridExists(@Validated @RequestBody MemberUseridCheckRequest request) {
        final Optional<Member> findMember = memberService.findOneByUserid(request.getUserid());
        final ResultCode resultCode = (findMember.isEmpty() ? USERID_VALID : USERID_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "닉네임 중복 체크")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/overlap/username", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkUsernameExists(@Validated @RequestBody MemberUsernameCheckRequest request) {
        final Optional<Member> findMember = memberService.findOneByUsername(request.getUsername());
        final ResultCode resultCode = (findMember.isEmpty() ? USERNAME_VALID : USERNAME_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "회원가입")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/join", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> join(@Validated @RequestBody MemberJoinRequest request) {
        certificateService.validateCertificate(request.getToken());
        certificateService.delete(request.getToken());
        memberService.save(request);

        return ResponseEntity.ok(ResultResponse.of(JOIN_SUCCESS, null));
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/leave")
    public ResponseEntity<ResultResponse> leave() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        memberService.removeOneByUsername(username);

        return ResponseEntity.ok(ResultResponse.of(LEAVE_SUCCESS, null));
    }

    @ApiOperation(value = "아이디 찾기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/help/id", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpFindUserid(@Validated @RequestBody MemberHelpFindUseridRequest request) {
        certificateService.validateCertificate(request.getToken());

        final Member member = memberService.findOneByEmail(request.getEmail()).orElseThrow(EmailNotFoundException::new);
        final MemberFindUseridResponse response = new MemberFindUseridResponse(member.getUserid());
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(FIND_USERID_SUCCESS, response));
    }

    @ApiOperation(value = "비밀번호 찾기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/help/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpChangePassword(@Validated @RequestBody MemberHelpFindPasswordRequest request) {
        certificateService.validateCertificate(request.getToken());

        memberService.changePassword(request.getUserid(), request.getNewPassword(), request.getCheckPassword());
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(CHANGE_PASSWORD_SUCCESS, null));
    }



    @ApiOperation(value = "회원 정보 수정")
    @PostMapping(value = "/members/profile", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> editProfile(@Validated @RequestBody MemeberProfileEditRequest request) {
    	final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	final UserDetails userDetails = (UserDetails) principal;

    	final MemeberProfileResponse response = memberService.changeProfile(request, userDetails.getUsername());

        return ResponseEntity.ok(ResultResponse.of(CHANGE_PROFILE_SUCCESS, response));
    }


    @ApiOperation(value = "회원 대학생 인증 등록")
    @PostMapping(value = "/student/certification", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> registerStudentCertification(
    			@RequestParam(value = "학생증",required = true) @NotNull(message = "학생증 사진을 첨부해 주세요.") MultipartFile image,
    			@Validated MemberStudentCertificationRequest memberStudentCertificationRequest,
    			@Validated SellerRegisterRequest sellerRegisterRequest) throws IOException {
    	final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member findMember = memberService.findOneByUsername(username).orElseThrow(InvalidCertificateException::new);


    	log.info("이미지 ContentType : " + image.getContentType());
    	log.info("이미지 이름 : " + image.getOriginalFilename());
    	log.info("학과 : " + memberStudentCertificationRequest.getDepartment());
    	memberCertificationsService.register(memberStudentCertificationRequest, sellerRegisterRequest, image, findMember);
    	return ResponseEntity.ok(ResultResponse.of(CERTIFY_STUDENT_ENROLL_SUCCESS, null));
    }

    @ApiOperation(value = "판매자 등록")
    @PostMapping(value = "/seller")
    public ResponseEntity<ResultResponse> certifyStudent(){
    	final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member findMember = memberService.findOneByUsername(username).orElseThrow(InvalidCertificateException::new);

    	memberCertificationsService.certify(findMember);
    	return ResponseEntity.ok(ResultResponse.of(CERTIFY_STUDENT_SUCCESS, null));
    }

    @ApiOperation(value = "판매자 등록 거절")
    @PostMapping(value = "/seller")
    public ResponseEntity<ResultResponse> rejectStudentCertification(){
    	final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member findMember = memberService.findOneByUsername(username).orElseThrow(InvalidCertificateException::new);

    	memberCertificationsService.reject(findMember);
    	return ResponseEntity.ok(ResultResponse.of(CERTIFY_STUDENT_REJECT, null));
    }

}
