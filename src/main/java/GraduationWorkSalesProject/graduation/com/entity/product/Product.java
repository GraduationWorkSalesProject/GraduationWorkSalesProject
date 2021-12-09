package GraduationWorkSalesProject.graduation.com.entity.product;


import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(length = 20, name = "product_name")
    private String name;

    @Column(name = "product_price")
    private int price;

    //length limit of product info?
    @Column(length = 1000, name = "product_information")
    private String information;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductHashtag> productHashtags;

    @Column(name = "product_register_date")
    @CreatedDate
    private Timestamp registerDate;


    @Column(name = "product_update_date")
    @LastModifiedDate
    private Timestamp updateDate;

    @Column(name = "product_rating")
    private int rating;

    @Column(name = "product_delivery_term")
    private int term;

    @Column(name = "product_delivery_price")
    private int deliveryPrice;

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
