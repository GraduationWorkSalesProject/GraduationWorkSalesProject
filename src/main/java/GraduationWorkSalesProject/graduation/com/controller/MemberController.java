package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.dto.certification.CertificateResponse;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificationCodeResponse;
import GraduationWorkSalesProject.graduation.com.dto.member.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.EmailNotExistException;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import GraduationWorkSalesProject.graduation.com.service.certificate.CertificateService;
import GraduationWorkSalesProject.graduation.com.service.certification.CertificationService;
import GraduationWorkSalesProject.graduation.com.service.mail.MailServiceGmailSMTP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO:
//  1. Redis -> heroku or aws에서 연동하는 방법 찾기

@Api(tags = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailServiceGmailSMTP mailService;
    private final CertificationService certificationService;
    private final CertificateService certificateService;

    @ApiOperation(value = "로그인", notes = "로그인 성공 시, JWT 토큰을 Response Header에 넣어서 반환합니다")
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> login(@Validated @RequestBody MemberLoginRequest request) {
        memberService.checkUseridPassword(request.getUserid(), request.getPassword());

        final String accessToken = memberService.createAccessTokenByUserid(request.getUserid());
        final String refreshToken = memberService.updateRefreshToken(request);

        return ResponseEntity.ok()
                .header("access-token", accessToken)
                .header("refresh-token", refreshToken)
                .body(ResultResponse.of(LOGIN_SUCCESS, null));
    }

    @ApiOperation(value = "JWT 토큰 재발급", notes = "재발급 성공 시, JWT 토큰을 Response Header에 넣어서 반환합니다")
    @PostMapping(value = "/reissue", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> reIssueAccessToken(@Validated @RequestBody MemberJwtTokenRequest request) {
        memberService.validateJwts(request.getAccessToken(), request.getRefreshToken());

        final String username = memberService.getUsernameFromRefreshJwt(request.getRefreshToken());
        final String accessToken = memberService.createAccessTokenByUsername(username);

        return ResponseEntity.ok()
                .header("access-token", accessToken)
                .body(ResultResponse.of(REISSUE_SUCCESS, null));
    }

    @ApiOperation(value = "이메일 인증 코드 발송")
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
    @PostMapping(value = "/overlap/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkEmailExists(@Validated @RequestBody MemberEmailCheckRequest request) {
        final Optional<Member> findMember = memberService.findOneByEmail(request.getEmail());
        final ResultCode resultCode = (findMember.isEmpty() ? EMAIL_VALID : EMAIL_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "아이디 중복 체크")
    @PostMapping(value = "/overlap/userid", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkUseridExists(@Validated @RequestBody MemberUseridCheckRequest request) {
        final Optional<Member> findMember = memberService.findOneByUserid(request.getUserid());
        final ResultCode resultCode = (findMember.isEmpty() ? USERID_VALID : USERID_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "닉네임 중복 체크")
    @PostMapping(value = "/overlap/username", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkUsernameExists(@Validated @RequestBody MemberUsernameCheckRequest request) {
        final Optional<Member> findMember = memberService.findOneByUsername(request.getUsername());
        final ResultCode resultCode = (findMember.isEmpty() ? USERNAME_VALID : USERNAME_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/join", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> join(@Validated @RequestBody MemberJoinRequest request) {
        certificateService.validateCertificate(request.getToken());
        certificateService.delete(request.getToken());
        memberService.save(request);

        return ResponseEntity.ok(ResultResponse.of(JOIN_SUCCESS, null));
    }

    @ApiOperation(value = "회원탈퇴")
    @ApiImplicitParam(name = "Authorization", value = "권한", example = "Bearer xxx.yyy.zzz")
    @DeleteMapping("/leave")
    public ResponseEntity<ResultResponse> leave(@RequestHeader("Authorization") String authorization) {
        final String username = memberService.getUsernameFromAccessJwt(authorization);
        memberService.removeOneByUsername(username);

        return ResponseEntity.ok(ResultResponse.of(LEAVE_SUCCESS, null));
    }

    @ApiOperation(value = "아이디 찾기")
    @PostMapping(value = "/help/id", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpFindUserid(@Validated @RequestBody MemberHelpFindUseridRequest request) {
        certificateService.validateCertificate(request.getToken());

        final Member member = memberService.findOneByEmail(request.getEmail()).orElseThrow(EmailNotExistException::new);
        final MemberFindUseridResponse response = new MemberFindUseridResponse(member.getUserid());
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(FIND_USERID_SUCCESS, response));
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping(value = "/help/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpChangePassword(@Validated @RequestBody MemberHelpFindPasswordRequest request) {
        certificateService.validateCertificate(request.getToken());

        memberService.changePassword(request.getUserid(), request.getNewPassword(), request.getCheckPassword());
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(CHANGE_PASSWORD_SUCCESS, null));
    }
}
