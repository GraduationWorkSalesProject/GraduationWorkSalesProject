package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import GraduationWorkSalesProject.graduation.com.entity.product.ProductHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductHashtagRepository extends JpaRepository<ProductHashtag, Long> {
    List<ProductHashtag> findAllByHashtagId(Long id);

    @Query(value = "select count(h) as cnt, h.hashtag.id, h.hashtag.hashtagName from ProductHashtag h group by h.hashtag order by cnt desc")
    List<Object[]> findTop5ByLikeWithJPQL();

    @Query(value = "select new GraduationWorkSalesProject.graduation.com.entity.product.Hashtag(h.id, h.hashtagName) from ProductHashtag p, Hashtag h where p.product.id = :productId and p.hashtag.id = h.id")
    List<Hashtag> findAllByProductId(@Param("productId") Long productId);
}
