package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.member.MemberJoinDTO;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.EmailDuplicationException;
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
    public Long save(MemberJoinDTO memberJoinDTO) {
        Optional<Member> findMember = memberRepository.findByEmail(memberJoinDTO.getEmail());
        findMember.ifPresent(member -> {
            throw new EmailDuplicationException();
        });

        Member member = memberJoinDTO.convert();
        member.encryptPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.save(member);

        return member.getId();
    }

    public void checkEmailPassword(String email, String password) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isEmpty() || !bCryptPasswordEncoder.matches(password, findMember.get().getPassword())){
            throw new MemberNotFoundException();
        }
    }

    // TODO: 2021. 10. 5.
    //  [판매 중인 상품 검증] & [미 완료 거래 여부 검증] 로직 추가 필요
    @Transactional
    public void removeByEmail(String email){
        memberRepository.deleteByEmail(email);
    }

    public Optional<Member> findOneByEmail(String email){
        return memberRepository.findByEmail(email);
    }
}
