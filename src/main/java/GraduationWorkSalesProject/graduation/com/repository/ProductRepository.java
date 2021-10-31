package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findProductById(Long product_id);
    List<Product> findProductsByCategoriesId(Long category_id);
    List<Product> findTop10ByOrderByProductRegisterDateDesc();
    List<Product> findProductsByProductNameContaining(String keyword);

}
