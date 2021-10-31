package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "category")
    private List<CategoryProduct> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<Category>();

    public void addCategoryProduct(CategoryProduct categoryProduct){
        products.add(categoryProduct);
        categoryProduct.setCategory(this);
    }

    public void addCategoryChild(Category category){
        child.add(category);
        category.setCategoryParent(this);
    }

    public void setCategoryParent(Category category){
        this.parent = category;
        category.addCategoryChild(this);
    }

}
