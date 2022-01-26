package GraduationWorkSalesProject.graduation.com.dto.follow;

import GraduationWorkSalesProject.graduation.com.entity.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FollowResponse {
	private String username;
	private String imageUrl;

	public FollowResponse(Follow follow) {
		this.username = follow.getFollowed().getUsername();
		this.imageUrl = follow.getFollowed().getImage().getImageHref();
	}


}
