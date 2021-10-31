package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "hashtags")
public class Hashtag {
    @Id
    @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name")
    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private List<ProductHashtag> products = new ArrayList<>();

    public void addHashtagProduct(ProductHashtag productHashtag){
        products.add(productHashtag);
        productHashtag.setHashtag(this);
    }
}
