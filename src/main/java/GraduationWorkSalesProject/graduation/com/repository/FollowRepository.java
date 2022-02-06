package GraduationWorkSalesProject.graduation.com.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import GraduationWorkSalesProject.graduation.com.entity.follow.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	ArrayList<Follow> findByFollowerId(Long followerId);
	ArrayList<Follow> findByFollowedId(Long followedId);
	Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);
	int countByFollowerId(Long followerId);
	int countByFollowedId(Long followedId);
}
