package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_hashtag",
            joinColumns = @JoinColumn(name = "hashtag_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> hashtagproducts = new ArrayList<Product>();

    public void addProduct(Product product){
        hashtagproducts.add(product);
        List<Hashtag> hashtagList = product.getHashtags();
        hashtagList.add(this);
        product.setHashtags(hashtagList);
    }

    @Builder
    public Hashtag(String hashtagName){
        this.hashtagName = hashtagName;
    }

}
