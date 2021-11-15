package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String productName;
    private Long category_id;
    private String categoryName;
    private int productPrice;
    private String productInformation;
    private Timestamp productRegisterDate;
    private Timestamp productUpdateDate;
    private int productRating;
    /*
    private String image_href;
    private ImageLinkResponse image;
    */
    public ProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.category_id = product.getCategories().get(0).getId();
        this.categoryName = product.getCategories().get(0).getCategoryName();
        this.productPrice = product.getProductPrice();
        this.productInformation = product.getProductInformation();
        this.productRegisterDate = product.getProductRegisterDate();
        this.productUpdateDate = product.getProductUpdateDate();
        this.productRating = product.getProductRating();
        /*
        this.image_href = product.getProductRepresentationImage().getImageHref();
        this.image.setImageHref(product.getProductRepresentationImage().getImageHref());
        this.image.setImageName(product.getProductRepresentationImage().getImageName());
        this.image.setImageType(product.getProductRepresentationImage().getImageType());
        this.image.setImageUuid(product.getProductRepresentationImage().getImageUuid());

        */
    }


}
