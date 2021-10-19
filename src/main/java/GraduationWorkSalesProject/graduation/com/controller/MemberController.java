package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.config.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificateResponse;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificationCodeResponse;
import GraduationWorkSalesProject.graduation.com.exception.*;
import GraduationWorkSalesProject.graduation.com.redis.Certificate;
import GraduationWorkSalesProject.graduation.com.redis.Certification;

import GraduationWorkSalesProject.graduation.com.dto.member.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.service.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO:
//  Redis 적용해서 인증코드와 만료시간 서버에서 체크하도록 로직 수정 -> 관련 DTO 수정 필요
//  이메일 인증 API -> Redis에 이메일, 인증코드, 만료시간 저장 -> Request로 인증코드만 받기 -> 인증코드 불일치&인증시간 만료 오류 추가하기
//  아이디&비밀번호 찾기 API -> Redis에 이메일 저장되어 있으므로, Request로 따로 받지 않기
//  Redis + Refresh Token

// TODO:
//  @ApiModelProperty로 모두 변경 -> position 기능 사용

@Api(tags = "회원 API")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailServiceGmailSMTP mailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final CertificationRedisService certificationRedisService;
    private final CertificateRedisService certificateRedisService;

    @ApiOperation(value = "로그인", notes = "로그인 성공 시, JWT 토큰을 Response Header(Authorization)에 넣어서 반환합니다")
    @PostMapping(value = "/login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ResultResponse> login(@Validated @ModelAttribute MemberLoginRequest request) {
        memberService.checkUseridPassword(request.getUserid(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserid());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header("Authorization", token)
                .body(ResultResponse.of(LOGIN_SUCCESS, null));
    }

    @ApiOperation(value = "이메일 인증 코드 발송")
    @PostMapping(value = "/verification/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> sendCertificationCode(@Validated @RequestBody MemberEmailCertificationRequest request) {
        String subject = "[그라듀] 이메일 인증코드 안내";
        Certification certification = Certification.create(request.getToken());
        certificationRedisService.save(certification);

        mailService.sendMail(request.getEmail(), subject, certification.getCertificationCode());
        CertificationCodeResponse response = new CertificationCodeResponse(certification.getCertificationCode(), certification.getExpirationDateTime());

        return ResponseEntity.ok(ResultResponse.of(SEND_MAIL_SUCCESS, response));
    }

    @ApiOperation(value = "인증 코드 검증")
    @PostMapping(value = "/verification/code", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> verifyCertificationCode(@Validated @RequestBody MemberCertificationCodeRequest request) {
        Certification certification = certificationRedisService.findOne(request.getToken()).get();

        if(!certification.getCertificationCode().equals(request.getCertificationCode())){
            throw new CertificationCodeNotMatchException();
        }
        certificationRedisService.delete(certification.getToken());

        Certificate certificate = Certificate.create(request.getToken());
        certificateRedisService.save(certificate);
        CertificateResponse response = new CertificateResponse(certificate.getCertificate(), certificate.getExpirationDateTime());

        return ResponseEntity.ok(ResultResponse.of(CERTIFY_EMAIL_SUCCESS, response));
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
    @PostMapping(value = "/join", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ResultResponse> join(@Validated @ModelAttribute MemberJoinRequest request) {
        if(certificateRedisService.findOne(request.getToken()).isEmpty()){
            throw new InvalidCertificateException();
        }
        memberService.save(request);
        certificateRedisService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(JOIN_SUCCESS, null));
    }

    @ApiOperation(value = "회원탈퇴")
    @ApiImplicitParam(name = "Authorization", value = "권한", example = "Bearer xxx.yyy.zzz")
    @DeleteMapping("/leave")
    public ResponseEntity<ResultResponse> leave(@RequestHeader("Authorization") String authorization){
        String username = getUsernameFromJwt(authorization);
        memberService.removeOneByUsername(username);

        return ResponseEntity.ok(ResultResponse.of(LEAVE_SUCCESS, null));
    }

    @ApiOperation(value = "아이디 찾기")
    @PostMapping(value = "/help/id", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpFindUserid(@Validated @RequestBody MemberHelpFindUseridRequest request) {
        if(certificateRedisService.findOne(request.getToken()).isEmpty()){
            throw new InvalidCertificateException();
        }
        if(memberService.findOneByEmail(request.getEmail()).isEmpty()){
            throw new EmailNotExistException();
        }

        Member findMember = memberService.findOneByEmail(request.getEmail()).get();
        MemberFindUseridResponse response = new MemberFindUseridResponse(findMember.getUserid());
        certificateRedisService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(FIND_USERID_SUCCESS, response));
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping(value = "/help/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpChangePassword(@Validated @RequestBody MemberHelpFindPasswordRequest request) {
        if(certificateRedisService.findOne(request.getToken()).isEmpty()){
            throw new InvalidCertificateException();
        }
        if(memberService.findOneByUserid(request.getUserid()).isEmpty()){
            throw new UseridNotExistException();
        }
        memberService.changePassword(request);
        certificateRedisService.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(CHANGE_PASSWORD_SUCCESS, null));
    }

    private String getUsernameFromJwt(String authorization) {
        String jwtToken = authorization.substring(7);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }
}
