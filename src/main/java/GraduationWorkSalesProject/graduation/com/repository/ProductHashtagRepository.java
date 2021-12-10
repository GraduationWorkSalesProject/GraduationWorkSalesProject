package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.ProductHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductHashtagRepository extends JpaRepository<ProductHashtag, Long> {
    List<ProductHashtag> findAllByHashtagId(Long id);
}
