package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
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

    private final MemberRepository memberRepository;

    private final HashtagRepository hashtagRepository;


    @Transactional
    public Long saveProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public void deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
    }

    @Transactional
    public Optional<Product> getProduct(Long product_id){
        return productRepository.findById(product_id);
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
            products.add(productRepository.findProductById(product_id).orElseThrow());
        }
        return products;
    }

    @Transactional
    public List<Product> getSearchResultProduct(String keyword) {
        return productRepository.findProductsByProductNameContaining(keyword);
    }

    @Transactional
    public void LikeProductAdd(Long member_id, Long product_id) {
        likeRepository.save(new Like(productRepository.getById(product_id),memberRepository.getById(member_id)));
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
        Like likeEntity = likeRepository.findByMemberIdAndProductId(member_id, product_id).orElseThrow();
        likeRepository.deleteById(likeEntity.getId());
    }

    @Transactional
    public Long getLikeNum(Long product_id) {
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
        return categoryRepository.findById(category_id).orElseThrow().getProducts();
    }

    @Transactional
    public void deleteCategory(Long category_id){
        categoryRepository.deleteById(category_id);
    }

    @Transactional
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    @Transactional
    public void saveHashtag(Hashtag hashtag){
        hashtagRepository.save(hashtag);
    }

    @Transactional
    public List<Product> getHashtagSearchResult(Long hashtag_id){
        return hashtagRepository.findHashtagById(hashtag_id).orElseThrow().getHashtagproducts();
    }

    @Transactional
    public Hashtag getHashtagByHashtagName(String hashtagName){
        return hashtagRepository.findHashtagByHashtagName(hashtagName).orElseThrow();
    }


}
