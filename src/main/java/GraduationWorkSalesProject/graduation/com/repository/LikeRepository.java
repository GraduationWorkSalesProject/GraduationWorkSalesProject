package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{

    List<Like> findAllByMember_Id(Long member_id);
    Long countLikeByProductId(Long product_id);
    Optional<Like> findByMemberIdAndProductId(Long member_id, Long product_id);
    @Query(value = "select count(l) as cnt, l.product.id from Like l group by l.product order by cnt desc")
    List<Object[]> findTop8ByLikeWithJPQL();


}


