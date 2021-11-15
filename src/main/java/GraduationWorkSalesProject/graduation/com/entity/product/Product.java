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
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(length = 20, name = "product_name")
    private String name;

    @Column(name = "product_price")
    private int price;

    //length limit of product info?
    @Column(length = 1000, name = "product_information")
    private String information;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<Category>();

    @ManyToMany(mappedBy = "hashtagproducts", fetch = FetchType.LAZY)
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();

    @Column(name = "product_register_date")
    @CreatedDate
    private Timestamp registerDate;


    @Column(name = "product_update_date")
    @LastModifiedDate
    private Timestamp updateDate;

    @Column(name = "product_rating")
    private int rating;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageName", column = @Column(name = "product_representation_image_name")),
            @AttributeOverride(name = "imageUuid", column = @Column(name = "product_representation_image_uuid")),
            @AttributeOverride(name = "imageType", column = @Column(name = "product_representation_image_type")),
            @AttributeOverride(name = "imageHref", column = @Column(name = "product_representation_image_href"))
    })
    private Image representationImage;

    @Builder
    public Product(String name,int price,String information,int rating){
        this.name = name;
        this.price = price;
        this.information = information;
        this.rating = rating;
    }
}
