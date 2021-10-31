package GraduationWorkSalesProject.graduation.com.entity.product;

import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Setter
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
            @AttributeOverride(name = "imageType", column = @Column(name = "product_image_type"))
    })
    private Image product_image;

}
