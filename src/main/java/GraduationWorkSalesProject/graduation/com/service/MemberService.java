package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // TODO: 2021. 10. 5.
    //  이메일 인증 로직 추가 필요
    @Transactional
    public Long save(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    public boolean isDuplicatedEmail(String email){
        return memberRepository.findByEmail(email).orElse(null) != null;
    }

    // TODO: 2021. 10. 5.
    //  [판매 중인 상품 검증] & [미 완료 거래 여부 검증] 로직 추가 필요
    @Transactional
    public void resign(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
