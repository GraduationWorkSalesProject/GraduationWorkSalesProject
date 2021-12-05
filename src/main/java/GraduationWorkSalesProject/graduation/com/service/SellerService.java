package GraduationWorkSalesProject.graduation.com.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import GraduationWorkSalesProject.graduation.com.dto.seller.SellerRegisterRequest;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.member.MemberRole;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.exception.SellerInvalidException;
import GraduationWorkSalesProject.graduation.com.exception.UsernameNotFoundException;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import GraduationWorkSalesProject.graduation.com.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
	private final SellerRepository sellerRepository;
	private final MemberRepository memberRepository;

    /**
     * 판매자 정보 등록 후 member의 role을 seller로 변경하기
     *
     * @param sellerRegisterRequest
     * @param userName
     */
	@Transactional
    public void register(SellerRegisterRequest sellerRegisterRequest, String userName) {
    	final Member member = memberRepository.findByUsername(userName).orElseThrow(UsernameNotFoundException::new);
    	//판매자 등록 여부
    	sellerRepository.findByMemberId(member.getId()).ifPresent(seller -> {
            throw new SellerInvalidException();
        });

    	Seller seller = sellerRegisterRequest.convert(member);
    	sellerRepository.save(seller);

    	//member role을 user -> seller로 변경
    	member.updateRole(MemberRole.ROLE_SELLER);
    	memberRepository.save(member);
    }

}
