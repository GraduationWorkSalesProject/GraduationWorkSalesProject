package GraduationWorkSalesProject.graduation.com.dto.product;

import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
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
    private Long categoryId;
    private String categoryName;
    private int productPrice;
    private String productInformation;
    private Timestamp productRegisterDate;
    private Timestamp productUpdateDate;
    private int productRating;
    private int productDeliveryTerm;
    private int productDeliveryPrice;
    private Seller seller;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getName();
        this.productPrice = product.getPrice();
        this.productInformation = product.getInformation();
        this.productRegisterDate = product.getRegisterDate();
        this.productUpdateDate = product.getUpdateDate();
        this.productRating = product.getRating();
        this.productDeliveryTerm = product.getTerm();
        this.productDeliveryPrice = product.getDeliveryPrice();

    }

}
