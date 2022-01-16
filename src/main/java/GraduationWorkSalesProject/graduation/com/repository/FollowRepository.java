package GraduationWorkSalesProject.graduation.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import GraduationWorkSalesProject.graduation.com.entity.follow.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFollowerId(Long followerId);
	Optional<Follow> findByFollowedId(Long followedId);
}
