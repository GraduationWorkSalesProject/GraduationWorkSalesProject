package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.config.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberHelpFindPasswordRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberLoginRequest;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.*;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @Transactional
    public void save(MemberJoinRequest memberJoinRequest) {
        checkJoinInputsWereVerified(memberJoinRequest);

        Member member = memberJoinRequest.convert();
        member.encryptPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    private void checkJoinInputsWereVerified(MemberJoinRequest memberJoinRequest) {
        memberRepository.findByUserid(memberJoinRequest.getUserid()).ifPresent(member -> {
            throw new JoinInvalidInputException();
        });
        memberRepository.findByUsername(memberJoinRequest.getUsername()).ifPresent(member -> {
            throw new JoinInvalidInputException();
        });
        memberRepository.findByEmail(memberJoinRequest.getEmail()).ifPresent(member -> {
            throw new JoinInvalidInputException();
        });
    }

    @Transactional
    public void changePassword(MemberHelpFindPasswordRequest request) {
        Member findMember = memberRepository.findByUserid(request.getUserid()).get();
        if (!request.getNewPassword().equals(request.getCheckPassword())) {
            throw new PasswordNotMatchException();
        }

        findMember.encryptPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
    }

    public void checkUseridPassword(String userid, String password){
        Optional<Member> findMember = memberRepository.findByUserid(userid);
        if (findMember.isEmpty() || !bCryptPasswordEncoder.matches(password, findMember.get().getPassword()))
            throw new LoginInvalidInputException();
    }

    public String createAccessToken(String userid) {
        Member findMember = memberRepository.findByUserid(userid).orElseThrow(UseridNotExistException::new);
        UserDetails userDetails = userDetailsService.loadUserByUsername(findMember.getUsername());
        return jwtTokenUtil.generateAccessToken(userDetails);
    }

    public Optional<Member> findOneByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findOneByUserid(String userid) {
        return memberRepository.findByUserid(userid);
    }

    public Optional<Member> findOneByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    // TODO: 2021. 10. 5.
    //  [판매 중인 상품 검증] & [미 완료 거래 여부 검증] 로직 추가 필요
    @Transactional
    public void removeOneByUsername(String username) {
        memberRepository.deleteByUsername(username);
    }

    @Transactional
    public String updateRefreshToken(MemberLoginRequest request) {
        Member member = memberRepository.findByUserid(request.getUserid()).get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());

        if (member.getRefreshToken() == null || !jwtTokenUtil.validateRefreshToken(member.getRefreshToken())) {
            String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
            member.updateRefreshToken(refreshToken);
        }

        return member.getRefreshToken();
    }
}
