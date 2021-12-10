package GraduationWorkSalesProject.graduation.com.entity.product;

import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

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
    public ProductImage(MultipartFile image){
        this.image.setImageName(image.getOriginalFilename());
        this.image.setImageHref("https://firebasestorage.googleapis.com/v0/b/gradu-884f1.appspot.com/o/efd17390-d39d-4bb0-96d7-bc5ea054cc19.jpeg?alt=media&token=4db8ed2b-5141-4fb7-8f63-43bd2677fc79");
        this.image.setImageUuid(UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        this.image.setImageType(image.getContentType());
    }

    @Builder
    public ProductImage(Product product,MultipartFile image){
        this.image.setImageName(image.getOriginalFilename());
        this.image.setImageHref("https://firebasestorage.googleapis.com/v0/b/gradu-884f1.appspot.com/o/efd17390-d39d-4bb0-96d7-bc5ea054cc19.jpeg?alt=media&token=4db8ed2b-5141-4fb7-8f63-43bd2677fc79");
        this.image.setImageUuid(UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        this.image.setImageType(image.getContentType());
        this.product = product;
    }





}
