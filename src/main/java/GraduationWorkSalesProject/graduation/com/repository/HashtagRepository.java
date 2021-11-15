package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Hashtag findHashtagById(Long hashtag_id);
    Hashtag findHashtagByHashtagName(String hashtagName);
}