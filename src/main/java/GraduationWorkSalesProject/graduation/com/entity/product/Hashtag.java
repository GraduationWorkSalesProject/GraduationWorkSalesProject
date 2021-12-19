package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name")
    private String hashtagName;

    /*
    public void addProduct(Product product){
        hashtagproducts.add(product);
        List<Hashtag> hashtagList = product.getHashtags();
        hashtagList.add(this);
        //product.setHashtags(hashtagList);
    }
*/
    @Builder
    public Hashtag(String hashtagName){
        this.hashtagName = hashtagName;
    }

}
