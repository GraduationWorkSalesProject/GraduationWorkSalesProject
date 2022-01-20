package GraduationWorkSalesProject.graduation.com.controller;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import GraduationWorkSalesProject.graduation.com.dto.member.*;
import GraduationWorkSalesProject.graduation.com.exception.*;
import GraduationWorkSalesProject.graduation.com.util.RedisUtil;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import GraduationWorkSalesProject.graduation.com.dto.certify.Certification;
import GraduationWorkSalesProject.graduation.com.dto.certify.Certificate;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificateResponse;
import GraduationWorkSalesProject.graduation.com.dto.certification.CertificationCodeResponse;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerRegisterRequest;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.service.MemberCertificationsService;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
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
    private final RedisUtil redisUtil;

    @ApiOperation(value = "로그인", notes = "Access Token은 Response Body에, Refresh Token은 Cookie의 refreshToken에 담아서 전달합니다.")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> login(
            @Validated @RequestBody MemberLoginRequest request,
            HttpServletResponse httpServletResponse) {
        final LoginResponse response = memberService.checkUseridPassword(request.getUserid(), request.getPassword());
        final JwtDTO jwtDTO = memberService.generateJwts(response.getUsername());
        response.setAccessToken(jwtDTO.getAccessToken());
        putRefreshTokenToCookie(httpServletResponse, jwtDTO.getRefreshToken());

        return ResponseEntity.ok()
                .body(ResultResponse.of(LOGIN_SUCCESS, response));
    }

    @ApiOperation(value = "JWT 토큰 재발급", notes = "Access Token은 Response Body에, Refresh Token은 Cookie의 refreshToken에 담아서 전달합니다.")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/reissue", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> reIssueAccessToken(
            @CookieValue(value = "refreshToken") Cookie cookie,
            HttpServletResponse httpServletResponse) {
        final String username = memberService.validateAndDeleteRefreshToken(cookie.getValue());
        final JwtDTO jwtDTO = memberService.generateJwts(username);
        final ReIssueJwtResponse response = new ReIssueJwtResponse(jwtDTO.getAccessToken());
        putRefreshTokenToCookie(httpServletResponse, jwtDTO.getRefreshToken());

        return ResponseEntity.ok()
                .body(ResultResponse.of(REISSUE_SUCCESS, response));
    }

    private void putRefreshTokenToCookie(HttpServletResponse httpServletResponse, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                /*.secure(true)
                .sameSite("strict")
                .domain("domain")*/
                .maxAge(14 * 24 * 60 * 60)
                .path("/")
                .build();

        httpServletResponse.addHeader("Set-Cookie", cookie.toString());
    }

    @ApiOperation(value = "이메일 인증 코드 발송")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/verification/email", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> sendCertificationCode(@Validated @RequestBody MemberEmailCertificationRequest request) {
        if (memberService.findOneByEmail(request.getEmail()).isPresent())
            throw new EmailAlreadyExistException();

        final String subject = "[그라듀] 이메일 인증코드 안내";
        final Certification certification = Certification.create(request.getToken());
        final CertificationCodeResponse response = new CertificationCodeResponse(
                certification.getCertificationCode(),
                certification.getExpirationDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")));

        mailService.sendMail(request.getEmail(), subject, certification.getCertificationCode());
        redisUtil.set(certification.getToken(), certification.getCertificationCode(), 3);
        redisUtil.set(certification.getCertificationCode(), request.getEmail(), 3);
        return ResponseEntity.ok(ResultResponse.of(SEND_MAIL_SUCCESS, response));
    }

    @ApiOperation(value = "인증 코드 검증")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/verification/code", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> verifyCertificationCode(@Validated @RequestBody MemberCertificationCodeRequest request) {
        final String certificationCode = (String) redisUtil.get(request.getToken());
        final String email = (String) redisUtil.get(certificationCode);
        if (certificationCode == null)
            throw new InvalidTokenException();
        else if (!certificationCode.equals(request.getCertificationCode()))
            throw new CertificationCodeNotMatchException();

        final Certificate certificate = Certificate.create();
        redisUtil.set(certificate.getToken(), email, 30);
        final CertificateResponse response = new CertificateResponse(
                certificate.getToken(),
                certificate.getExpirationDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")));
        redisUtil.delete(request.getToken());

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
        final String email = (String) redisUtil.get(request.getToken());
        if (email == null || !email.equals(request.getEmail()))
            throw new InvalidCertificateException();
        memberService.save(request);
        redisUtil.delete(request.getToken());

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
        final String email = (String) redisUtil.get(request.getToken());
        if (email == null || !email.equals(request.getEmail()))
            throw new InvalidCertificateException();

        final Member member = memberService.findOneByEmail(request.getEmail()).orElseThrow(EmailNotFoundException::new);
        final MemberFindUseridResponse response = new MemberFindUseridResponse(member.getUserid());
        redisUtil.delete(request.getToken());

        return ResponseEntity.ok(ResultResponse.of(FIND_USERID_SUCCESS, response));
    }

    @ApiOperation(value = "비밀번호 찾기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/help/password", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> helpChangePassword(@Validated @RequestBody MemberHelpFindPasswordRequest request) {
        final String email = (String) redisUtil.get(request.getToken());
        if (email == null)
            throw new InvalidCertificateException();

        memberService.changePassword(request.getUserid(), request.getNewPassword(), request.getCheckPassword());
        redisUtil.delete(request.getToken());

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
            @RequestParam(value = "학생증", required = true) @NotNull(message = "학생증 사진을 첨부해 주세요.") MultipartFile image,
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
    @PostMapping(value = "/admin/seller/register")
    public ResponseEntity<ResultResponse> certifyStudent() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member findMember = memberService.findOneByUsername(username).orElseThrow(InvalidCertificateException::new);

        memberCertificationsService.certify(findMember);
        return ResponseEntity.ok(ResultResponse.of(CERTIFY_STUDENT_SUCCESS, null));
    }

    @ApiOperation(value = "판매자 등록 거절")
    @PostMapping(value = "/admin/seller/reject")
    public ResponseEntity<ResultResponse> rejectStudentCertification() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member findMember = memberService.findOneByUsername(username).orElseThrow(InvalidCertificateException::new);

        memberCertificationsService.reject(findMember);
        return ResponseEntity.ok(ResultResponse.of(CERTIFY_STUDENT_REJECT, null));
    }

}
