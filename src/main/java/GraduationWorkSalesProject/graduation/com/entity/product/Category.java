package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "product_categories")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "product_category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_product",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<Product>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER)
    private List<Category> child = new ArrayList<Category>();

    public void addProduct(Product product){
        products.add(product);
        List<Category> categoryList = product.getCategories();
        categoryList.add(this);
        product.setCategories(categoryList);
    }

    public void addCategoryChild(Category category){
        child.add(category);
        category.setCategoryParent(this);
    }

    public void setCategoryParent(Category category){
        this.parent = category;
        category.addCategoryChild(this);
    }

    @Builder
    public Category(String categoryName, Category parent){
        this.categoryName = categoryName;
        this.parent = parent;
    }


}
