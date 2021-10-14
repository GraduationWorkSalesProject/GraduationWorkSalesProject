package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinDTO;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.JoinInvalidInputException;
import GraduationWorkSalesProject.graduation.com.exception.MemberNotFoundException;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void save(MemberJoinDTO memberJoinDTO) {
        checkJoinInputsWereVerified(memberJoinDTO);

        Member member = memberJoinDTO.convert();
        member.encryptPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    private void checkJoinInputsWereVerified(MemberJoinDTO memberJoinDTO) {
        memberRepository.findByUserid(memberJoinDTO.getUserid()).ifPresent(member -> {
            throw new JoinInvalidInputException();
        });
        memberRepository.findByUsername(memberJoinDTO.getUsername()).ifPresent(member -> {
            throw new JoinInvalidInputException();
        });
        memberRepository.findByEmail(memberJoinDTO.getEmail()).ifPresent(member -> {
            throw new JoinInvalidInputException();
        });
    }

    public void checkUseridPassword(String userid, String password) {
        Optional<Member> findMember = memberRepository.findByUserid(userid);
        if(findMember.isEmpty() || !bCryptPasswordEncoder.matches(password, findMember.get().getPassword())){
            throw new MemberNotFoundException();
        }
    }

    public Optional<Member> findOneByEmail(String userid){
        return memberRepository.findByUserid(userid);
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
}
