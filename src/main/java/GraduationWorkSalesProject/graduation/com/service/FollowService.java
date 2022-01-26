package GraduationWorkSalesProject.graduation.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import GraduationWorkSalesProject.graduation.com.dto.follow.FollowResponse;
import GraduationWorkSalesProject.graduation.com.entity.follow.Follow;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.NotFollowedException;
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
	 *
	 * @param follower 팔로우 한 사람
	 * @param followed 팔로우 당한 사람
	 */
	@Transactional
	public void follow(Member follower, Member followed) {
		final Follow follow = Follow.of(follower, followed);
		followRepository.save(follow);
	}

	/**
	 * follower가 followed를 팔로우 했는지 여부
	 *
	 * @param follower 팔로우 한 사람
	 * @param followed 팔로우 당한 사람
	 * @return boolean
	 */
	public boolean isFollowed(Member follower, Member followed) {
		final Optional<Follow> follow = followRepository.findByFollowerIdAndFollowedId(follower.getId(),
				followed.getId());
		return follow.isPresent();
	}

	/**
	 * 나를 팔로우 한 사람 목록 조회
	 *
	 * @param member
	 * @return List<FollowResponse>
	 */
	public List<FollowResponse> findFollowers(Member member) {
		final ArrayList<Follow> follows = followRepository.findByFollowedId(member.getId());
		return follows.stream().map(FollowResponse::new).collect(Collectors.toList());
	}

	/**
	 * 내가 팔로우 한 사람 목록 조회
	 *
	 * @param member
	 * @return List<FollowResponse>
	 */
	public List<FollowResponse> findFolloweds(Member member) {
		final ArrayList<Follow> follows = followRepository.findByFollowerId(member.getId());
		return follows.stream().map(FollowResponse::new).collect(Collectors.toList());
	}

	/**
	 * 팔로우 취소하기
	 *
	 * @param follower 팔로우 한 사람
	 * @param followed 팔로우 당한 사람
	 */
	@Transactional
	public void stopFollow(Member follower, Member followed) {
		//둘의 팔로우 여부 체크
		final Follow follow = followRepository.findByFollowerIdAndFollowedId(follower.getId(),
				followed.getId()).orElseThrow(NotFollowedException::new);
		followRepository.delete(follow);
	}

	/**
	 * 내가 팔로우 한 사람 수 조회
	 *
	 * @param member
	 * @return int
	 */
	public int countFolloweds(Member member) {
		return followRepository.countByFollowerId(member.getId());
	}

	/**
	 * 나를 팔로우 한 사람 수 조회
	 *
	 * @param member
	 * @return int
	 */
	public int countFollowers(Member member) {
		return followRepository.countByFollowedId(member.getId());
	}
}
