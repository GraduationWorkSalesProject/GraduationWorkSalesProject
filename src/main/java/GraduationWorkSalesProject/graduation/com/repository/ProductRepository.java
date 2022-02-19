package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findTop8ByOrderByRegisterDateDesc();

	List<Product> findAllByNameContaining(String keyword);

	List<Product> findByMemberIdOrderByRegisterDate(Long memberId);

	@Query(value = "select p from Product p left join Like l on p.id = l.product where p.member.id = :memberId group by p.id order by count(l) DESC")
	List<Product> findByMemberIdOrderByLikeCountDESC(@Param("memberId") Long memberId);
}
