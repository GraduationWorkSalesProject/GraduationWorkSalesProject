package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{

    List<Like> findAllByMemberId(Long memberId);
    Long countLikeByProductId(Long productId);
    Optional<Like> findByMemberIdAndProductId(Long memberId, Long productId);
    @Query(value = "select count(l) as cnt, l.product.id from Like l group by l.product order by cnt desc")
    List<Object[]> findTop8ByLikeWithJPQL();

}


