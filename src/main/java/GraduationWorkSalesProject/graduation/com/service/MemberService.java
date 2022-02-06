package GraduationWorkSalesProject.graduation.com.service;

import java.util.Optional;

import GraduationWorkSalesProject.graduation.com.dto.member.*;
import GraduationWorkSalesProject.graduation.com.util.RedisUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import GraduationWorkSalesProject.graduation.com.config.jwt.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.JoinInvalidInputException;
import GraduationWorkSalesProject.graduation.com.exception.LoginInvalidInputException;
import GraduationWorkSalesProject.graduation.com.exception.PasswordNotMatchException;
import GraduationWorkSalesProject.graduation.com.exception.RefreshTokenNotMatchException;
import GraduationWorkSalesProject.graduation.com.exception.UseridNotFoundException;
import GraduationWorkSalesProject.graduation.com.exception.UsernameNotFoundException;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisUtil redisUtil;
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
        Member findMember = memberRepository.findByUserid(userid).orElseThrow(UseridNotFoundException::new);
        if (!newPassword.equals(checkPassword))
            throw new PasswordNotMatchException();

        findMember.encryptPassword(bCryptPasswordEncoder.encode(newPassword));
    }

    public LoginResponse checkUseridPassword(String userid, String password) {
        Member findMember = memberRepository.findByUserid(userid).orElseThrow(LoginInvalidInputException::new);
        if (!bCryptPasswordEncoder.matches(password, findMember.getPassword()))
            throw new LoginInvalidInputException();

        return LoginResponse.builder()
                .userid(findMember.getUserid())
                .username(findMember.getUsername())
                .email(findMember.getEmail())
                .phoneNumber(findMember.getPhoneNumber())
                .imageUrl(findMember.getImage().getImageHref())
                .joinedDate(findMember.getJoinedDate())
                .role(findMember.getRole())
                .certificationStatus(findMember.getCertificationStatus())
                .address(findMember.getAddress())
                .build();
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

    public JwtDTO generateJwts(String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        redisUtil.set(username, refreshToken, 20160);
        return new JwtDTO(accessToken, refreshToken);
    }

    public String validateAndDeleteRefreshToken(String refreshToken){
        jwtTokenUtil.validateRefreshToken(refreshToken);
        final String username = jwtTokenUtil.getUsernameFromRefreshToken(refreshToken);
        redisUtil.delete(username);
        return username;
    }

    /**
     * 회원 정보(전화번호, 주소, 상세주소, 우편번호) 수정하기
     *
     * @param request
     * @param userName
     * @return 수정된 회원 정보
     */
    @Transactional
	public MemeberProfileResponse changeProfile(MemeberProfileEditRequest request, String userName) {
    	Member member = memberRepository.findByUsername(userName).orElseThrow(UsernameNotFoundException::new);
    	member.updateProfile(request.getPhoneNumber(), request.getAddress(), request.getDetailAddress(), request.getPostcode());
    	return MemeberProfileResponse.builder()
    									 .phoneNumber(member.getPhoneNumber())
    									 .address(member.getAddress().getAddress())
    									 .detailAddress(member.getAddress().getDetailAddress())
    									 .postcode(member.getAddress().getPostcode())
    									 .build();
	}
}
