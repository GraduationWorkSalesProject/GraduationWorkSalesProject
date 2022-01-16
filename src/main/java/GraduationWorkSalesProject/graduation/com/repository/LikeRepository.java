package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{

    List<Like> findAllByMemberId(Long memberId);
    Long countLikeByProductId(Long productId);
    Optional<Like> findByMemberIdAndProductId(Long memberId, Long productId);


}


