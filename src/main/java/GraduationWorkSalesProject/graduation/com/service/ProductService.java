package GraduationWorkSalesProject.graduation.com.service;

import GraduationWorkSalesProject.graduation.com.dto.product.*;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.product.*;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.exception.*;
import GraduationWorkSalesProject.graduation.com.repository.*;
import GraduationWorkSalesProject.graduation.com.service.file.FileUploadService;
import GraduationWorkSalesProject.graduation.com.vo.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public void saveProduct(ProductRegisterRequest productRegisterRequest, Member member) throws IOException {

        if (!member.getRole().toString().equals("ROLE_SELLER"))
            throw new ProductRegisterCertificationException();

        Product registerProduct = productRegisterRequest.convert(fileUploadService.saveTest(productRegisterRequest.getProductRepImage()), member);

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
        List<Image> imageList = new ArrayList<>();
        for (ProductImage productImage : productImageRepository.findByProductId(productId)){
            imageList.add(productImage.getImage());
        }
        ProductDetailResponse response = new ProductDetailResponse(product.orElseThrow(ProductNotExistException::new), seller.orElseThrow(SellerNotFoundException::new), imageList, productHashtagRepository.findAllByProductId(productId));
        Long categoryId = getCategoryIdByProduct(product.orElseThrow(ProductNotExistException::new).getId());
        response.setCategoryId(categoryId);
        response.setCategoryName(categoryRepository.findById(categoryId).orElseThrow(CategoryNotExistException::new).getCategoryName());
        return response;
    }


    public List<ProductResponse> getRecentProduct() {
        List<ProductResponse> response = productRepository.findTop8ByOrderByRegisterDateDescWithJPQL();

        for (ProductResponse productResponse : response) {
            productResponse.setHashtagList(productHashtagRepository.findAllByProductId(productResponse.getId()));
        }
        return response;
    }

    public List<ProductResponse> getBestProduct() {
        List<ProductResponse> response = productRepository.findTop8ByOrderByViewDescWithJPQL();

        for (ProductResponse productResponse : response) {
            productResponse.setHashtagList(productHashtagRepository.findAllByProductId(productResponse.getId()));
        }

        return response;
    }

    public Page<ProductResponse> getSearchResultProduct(SearchProductPageRequest searchProductPageRequest) {
        PageRequest pageRequest = PageRequest.of(searchProductPageRequest.getPage(), 8, Sort.Direction.DESC, "registerDate");
        Page<ProductResponse> products = productRepository.findAllByNameContainingWithJPQL(pageRequest, searchProductPageRequest.getKeyword());

        return products;
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

    public Page<ProductResponse> getCategoryProducts(CategoryProductPageRequest categoryProductPageRequest) {

        PageRequest pageRequest = null;

        if (categoryProductPageRequest.getSort() == "최신순")
            pageRequest = PageRequest.of(categoryProductPageRequest.getPage(), 8, Sort.Direction.DESC, "registerDate");

        if (categoryProductPageRequest.getSort() == "인기순")
            pageRequest = PageRequest.of(categoryProductPageRequest.getPage(), 8, Sort.Direction.DESC, "view");

        Page<ProductResponse> productResponsePage = productRepository.findAllWithJPQL(pageRequest, categoryProductPageRequest.getCategoryId());
        for (ProductResponse productResponse : productResponsePage.getContent()) {
            productResponse.setHashtagList(productHashtagRepository.findAllByProductId(productResponse.getId()));
        }

        return productResponsePage;
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

    public List<HashtagProductResponse> getHashtagProducts() {
        List<Object[]> hashtags = productHashtagRepository.findTop5ByLikeWithJPQL();

        List<HashtagProductResponse> response = new ArrayList<>();

        for (Object[] row : hashtags) {
            HashtagProductResponse hashtagProductResponse = new HashtagProductResponse((Long) row[1], row[2].toString(), productRepository.findProductImageJPQL((Long) row[1]).get(1));
            System.out.println(productRepository.findProductImageJPQL((Long) row[1]));
            response.add(hashtagProductResponse);
        }

        return response;
    }

    public Page<ProductResponse> getTotalRecentProducts(int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 8, Sort.Direction.DESC, "registerDate");
        if (productRepository.findAllWithJPQL(pageRequest).getTotalPages() <= pageNum)
            throw new PageNumberInvalidException();
        Page<ProductResponse> productResponsePage = productRepository.findAllWithJPQL(pageRequest);
        for (ProductResponse productResponse : productResponsePage.getContent()) {
            productResponse.setHashtagList(productHashtagRepository.findAllByProductId(productResponse.getId()));
        }
        return productResponsePage;
    }

    public Page<ProductResponse> getTotalPopularProducts(int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 8, Sort.Direction.DESC, "view");
        if (productRepository.findAllWithJPQL(pageRequest).getTotalPages() <= pageNum)
            throw new PageNumberInvalidException();
        Page<ProductResponse> productResponsePage = productRepository.findAllWithJPQL(pageRequest);
        for (ProductResponse productResponse : productResponsePage.getContent()) {
            productResponse.setHashtagList(productHashtagRepository.findAllByProductId(productResponse.getId()));
        }
        return productResponsePage;
    }
}
