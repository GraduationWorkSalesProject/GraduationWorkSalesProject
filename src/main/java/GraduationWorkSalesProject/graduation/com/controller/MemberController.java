package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.config.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.ResultResponse;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberLoginDTO;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberEmailCheckDTO;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinDTO;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.service.JwtUserDetailsService;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@Validated MemberLoginDTO memberLoginDTO) {
        memberService.checkEmailPassword(memberLoginDTO.getEmail(), memberLoginDTO.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberLoginDTO.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header("Authorization", token)
                .body(new ResultResponse(ResultCode.LOGIN_SUCCESS));
    }

    @PostMapping("/overlap")
    public ResponseEntity<ResultResponse> checkEmailExists(@Validated MemberEmailCheckDTO memberEmailCheckDTO) {
        Optional<Member> findMember = memberService.findOneByEmail(memberEmailCheckDTO.getEmail());
        ResultCode resultCode = (findMember.isEmpty() ? ResultCode.EMAIL_VALID : ResultCode.EMAIL_DUPLICATION);

        return ResponseEntity.ok(new ResultResponse(resultCode));
    }

    @PostMapping("/join")
    public ResponseEntity<ResultResponse> join(@Validated MemberJoinDTO memberJoinDTO) {
        memberService.save(memberJoinDTO);

        return ResponseEntity.ok(new ResultResponse(ResultCode.JOIN_SUCCESS));
    }

    @DeleteMapping("/leave")
    public ResponseEntity<ResultResponse> leave(@RequestHeader("Authorization") String authorization){
        String jwtToken = authorization.substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        memberService.removeByEmail(email);

        return ResponseEntity.ok(new ResultResponse(ResultCode.LEAVE_SUCCESS));
    }
}
