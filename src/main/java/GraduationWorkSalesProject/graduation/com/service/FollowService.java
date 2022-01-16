package GraduationWorkSalesProject.graduation.com.service;

import org.springframework.stereotype.Service;

import GraduationWorkSalesProject.graduation.com.entity.follow.Follow;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {

	private final FollowRepository followRepository;

	/**
	 * 팔로우 신청하기
	 * @param follower 팔로우 한 사람
	 * @param followed 팔로우 당한 사람
	 */
	public void follow(Member follower, Member followed) {
		Follow follow = Follow.builder()
				.followed(followed)
				.follower(follower)
				.build();
		followRepository.save(follow);
	}

}
