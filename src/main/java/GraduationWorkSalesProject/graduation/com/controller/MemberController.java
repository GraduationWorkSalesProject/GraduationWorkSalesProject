package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.config.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJwtTokenRequest;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificateResponse;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificationCodeResponse;
import GraduationWorkSalesProject.graduation.com.exception.*;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.entity.certify.Certification;

import GraduationWorkSalesProject.graduation.com.dto.member.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.service.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO:
//  1. 만료된 인증 코드 모두 제거하는 로직 -> 회원가입, ID/PW 찾기 메소드에 동적 쿼리 추가 필요
//  2. Redis -> heroku or aws에서 연동하는 방법 찾기

@Api(tags = "회원 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailServiceGmailSMTP mailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final CertificationService certificationService;
    private final CertificateService certificateService;

    @ApiOperation(value = "로그인", notes = "로그인 성공 시, JWT 토큰을 Response Header에 넣어서 반환합니다")
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> login(@Validated @RequestBody MemberLoginRequest request) {
        memberService.checkUseridPassword(request.getUserid(), request.getPassword());

        Member member = memberService.findOneByUserid(request.getUserid()).get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = memberService.updateRefreshToken(request, userDetails);

        return ResponseEntity.ok()
                .header("access-token", accessToken)
                .header("refresh-token", refreshToken)
                .body(ResultResponse.of(LOGIN_SUCCESS, null));
    }

    @ApiOperation(value = "JWT 토큰 재발급", notes = "재발급 성공 시, JWT 토큰을 Response Header에 넣어서 반환합니다")
    @PostMapping(value = "/reissue", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> reIssueAccessToken(@RequestBody MemberJwtTokenRequest request) {
        jwtTokenUtil.validateAccessToken(request.getAccessToken());
        if (!jwtTokenUtil.validateRefreshToken(request.getRefreshToken())) throw new ExpiredRefreshTokenException();

        String username = getUsernameFromRefreshJwt(request.getRefreshToken());
        Member member = memberService.findOneByUsername(username).orElseThrow(UseridNotExistException::new);
        if (!member.getRefreshToken().equals(request.getRefreshToken())) throw new RefreshTokenNotMatchException();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);

        return ResponseEntity.ok()
                .header("Authorization", accessToken)
                .body(ResultResponse.of(REISSUE_SUCCESS, null));
    }

    @ApiOperation(value = "이메일 인증 코드 발송")
    @PostMapping(value = "/verification/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> sendCertificationCode(@Validated @RequestBody MemberEmailCertificationRequest request) {
        String subject = "[그라듀] 이메일 인증코드 안내";
        Certification certification = Certification.create(request.getToken());
        certificationService.save(certification);

        mailService.sendMail(request.getEmail(), subject, certification.getCertificationCode());
        CertificationCodeResponse response = new CertificationCodeResponse(certification.getCertificationCode(), certification.getExpirationDateTime());

        return ResponseEntity.ok(ResultResponse.of(SEND_MAIL_SUCCESS, response));
    }

    @ApiOperation(value = "인증 코드 검증")
    @PostMapping(value = "/verification/code", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> verifyCertificationCode(@Validated @RequestBody MemberCertificationCodeRequest request) throws ParseException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Optional<Certification> findCertification = certificationService.findOne(request.getToken());

        Certification certification = findCertification.get();
        validateCertification(request, now, findCertification);
        certificationService.delete(certification.getToken());

        Certificate certificate = Certificate.create();
        certificateService.save(certificate);
        CertificateResponse response = new CertificateResponse(certificate.getToken(), certificate.getExpirationDateTime());

        return ResponseEntity.ok(ResultResponse.of(CERTIFY_EMAIL_SUCCESS, response));
    }

    private void validateCertification(MemberCertificationCodeRequest request, ZonedDateTime now, Optional<Certification> findCertification) throws ParseException {
        if (findCertification.isEmpty() || !findCertification.get().getCertificationCode().equals(request.getCertificationCode())) {
            throw new CertificationCodeNotMatchException();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
        ZonedDateTime expirationDateTime = formatter.parse(findCertification.get().getExpirationDateTime())
                .toInstant()
                .atZone(ZoneId.of("Asia/Seoul"));

        if (now.isAfter(expirationDateTime)) {
            certificationService.delete(findCertification.get().getToken());
            throw new ExpiredCertificationCodeException();
        }
    }

    @ApiOperation(value = "이메일 중복 체크")
    @PostMapping(value = "/overlap/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkEmailExists(@Validated @RequestBody MemberEmailCheckRequest request) {
        Optional<Member> findMember = memberService.findOneByEmail(request.getEmail());
        ResultCode resultCode = (findMember.isEmpty() ? EMAIL_VALID : EMAIL_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "아이디 중복 체크")
    @PostMapping(value = "/overlap/userid", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkUseridExists(@Validated @RequestBody MemberUseridCheckRequest request) {
        Optional<Member> findMember = memberService.findOneByUserid(request.getUserid());
        ResultCode resultCode = (findMember.isEmpty() ? USERID_VALID : USERID_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "닉네임 중복 체크")
    @PostMapping(value = "/overlap/username", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> checkUsernameExists(@Validated @RequestBody MemberUsernameCheckRequest request) {
        Optional<Member> findMember = memberService.findOneByUsername(request.getUsername());
        ResultCode resultCode = (findMember.isEmpty() ? USERNAME_VALID : USERNAME_DUPLICATION);

        return ResponseEntity.ok(ResultResponse.of(resultCode, null));
    }

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/join", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> join(@Validated @RequestBody MemberJoinRequest request) throws ParseException {
        validateCertificate(request);

        memberService.save(request);
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(JOIN_SUCCESS, null));
    }

    private void validateCertificate(MemberJoinRequest request) throws ParseException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Optional<Certificate> findCertificate = certificateService.findOne(request.getToken());
        if (findCertificate.isEmpty() || !findCertificate.get().getToken().equals(request.getToken())) {
            throw new InvalidCertificateException();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
        ZonedDateTime expirationDateTime = formatter.parse(findCertificate.get().getExpirationDateTime())
                .toInstant()
                .atZone(ZoneId.of("Asia/Seoul"));
        if (now.isAfter(expirationDateTime)) {
            certificateService.delete(findCertificate.get().getToken());
            throw new InvalidCertificateException();
        }
    }

    @ApiOperation(value = "회원탈퇴")
    @ApiImplicitParam(name = "Authorization", value = "권한", example = "Bearer xxx.yyy.zzz")
    @DeleteMapping("/leave")
    public ResponseEntity<ResultResponse> leave(@RequestHeader("Authorization") String authorization) {
        String username = getUsernameFromAccessJwt(authorization);
        memberService.removeOneByUsername(username);

        return ResponseEntity.ok(ResultResponse.of(LEAVE_SUCCESS, null));
    }

    @ApiOperation(value = "아이디 찾기")
    @PostMapping(value = "/help/id", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpFindUserid(@Validated @RequestBody MemberHelpFindUseridRequest request) throws ParseException {
        validateCertificate(request);
        if (memberService.findOneByEmail(request.getEmail()).isEmpty()) {
            throw new EmailNotExistException();
        }

        Member findMember = memberService.findOneByEmail(request.getEmail()).get();
        MemberFindUseridResponse response = new MemberFindUseridResponse(findMember.getUserid());
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(FIND_USERID_SUCCESS, response));
    }

    private void validateCertificate(MemberHelpFindUseridRequest request) throws ParseException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Optional<Certificate> findCertificate = certificateService.findOne(request.getToken());
        if (findCertificate.isEmpty() || !findCertificate.get().getToken().equals(request.getToken())) {
            throw new InvalidCertificateException();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
        ZonedDateTime expirationDateTime = formatter.parse(findCertificate.get().getExpirationDateTime())
                .toInstant()
                .atZone(ZoneId.of("Asia/Seoul"));
        if (now.isAfter(expirationDateTime)) {
            certificateService.delete(findCertificate.get().getToken());
            throw new InvalidCertificateException();
        }
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping(value = "/help/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpChangePassword(@Validated @RequestBody MemberHelpFindPasswordRequest request) throws ParseException {
        validateCertificate(request);
        if (memberService.findOneByUserid(request.getUserid()).isEmpty()) {
            throw new UseridNotExistException();
        }
        memberService.changePassword(request);
        certificateService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(CHANGE_PASSWORD_SUCCESS, null));
    }

    private void validateCertificate(MemberHelpFindPasswordRequest request) throws ParseException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Optional<Certificate> findCertificate = certificateService.findOne(request.getToken());
        if (findCertificate.isEmpty() || !findCertificate.get().getToken().equals(request.getToken())) {
            throw new InvalidCertificateException();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
        ZonedDateTime expirationDateTime = formatter.parse(findCertificate.get().getExpirationDateTime())
                .toInstant()
                .atZone(ZoneId.of("Asia/Seoul"));
        if (now.isAfter(expirationDateTime)) {
            certificateService.delete(findCertificate.get().getToken());
            throw new InvalidCertificateException();
        }
    }

    private String getUsernameFromAccessJwt(String authorization) {
        return jwtTokenUtil.getUsernameFromAccessToken(authorization.substring(7));
    }

    private String getUsernameFromRefreshJwt(String authorization) {
        return jwtTokenUtil.getUsernameFromRefreshToken(authorization);
    }
}
