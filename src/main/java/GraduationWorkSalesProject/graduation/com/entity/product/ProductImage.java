package GraduationWorkSalesProject.graduation.com.entity.product;

import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageName", column = @Column(name = "product_image_name")),
            @AttributeOverride(name = "imageUuid", column = @Column(name = "product_image_uuid")),
            @AttributeOverride(name = "imageType", column = @Column(name = "product_image_type")),
            @AttributeOverride(name = "imageHref", column = @Column(name = "product_image_href"))
    })
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductImage(Product product, Image image){
        this.product = product;
        this.image = image;
    }

}
