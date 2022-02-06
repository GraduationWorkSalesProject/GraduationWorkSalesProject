package GraduationWorkSalesProject.graduation.com.dto.follow;

import GraduationWorkSalesProject.graduation.com.entity.follow.Follow;
import lombok.Getter;


@Getter
public class FollowResponse {
	private String username;
	private String imageUrl;

	public FollowResponse(Follow follow) {
		this.username = follow.getFollowed().getUsername();
		this.imageUrl = follow.getFollowed().getImage().getImageHref();
	}


}
