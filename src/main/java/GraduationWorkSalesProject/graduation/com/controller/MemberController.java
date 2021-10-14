package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.config.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.mail.MailCertificationResponse;
import GraduationWorkSalesProject.graduation.com.dto.mail.MailDTO;

import GraduationWorkSalesProject.graduation.com.dto.member.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.service.JwtUserDetailsService;
import GraduationWorkSalesProject.graduation.com.service.MailServiceGmailSMTP;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static GraduationWorkSalesProject.graduation.com.dto.mail.MailTemplate.MAIL_CERTIFICATION;

@Api(tags = "회원 API")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailServiceGmailSMTP mailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @ApiOperation(value = "로그인", notes = "로그인 성공 시, JWT 토큰을 Response Header(Authorization)에 넣어서 반환합니다")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@Validated @ModelAttribute MemberLoginDTO memberLoginDTO) {
        memberService.checkUseridPassword(memberLoginDTO.getUserid(), memberLoginDTO.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberLoginDTO.getUserid());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header("Authorization", token)
                .body(new ResultResponse(ResultCode.LOGIN_SUCCESS, null));
    }

    @ApiOperation(value = "이메일 인증")
    @PostMapping("/certification/email")
    public ResponseEntity<ResultResponse> certifyEmail(@Validated @ModelAttribute MemberEmailCheckDTO memberEmailCheckDTO) {
        String subject = MAIL_CERTIFICATION.getSubject();
        String certificationCode = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));

        MailDTO mail = new MailDTO(memberEmailCheckDTO.getEmail(), subject, certificationCode);
        mailService.sendMail(mail);

        String expirationDateTime = LocalDateTime.now().plusMinutes(10).format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm"));
        MailCertificationResponse data = new MailCertificationResponse(expirationDateTime, certificationCode);

        return ResponseEntity.ok(new ResultResponse(ResultCode.SEND_MAIL_SUCCESS, data));
    }

    @ApiOperation(value = "이메일 중복 체크")
    @PostMapping("/overlap/email")
    public ResponseEntity<ResultResponse> checkEmailExists(@Validated @ModelAttribute MemberEmailCheckDTO memberEmailCheckDTO) {
        Optional<Member> findMember = memberService.findOneByEmail(memberEmailCheckDTO.getEmail());
        ResultCode resultCode = (findMember.isEmpty() ? ResultCode.EMAIL_VALID : ResultCode.EMAIL_DUPLICATION);

        return ResponseEntity.ok(new ResultResponse(resultCode, null));
    }

    @ApiOperation(value = "아이디 중복 체크")
    @PostMapping("/overlap/userid")
    public ResponseEntity<ResultResponse> checkUseridExists(@Validated @ModelAttribute MemberUseridCheckDTO memberUseridCheckDTO) {
        Optional<Member> findMember = memberService.findOneByUserid(memberUseridCheckDTO.getUserid());
        ResultCode resultCode = (findMember.isEmpty() ? ResultCode.USERID_VALID : ResultCode.USERID_DUPLICATION);

        return ResponseEntity.ok(new ResultResponse(resultCode, null));
    }

    @ApiOperation(value = "닉네임 중복 체크")
    @PostMapping("/overlap/username")
    public ResponseEntity<ResultResponse> checkUsernameExists(@Validated @ModelAttribute MemberUsernameCheckDTO memberUsernameCheckDTO) {
        Optional<Member> findMember = memberService.findOneByUsername(memberUsernameCheckDTO.getUsername());
        ResultCode resultCode = (findMember.isEmpty() ? ResultCode.USERNAME_VALID : ResultCode.USERNAME_DUPLICATION);

        return ResponseEntity.ok(new ResultResponse(resultCode, null));
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(@Validated @ModelAttribute MemberJoinDTO memberJoinDTO) {
        memberService.save(memberJoinDTO);

        return ResponseEntity.ok(new ResultResponse(ResultCode.JOIN_SUCCESS, null));
    }

    @ApiOperation(value = "회원탈퇴")
    @ApiImplicitParam(name = "Authorization", value = "권한", example = "Bearer xxx.yyy.zzz")
    @DeleteMapping("/leave")
    public ResponseEntity<ResultResponse> leave(@RequestHeader("Authorization") String authorization){
        String username = getUsernameFromJwt(authorization);
        memberService.removeOneByUsername(username);

        return ResponseEntity.ok(new ResultResponse(ResultCode.LEAVE_SUCCESS, null));
    }

    private String getUsernameFromJwt(String authorization) {
        String jwtToken = authorization.substring(7);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }
}
