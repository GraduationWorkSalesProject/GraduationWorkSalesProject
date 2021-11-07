package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.config.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJwtTokenRequest;
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
    public void changePassword(String userid, String newPassword, String checkPassword) {
        Member findMember = memberRepository.findByUserid(userid).orElseThrow(UseridNotExistException::new);
        if (!newPassword.equals(checkPassword))
            throw new PasswordNotMatchException();

        findMember.encryptPassword(bCryptPasswordEncoder.encode(newPassword));
    }

    public void checkUseridPassword(String userid, String password) {
        Optional<Member> findMember = memberRepository.findByUserid(userid);
        if (findMember.isEmpty() || !bCryptPasswordEncoder.matches(password, findMember.get().getPassword()))
            throw new LoginInvalidInputException();
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
        final Member member = memberRepository.findByUserid(request.getUserid()).orElseThrow(UseridNotExistException::new);
        if (member.getRefreshToken() == null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());
            final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
            member.updateRefreshToken(refreshToken);
        }
        return member.getRefreshToken();
    }

    public String createAccessTokenByUserid(String userid) {
        final Member member = memberRepository.findByUserid(userid).orElseThrow(UseridNotExistException::new);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());
        return jwtTokenUtil.generateAccessToken(userDetails);
    }

    public String createAccessTokenByUsername(String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateAccessToken(userDetails);
    }

    public String getUsernameFromAccessJwt(String accessToken) {
        return jwtTokenUtil.getUsernameFromAccessToken(accessToken.substring(7));
    }

    public String getUsernameFromRefreshJwt(String refreshToken) {
        final String username = jwtTokenUtil.getUsernameFromRefreshToken(refreshToken);
        checkRefreshToken(refreshToken);

        return username;
    }

    private void checkRefreshToken(String refreshToken){
        final String username = getUsernameFromRefreshJwt(refreshToken);
        final Member member = memberRepository.findByUsername(username).orElseThrow(UseridNotExistException::new);
        if (!member.getRefreshToken().equals(refreshToken))
            throw new RefreshTokenNotMatchException();
    }

    public void validateJwts(String accessToken, String refreshToken) {
        jwtTokenUtil.validateAccessToken(accessToken);
        jwtTokenUtil.validateRefreshToken(refreshToken);
    }
}
