package GraduationWorkSalesProject.graduation.com.repository;

import GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse( p.id, c.category.id, c.category.categoryName, p.name, p.price, p.information, p.registerDate, p.updateDate, 0, p.representationImage, s.sellerName) from Product p, CategoryProduct c, Seller s where p.id = c.product.id and s.member.id = p.member.id order by p.registerDate desc")
    List<ProductResponse> findTop8ByOrderByRegisterDateDescWithJPQL();

    @Query("select new GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse( p.id, c.category.id, c.category.categoryName, p.name, p.price, p.information, p.registerDate, p.updateDate, 0, p.representationImage, s.sellerName) from Product p, CategoryProduct c, Seller s where p.id = c.product.id and s.member.id = p.member.id and p.name like %:keyword%")
    Page<ProductResponse> findAllByNameContainingWithJPQL(Pageable pageable, @Param("keyword") String keyword);

    @Query("select new GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse( p.id, c.category.id, c.category.categoryName, p.name, p.price, p.information, p.registerDate, p.updateDate, 0, p.representationImage, s.sellerName) from Product p, CategoryProduct c, Seller s where p.id = c.product.id and c.category.id = :categoryId and s.member.id = p.member.id")
    Page<ProductResponse> findAllWithJPQL(Pageable pageable, @Param("categoryId") Long categoryId);

    @Query("select p.representationImage from Product p, ProductHashtag h where h.hashtag.id = :hashtagId order by p.view desc ")
    List<Image> findProductImageJPQL(@Param("hashtagId") Long hashtagId);

    @Query("select new GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse( p.id, c.category.id, c.category.categoryName, p.name, p.price, p.information, p.registerDate, p.updateDate, 0, p.representationImage, s.sellerName) from Product p, CategoryProduct c, Seller s where p.id = c.product.id and s.member.id = p.member.id")
    Page<ProductResponse> findAllWithJPQL(Pageable pageable);

    @Query("select new GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse( p.id, c.category.id, c.category.categoryName, p.name, p.price, p.information, p.registerDate, p.updateDate, 0, p.representationImage, s.sellerName) from Product p, CategoryProduct c, Seller s where p.id = c.product.id and s.member.id = p.member.id order by p.view desc")
    List<ProductResponse> findTop8ByOrderByViewDescWithJPQL();
}
