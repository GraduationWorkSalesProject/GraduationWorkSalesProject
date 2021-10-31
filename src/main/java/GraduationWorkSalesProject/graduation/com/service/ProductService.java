package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.product.ProductRegisterRequest;
import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import GraduationWorkSalesProject.graduation.com.entity.product.CategoryProduct;
import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final LikeRepository likeRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryProductRepository categoryProductRepository;

    private final MemberRepository memberRepository;

    private final HashtagRepository hashtagRepository;

    private final ProductHashtagRepository productHashtagRepository;

    //상품 등록, 수정, 조회, 삭제
    @Transactional
    public Long saveProduct(ProductRegisterRequest productRegisterRequest) {

        Product product = productRegisterRequest.convert();
        productRepository.save(product);

        return product.getId();
    }

    @Transactional
    void deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
    }

    @Transactional
    public Optional<Product> getProduct(Long product_id){
        return productRepository.findById(product_id);
    }

    //어떤 순서로?
    @Transactional
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Transactional
    public List<Product> getAllProductByCategory(Long category_id) {
        return productRepository.findProductsByCategoriesId(category_id);
    }

    @Transactional
    public List<Product> getRecentProduct() {
        return productRepository.findTop10ByOrderByProductRegisterDateDesc();
    }

    @Transactional
    public List<Product> getBestProduct() {
        Map<Integer, Integer> map = likeRepository.findTopByLikeWithJPQL();
        List<Integer> keyList = new ArrayList<>(map.keySet());
        keyList.sort(Comparator.reverseOrder());
        List<Product> products = null;

        int limit = (keyList.size() < 10) ? keyList.size() : 10;

        for (int i = 0; i < limit; i++) {
            int num = keyList.get(i);
            Long product_id = Long.valueOf(map.get(num));
            products.add(productRepository.findProductById(product_id));
        }
        return products;
    }

    @Transactional
    public List<Product> getSearchResultProduct(String keyword) {
        return productRepository.findProductsByProductNameContaining(keyword);
    }

    @Transactional
    public void LikeProductAdd(Long member_id, Long product_id) {
        Like likeEntity = null;
        likeEntity.setMember(memberRepository.getById(member_id));
        likeEntity.setProduct(productRepository.getById(product_id));
        likeRepository.save(likeEntity);
    }

    @Transactional
    public List<Product> getMemberLikeProducts(Long member_id) {
        ArrayList<Like> likeList = new ArrayList<Like>();
        likeRepository.findAllByMember_Id(member_id);
        ArrayList<Product> resultList = new ArrayList<Product>();
        for (Like entity : likeList) {
            resultList.add(productRepository.getById(entity.getProduct().getId()));
        }
        return resultList;
    }

    @Transactional
    public void LikeProductUndo(Long member_id, Long product_id) {
        Like likeEntity = likeRepository.findByMemberIdAndProductId(member_id, product_id);
        likeRepository.deleteById(likeEntity.getId());
    }

    @Transactional
    public int getLikeNum(Long product_id) {
        return likeRepository.countLikeByProductId(product_id);
    }


    @Transactional
    public Optional<Category> findCategory(Long id){
        return categoryRepository.findById(id);
    }

    @Transactional
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @Transactional
    public List<Product> getCategoryProducts(Long category_id){
        return categoryProductRepository.findProductsByCategoryId(category_id);
    }

    @Transactional
    public void deleteCategory(Long category_id){
        categoryRepository.deleteById(category_id);
    }



    @Transactional
    public void saveCategoryProduct(CategoryProduct categoryProduct){
        categoryProductRepository.save(categoryProduct);
    }

    @Transactional
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }


    //검색시에 해시태그 + 상품명?
    //해시태그는 몇개까지? 지정해서 검색 가능?
    //해시태그는 상품 등록할때? 카테고리는 등록할 때 지정하는 것 알겠음
    //한 상품이 카테고리 2개에 속할 수도 있나...?


}
