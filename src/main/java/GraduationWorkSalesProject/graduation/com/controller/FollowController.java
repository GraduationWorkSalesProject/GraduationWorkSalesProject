package GraduationWorkSalesProject.graduation.com.controller;

import static GraduationWorkSalesProject.graduation.com.dto.result.ResultCode.FIND_USERID_SUCCESS;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.exception.InvalidCertificateException;
import GraduationWorkSalesProject.graduation.com.service.FollowService;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "팔로우 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class FollowController {

	private final MemberService memberService;
	private final FollowService followService;

	@ApiOperation(value = "팔로우 신청")
    @PostMapping(value = "/follows/{followedUserName}")
    public ResponseEntity<ResultResponse> follow(@PathVariable String followedUserName) {
		final String followerUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member follower = memberService.findOneByUsername(followerUserName).orElseThrow(InvalidCertificateException::new);
        final Member followed = memberService.findOneByUserid(followedUserName).orElseThrow(InvalidCertificateException::new);
		log.info("팔로우를 당한 userName" + followedUserName.toString());
		log.info("팔로우를 한 userName" + followerUserName.toString());

		followService.follow(follower, followed);
        return ResponseEntity.ok(ResultResponse.of(FIND_USERID_SUCCESS, null));
    }
	//내가 팔로우 한 사람 목록 조회
	//나를 팔로우 한 사람 목록 조회
	//팔로우 수 조회
	//해당 회원을 팔로우 했는지 여부
}
