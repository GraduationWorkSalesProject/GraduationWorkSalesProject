package GraduationWorkSalesProject.graduation.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;


public interface SellerRepository extends JpaRepository<Seller, Long> {

	Optional<Seller> findByMemberId(Long memberId);

	void deleteByMemberId(Long memberId);

}