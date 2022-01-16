package GraduationWorkSalesProject.graduation.com.service;

import org.springframework.stereotype.Service;

import GraduationWorkSalesProject.graduation.com.dto.seller.SellerResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.exception.SellerNotFoundException;
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
	 * 회원 닉네임으로 판매자 정보 조회
	 *
	 * @param userName
	 * @return
	 */
	public SellerResponse getSellerInfo(String userName) {
		Member member = memberRepository.findByUsername(userName).orElseThrow(UsernameNotFoundException::new);
		Seller seller = sellerRepository.findByMemberId(member.getId()).orElseThrow(SellerNotFoundException::new);
		return seller.convert();
	}

}
