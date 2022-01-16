package GraduationWorkSalesProject.graduation.com.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import GraduationWorkSalesProject.graduation.com.dto.follow.FollowResponse;
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
	 *
	 * @param follower 팔로우 한 사람
	 * @param followed 팔로우 당한 사람
	 */
	public void follow(Member follower, Member followed) {
		final Follow follow = Follow.builder().followed(followed).follower(follower).build();
		followRepository.save(follow);
	}

	public boolean isFollowed(Member follower, Member followed) {
		final Optional<Follow> follow = followRepository.findByFollowerIdAndFollowedId(follower.getId(),
				followed.getId());
		return follow.isPresent();
	}

	public ArrayList<FollowResponse> findFollowers(Member member) {
		// TODO Auto-generated method stub
		final ArrayList<Follow> follows = followRepository.findByFollowedId(member.getId());
		final ArrayList<FollowResponse> followResponses = new ArrayList<FollowResponse>();
		for (Follow follow : follows) {
			followResponses.add(new FollowResponse(follow.getFollowed().getUsername(), follow.getFollowed().getImage().getImageHref()));
		}
		return followResponses;
	}

	public ArrayList<FollowResponse> findFolloweds(Member member) {
		// TODO Auto-generated method stub
		final ArrayList<Follow> follows = followRepository.findByFollowerId(member.getId());
		final ArrayList<FollowResponse> followResponses = new ArrayList<FollowResponse>();
		for (Follow follow : follows) {
			followResponses.add(new FollowResponse(follow.getFollowed().getUsername(), follow.getFollowed().getImage().getImageHref()));
		}
		return followResponses;
	}

	public void stopFollow(Member follower, Member followed) {
		//둘의 팔로우 여부 체크
		final Follow follow = followRepository.findByFollowerIdAndFollowedId(follower.getId(),
				followed.getId()).get();
		followRepository.delete(follow);
	}

}
