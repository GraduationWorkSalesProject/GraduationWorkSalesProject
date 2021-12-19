package GraduationWorkSalesProject.graduation.com.entity.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "product_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_parent_id")
    private Category parent;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<CategoryProduct> categoryProducts;

    @Builder
    public Category(String categoryName, Category parent){
        this.categoryName = categoryName;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            Category category = (Category) obj;
            if (this.id.equals(category.id)) {
                return true;
            }
        }return false;
    }

    @Override
    public int hashCode(){
        return this.hashCode();
    }


}
