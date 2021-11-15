package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface LikeRepository extends JpaRepository<Like, Long>{

    List<Like> findAllByMember_Id(Long member_id);
    int countLikeByProductId(Long product_id);
    //int findLikeByProduct_Id(Long product_id);
    Like findByMemberIdAndProductId(Long member_id, Long product_id);
    @Query(value = "select count(l) as cnt, l.product.id from Like l group by l.product order by cnt desc")
    Map<Integer,Integer> findTopByLikeWithJPQL();


}


