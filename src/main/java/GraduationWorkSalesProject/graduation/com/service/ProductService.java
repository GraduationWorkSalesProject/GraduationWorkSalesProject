package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.product.*;
import GraduationWorkSalesProject.graduation.com.entity.product.*;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.exception.*;
import GraduationWorkSalesProject.graduation.com.repository.*;
import GraduationWorkSalesProject.graduation.com.service.file.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private final SellerRepository sellerRepository;

    private final FileUploadService fileUploadService;

    private final ProductImageRepository productImageRepository;

    @Transactional
    public void saveProduct(ProductRegisterRequest productRegisterRequest) throws IOException {

        if (!productRegisterRequest.getMember().getRole().toString().equals("ROLE_SELLER"))
            throw new ProductRegisterCertificationException();

        Product registerProduct = productRegisterRequest.convert(fileUploadService.saveTest(productRegisterRequest.getProductRepImage()));

        List<String> hashtags = productRegisterRequest.getHashtags();

        productRepository.save(registerProduct);

        if (productRegisterRequest.getProductImage1() != null && !productRegisterRequest.getProductImage1().isEmpty())
            productImageRepository.save(
                    ProductImage.builder().product(registerProduct)
                            .image(fileUploadService.saveTest(productRegisterRequest.getProductImage1())).build());

        if (productRegisterRequest.getProductImage2() != null && !productRegisterRequest.getProductImage2().isEmpty())
            productImageRepository.save(
                    ProductImage.builder().product(registerProduct)
                            .image(fileUploadService.saveTest(productRegisterRequest.getProductImage2())).build());

        if (productRegisterRequest.getProductImage3() != null && !productRegisterRequest.getProductImage3().isEmpty())
            productImageRepository.save(
                    ProductImage.builder().product(registerProduct)
                            .image(fileUploadService.saveTest(productRegisterRequest.getProductImage3())).build());

        if (productRegisterRequest.getProductImage4() != null && !productRegisterRequest.getProductImage4().isEmpty())
            productImageRepository.save(
                    ProductImage.builder().product(registerProduct)
                            .image(fileUploadService.saveTest(productRegisterRequest.getProductImage4())).build());

        Optional<Category> category = categoryRepository.findById(productRegisterRequest.getCategoryId());
        CategoryProduct categoryProduct = new CategoryProduct(registerProduct, category.orElseThrow(CategoryNotExistException::new));
        categoryProductRepository.save(categoryProduct);

        if (hashtags != null && !hashtags.isEmpty()) {
            for (String hashtag : hashtags) {
                Optional<Hashtag> findHashtag = hashtagRepository.findHashtagByHashtagName(hashtag);
                if (findHashtag.isEmpty()) {
                    HashtagRegisterRequest hashtagRegisterRequest = new HashtagRegisterRequest();
                    hashtagRegisterRequest.setHashtagName(hashtag);
                    Hashtag saveHashtag = hashtagRegisterRequest.covert();
                    hashtagRepository.save(saveHashtag);
                    ProductHashtag productHashtag = new ProductHashtag(registerProduct, saveHashtag);
                    productHashtagRepository.save(productHashtag);

                } else {
                    Hashtag registerHashtag = hashtagRepository.findHashtagByHashtagName(hashtag).orElseThrow(HashtagNotExistException::new);
                    ProductHashtag productHashtag = new ProductHashtag(registerProduct, registerHashtag);
                    productHashtagRepository.save(productHashtag);
                }
            }
        }

    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public ProductDetailResponse getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<Seller> seller = sellerRepository.findByMemberId(product.orElseThrow(ProductNotExistException::new).getMember().getId());
        ProductDetailResponse response = new ProductDetailResponse(product.orElseThrow(ProductNotExistException::new), seller.orElseThrow(SellerNotFoundException::new));
        Long categoryId = getCategoryIdByProduct(product.orElseThrow(ProductNotExistException::new).getId());
        response.setCategoryId(categoryId);
        response.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
        return response;
    }


    public List<ProductResponse> getRecentProduct() {
        List<Product> products = productRepository.findTop8ByOrderByRegisterDateDesc();
        List<ProductResponse> response = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            ProductResponse productResponse = new ProductResponse(products.get(i));
            Long categoryId = getCategoryIdByProduct(products.get(i).getId());
            productResponse.setCategoryId(categoryId);
            productResponse.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    public List<ProductResponse> getBestProduct() {
        List<Object[]> resultList = likeRepository.findTop8ByLikeWithJPQL();

        List<ProductResponse> response = new ArrayList<>();

        for (Object[] row : resultList) {
            Long productId = (Long) row[1];
            Product product = productRepository.findById(productId).orElseThrow(ProductNotExistException::new);
            ProductResponse productResponse = new ProductResponse(product);
            Long category_id = getCategoryIdByProduct(product.getId());
            productResponse.setCategoryId(category_id);
            productResponse.setCategoryName(categoryRepository.findById(category_id).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    public List<ProductResponse> getSearchResultProduct(String keyword) {
        List<Product> products = productRepository.findAllByNameContaining(keyword);
        List<ProductResponse> response = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse(product);
            Long categoryId = getCategoryIdByProduct(product.getId());
            productResponse.setCategoryId(categoryId);
            productResponse.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    @Transactional
    public void likeProductAdd(Long memberId, Long productId) {
        likeRepository.save(new Like(productRepository.getById(productId), memberRepository.getById(memberId)));
    }

    public List<ProductResponse> getMemberLikeProducts(Long memberId) {
        List<ProductResponse> response = new ArrayList<>();

        List<Like> likeList = likeRepository.findAllByMemberId(memberId);

        for (Like entity : likeList) {
            Optional<Product> product = productRepository.findById(entity.getProduct().getId());
            ProductResponse productResponse = new ProductResponse(product.orElseThrow(ProductNotExistException::new));
            Long categoryId = getCategoryIdByProduct(product.orElseThrow(ProductNotExistException::new).getId());
            productResponse.setCategoryId(categoryId);
            productResponse.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    @Transactional
    public void likeProductUndo(Long member_id, Long product_id) {
        Like likeEntity = likeRepository.findByMemberIdAndProductId(member_id, product_id).orElseThrow(LikeNotExistException::new);
        likeRepository.deleteById(likeEntity.getId());
    }

    public Long getLikeNum(Long product_id) {
        return likeRepository.countLikeByProductId(product_id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<ProductResponse> getCategoryProducts(Long categoryId) {
        List<CategoryProduct> categoryProductsList = categoryProductRepository.findAllByCategoryId(categoryId);
        List<ProductResponse> response = new ArrayList<>();

        //if list is empty
        //need exception?

        //if list is not empty
        for (CategoryProduct categoryProduct : categoryProductsList) {
            ProductResponse productResponse = new ProductResponse(categoryProduct.getProduct());
            productResponse.setCategoryId(categoryId);
            productResponse.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public void saveCategory(CategoryRegisterRequest categoryRegisterRequest) {
        Category parentCategory = categoryRepository.getOne(categoryRegisterRequest.getCategoryParentId());
        Category newCategory = Category.builder().categoryName(categoryRegisterRequest.getCategoryName()).parent(parentCategory).build();
        categoryRepository.save(newCategory);

    }


    public List<ProductResponse> getHashtagSearchResult(Long hashtagId) {
        List<ProductHashtag> productHashtagList = productHashtagRepository.findAllByHashtagId(hashtagId);
        List<ProductResponse> response = new ArrayList<>();
        for (ProductHashtag productHashtag : productHashtagList) {
            ProductResponse productResponse = new ProductResponse(productHashtag.getProduct());
            Long categoryId = getCategoryIdByProduct(productHashtag.getProduct().getId());
            productResponse.setCategoryId(categoryId);
            productResponse.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
            response.add(productResponse);
        }
        return response;
    }

    public Long getCategoryIdByProduct(Long productId) {
        List<CategoryProduct> categoryProductList = categoryProductRepository.findAllByProductId(productId);
        Long categoryId = categoryProductList.get(categoryProductList.size() - 1).getCategory().getId();
        return categoryId;
    }


}
