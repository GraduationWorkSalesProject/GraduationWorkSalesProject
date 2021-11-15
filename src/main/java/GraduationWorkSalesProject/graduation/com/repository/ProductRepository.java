package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findProductById(Long productId);
    List<Product> findTop10ByOrderByProductRegisterDateDesc();
    List<Product> findProductsByProductNameContaining(String keyword);

}
