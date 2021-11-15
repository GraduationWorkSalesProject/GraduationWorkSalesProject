package GraduationWorkSalesProject.graduation.com.entity.product;


import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(length = 20, name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    //length limit of product info?
    @Column(length = 1000, name = "product_information")
    private String productInformation;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private List<Category> categories = new ArrayList<Category>();

    @ManyToMany(mappedBy = "hashtagproducts", fetch = FetchType.LAZY)
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();

    @Column(name = "product_register_date")
    @CreatedDate
    private Timestamp productRegisterDate;


    @Column(name = "product_update_date")
    @LastModifiedDate
    private Timestamp productUpdateDate;

    @Column(name = "product_rating")
    private int productRating;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageName", column = @Column(name = "product_representation_image_name")),
            @AttributeOverride(name = "imageUuid", column = @Column(name = "product_representation_image_uuid")),
            @AttributeOverride(name = "imageType", column = @Column(name = "product_representation_image_type")),
            @AttributeOverride(name = "imageHref", column = @Column(name = "product_representation_image_href"))
    })
    private Image productRepresentationImage;

    @Builder
    public Product(String productName,int productPrice,String productInformation,int productRating){
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInformation = productInformation;
        this.productRating = productRating;
    }
}
