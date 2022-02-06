package GraduationWorkSalesProject.graduation.com.entity.follow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "follows")
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follower_id")
    private Member follower; //팔로우를 한 사람

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="followed_id")
    private Member followed; // 팔로우를 당한 사람

    @Builder
    public Follow(Member follower, Member followed) {
    	this.followed = followed;
    	this.follower = follower;
    }

    public static Follow of(Member follower, Member followed) {
        return Follow.builder()
                .follower(follower)
                .followed(followed)
                .build();
    }

}
