package GraduationWorkSalesProject.graduation.com.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import GraduationWorkSalesProject.graduation.com.config.jwt.JwtTokenUtil;
import GraduationWorkSalesProject.graduation.com.dto.member.LoginResponse;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemberLoginRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemeberProfileEditRequest;
import GraduationWorkSalesProject.graduation.com.dto.member.MemeberProfileResponse;
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

        LoginResponse response = LoginResponse.builder()
                .userid(findMember.getUserid())
                .username(findMember.getUsername())
                .email(findMember.getEmail())
                .phoneNumber(findMember.getPhoneNumber())
                .imageUrl("test")//findMember.getImage().getImageHref()
                .joinedDate(findMember.getJoinedDate())
                .role(findMember.getRole())
                .certificationStatus(findMember.getCertificationStatus())
                .address(findMember.getAddress())
                .build();

        return response;
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
        final Member member = memberRepository.findByUserid(request.getUserid()).orElseThrow(UseridNotFoundException::new);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        member.updateRefreshToken(refreshToken);
        return refreshToken;
    }

    public String createAccessTokenByUserid(String userid) {
        final Member member = memberRepository.findByUserid(userid).orElseThrow(UseridNotFoundException::new);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());
        return jwtTokenUtil.generateAccessToken(userDetails);
    }

    public String createAccessTokenByUsername(String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateAccessToken(userDetails);
    }

    private void checkRefreshToken(String refreshToken){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member member = memberRepository.findByUsername(username).orElseThrow(UseridNotFoundException::new);
        if (!member.getRefreshToken().equals(refreshToken))
            throw new RefreshTokenNotMatchException();
    }

    public void validateJwts(String accessToken, String refreshToken) {
        jwtTokenUtil.validateAccessToken(accessToken);
        jwtTokenUtil.validateRefreshToken(refreshToken);
        checkRefreshToken(refreshToken);
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
