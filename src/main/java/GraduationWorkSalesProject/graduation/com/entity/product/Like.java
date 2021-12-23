package GraduationWorkSalesProject.graduation.com.entity.product;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Like(Product product, Member member){
        this.product = product;
        this.member = member;
    }

}
