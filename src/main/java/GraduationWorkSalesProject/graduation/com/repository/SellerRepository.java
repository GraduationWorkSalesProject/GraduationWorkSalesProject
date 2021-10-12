package GraduationWorkSalesProject.graduation.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;


public interface SellerRepository extends JpaRepository<Seller, Long> {

}