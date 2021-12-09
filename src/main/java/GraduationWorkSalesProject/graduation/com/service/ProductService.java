package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.product.CategoryRegisterRequest;
import GraduationWorkSalesProject.graduation.com.dto.product.HashtagRegisterRequest;
import GraduationWorkSalesProject.graduation.com.dto.product.ProductRegisterRequest;
import GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse;
import GraduationWorkSalesProject.graduation.com.entity.product.*;
import GraduationWorkSalesProject.graduation.com.exception.CategoryNotExistException;
import GraduationWorkSalesProject.graduation.com.exception.HashtagNotExistException;
import GraduationWorkSalesProject.graduation.com.exception.LikeNotExistException;
import GraduationWorkSalesProject.graduation.com.exception.ProductNotExistException;
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

    private final CategoryProductRepository categoryProductRepository;

    private final ProductHashtagRepository productHashtagRepository;


    //save , delete product

    @Transactional
    public void saveProduct(ProductRegisterRequest productRegisterRequest) {
        Product registerProduct = productRegisterRequest.convert();
        List<String> hashtags = productRegisterRequest.getHashtags();


        //if hashtag exist => add to product
        //if hashtag not exist => create hashtag and add to product
        productRepository.save(registerProduct);
        Optional<Category> category = categoryRepository.findById(productRegisterRequest.getCategoryId());
        CategoryProduct categoryProduct = new CategoryProduct(registerProduct,category.orElseThrow(CategoryNotExistException::new));
        categoryProductRepository.save(categoryProduct);

        for(String hashtag: hashtags){
            Optional<Hashtag> findHashtag = hashtagRepository.findHashtagByHashtagName(hashtag);
            if(findHashtag.isEmpty()){
                HashtagRegisterRequest hashtagRegisterRequest = new HashtagRegisterRequest();
                hashtagRegisterRequest.setHashtagName(hashtag);
                Hashtag saveHashtag = hashtagRegisterRequest.covert();
                hashtagRepository.save(saveHashtag);
                ProductHashtag productHashtag = new ProductHashtag(registerProduct,saveHashtag);
                productHashtagRepository.save(productHashtag);

            }else{
                Hashtag registerHashtag = hashtagRepository.findHashtagByHashtagName(hashtag).orElseThrow(HashtagNotExistException::new);
                ProductHashtag productHashtag = new ProductHashtag(registerProduct,registerHashtag);
                productHashtagRepository.save(productHashtag);
            }
        }

    }

    @Transactional
    public void deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
    }

    public ProductResponse getProduct(Long product_id){
        Optional<Product> product = productRepository.findById(product_id);
        ProductResponse response = new ProductResponse(product.orElseThrow(ProductNotExistException::new));
        return response;
    }



    public List<ProductResponse> getRecentProduct() {
        List<Product> products = productRepository.findTop8ByOrderByRegisterDateDesc();
        List<ProductResponse> response = new ArrayList<>();
        for(int i = 0 ; i < products.size() ; i++){
            ProductResponse productResponse = new ProductResponse(products.get(i));
            Long category_id = getCategoryIdByProduct(products.get(i).getId());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    public List<ProductResponse> getBestProduct() {
        List<Object[]> resultList = likeRepository.findTop8ByLikeWithJPQL();

        List<ProductResponse> response = new ArrayList<>();

        for (Object[] row:resultList) {
            Long product_id = (Long)row[1];
            Product product = productRepository.findById(product_id).orElseThrow(ProductNotExistException::new);
            ProductResponse productResponse = new ProductResponse(product);
            Long category_id = getCategoryIdByProduct(product.getId());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    public List<ProductResponse> getSearchResultProduct(String keyword) {
        List<Product> products = productRepository.findProductsByNameContaining(keyword);
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            ProductResponse productResponse = new ProductResponse(product);
            Long category_id = getCategoryIdByProduct(product.getId());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    @Transactional
    public void LikeProductAdd(Long member_id, Long product_id) {
        likeRepository.save(new Like(productRepository.getById(product_id),memberRepository.getById(member_id)));
    }

    public List<ProductResponse> getMemberLikeProducts(Long member_id) {
        List<ProductResponse> response = new ArrayList<>();

        List<Like> likeList = likeRepository.findAllByMember_Id(member_id);

        for (Like entity : likeList) {
            Optional<Product> product = productRepository.findById(entity.getProduct().getId());
            ProductResponse productResponse = new ProductResponse(product.orElseThrow(ProductNotExistException::new));
            Long category_id = getCategoryIdByProduct(product.orElseThrow(ProductNotExistException::new).getId());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    @Transactional
    public void LikeProductUndo(Long member_id, Long product_id) {
        Like likeEntity = likeRepository.findByMemberIdAndProductId(member_id, product_id).orElseThrow(LikeNotExistException::new);
        likeRepository.deleteById(likeEntity.getId());
    }

    public Long getLikeNum(Long product_id) {
        return likeRepository.countLikeByProductId(product_id);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public List<ProductResponse> getCategoryProducts(Long category_id){
        List<CategoryProduct> categoryProductsList = categoryProductRepository.findByCategory_Id(category_id);
        List<ProductResponse> response = new ArrayList<>();

        //if list is empty
        //need exception?

        //if list is not empty
        for(CategoryProduct categoryProduct: categoryProductsList){
            ProductResponse productResponse = new ProductResponse(categoryProduct.getProduct());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    @Transactional
    public void deleteCategory(Long category_id){
        categoryRepository.deleteById(category_id);
    }

    @Transactional
    public void saveCategory(CategoryRegisterRequest categoryRegisterRequest){
        Category child_category = categoryRegisterRequest.convert();

        Category parent_category = categoryRepository.getOne(categoryRegisterRequest.getCategoryParentId());

        child_category.setCategoryParent(parent_category);
        categoryRepository.save(child_category);

        parent_category.addCategoryChild(child_category);
        categoryRepository.save(parent_category);
    }


    public List<ProductResponse> getHashtagSearchResult(Long hashtag_id){
        List<ProductHashtag> productHashtagList = productHashtagRepository.findByHashtag_Id(hashtag_id);
        List<ProductResponse> response = new ArrayList<>();
        for(ProductHashtag productHashtag: productHashtagList){
            ProductResponse productResponse = new ProductResponse(productHashtag.getProduct());
            Long category_id = getCategoryIdByProduct(productHashtag.getProduct().getId());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    public Long getCategoryIdByProduct(Long product_id){
        List<CategoryProduct> categoryProductList = categoryProductRepository.findByProduct_Id(product_id);
        Long category_id = categoryProductList.get(categoryProductList.size()-1).getCategory().getId();
        return category_id;
    }



}
