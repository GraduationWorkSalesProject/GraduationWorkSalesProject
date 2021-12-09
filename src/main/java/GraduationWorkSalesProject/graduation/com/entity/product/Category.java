package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "product_categories")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "product_category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_parent_id")
    private Category parent;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<CategoryProduct> categoryProducts;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    private List<Category> child = new ArrayList<Category>();

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
