package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.dto.product.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import GraduationWorkSalesProject.graduation.com.exception.InvalidCertificateException;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import GraduationWorkSalesProject.graduation.com.service.ProductService;
import GraduationWorkSalesProject.graduation.com.service.file.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Api(tags = "상품 API")
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final MemberService memberService;
    private final ProductService productService;
    private final FileUploadService uploadService;

    //ok
    @ApiOperation(value = "상품 등록하기")
    @PostMapping(value = "/products", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> register(
            @RequestParam(value = "productRepImage", required = false) @NotNull(message = "상품 사진은 적어도 한 개 이상 존재해야 합니다") MultipartFile productRepImage,
            @RequestParam(value = "productImage1", required = false) MultipartFile productImage1,
            @RequestParam(value = "productImage2", required = false) MultipartFile productImage2,
            @RequestParam(value = "productImage3", required = false) MultipartFile productImage3,
            @RequestParam(value = "productImage4", required = false) MultipartFile productImage4,
            @RequestParam(value = "productName", required = false) @NotBlank(message = "상품명은 필수입니다") String productName,
            @RequestParam(value = "productPrice", required = false) @Range(min = 1, message = "상품 가격은 1 이상이어야 합니다") int productPrice,
            @RequestParam(value = "productInformation", required = false) @NotBlank(message = "상품 정보는 필수입니다") String productInformation,
            @RequestParam(value = "categoryId", required = false) @Range(min = 1, message = "카테고리 번호는 유효한 값이어야 합니다") Long categoryId,
            @RequestParam(value = "hashtags", required = false) List<String> hashtags,
            @RequestParam(value = "productDeliveryTerm", required = false, defaultValue = "0") @Range(min = 0, message = "유효하지 않은 배송기간입니다") int productDeliveryTerm,
            @RequestParam(value = "productDeliveryPrice", required = false, defaultValue = "0") @Range(min = 0, message = "유효하지 않은 배송비입니다") int productDeliveryPrice
    ) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Member findMember = memberService.findOneByUsername(username).orElseThrow(InvalidCertificateException::new);

        ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest(productName,
                productPrice, productInformation, categoryId, hashtags,
                productDeliveryTerm, productDeliveryPrice, productRepImage, productImage1, productImage2, productImage3, productImage4);
        try {
            productService.saveProduct(productRegisterRequest, findMember);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_REGISTER_SUCCESS, null));
    }

    //ok
    @ApiOperation(value = "상품 삭제하기")
    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<ResultResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_DELETE_SUCCESS, null));
    }

    //ok
    @ApiOperation(value = "특정 상품 불러오기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ResultResponse> getProduct(@PathVariable Long id) {
        ProductDetailResponse response = productService.getProduct(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "전체 카테고리 불러오기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @GetMapping(value = "/categories")
    public ResponseEntity<ResultResponse> getAllCategories() {
        List<Category> categories = productService.getCategories();
        List<CategoryResponse> response = new ArrayList<>();
        for (Category category : categories) {
            response.add(new CategoryResponse(category));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORIES_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "특정 카테고리 내 상품들 불러오기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/categoryProducts")
    public ResponseEntity<ResultResponse> getCategoryProducts(@Validated @RequestBody CategoryProductPageRequest categoryProductPageRequest) {
        List<ProductResponse> response = productService.getCategoryProducts(categoryProductPageRequest).getContent();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_PRODUCTS_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "조회수 높은 상품 최대 8개 불러오기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @GetMapping(value = "/products/lists/like")
    public ResponseEntity<ResultResponse> getBestProducts() {
        List<ProductResponse> response = productService.getBestProduct();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.BEST_PRODUCTS_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "최근 등록된 상품 최대 8개 불러오기")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @GetMapping(value = "/products/lists/recent")
    public ResponseEntity<ResultResponse> getRecentProducts() {
        List<ProductResponse> response = productService.getRecentProduct();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RECENT_PRODUCTS_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "상품 검색 결과")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @PostMapping(value = "/products")
    public ResponseEntity<ResultResponse> getSearchProducts(@Validated @RequestBody SearchProductPageRequest searchProductPageRequest) {

        List<ProductResponse> response = productService.getSearchResultProduct(searchProductPageRequest).getContent();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_PRODUCTS_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "카테고리 등록")
    @PostMapping(value = "/categoriesRegister", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registerCategory(@Validated @RequestBody CategoryRegisterRequest categoryRegisterRequest) {
        productService.saveCategory(categoryRegisterRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_REGISTER_SUCCESS, null));
    }


    //ok
    @ApiOperation(value = "상품 해시태그 검색 결과")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @GetMapping(value = "/hashtags")
    public ResponseEntity<ResultResponse> getSearchProductsByHashtag(@RequestParam(required = false) Long hashtag_id) {
        List<ProductResponse> response = productService.getHashtagSearchResult(hashtag_id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_PRODUCTS_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "상품 좋아요 숫자 조회")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @GetMapping(value = "/products/{id}/likes")
    public ResponseEntity<ResultResponse> getLikeNum(@PathVariable Long id) {
        ProductLikeNumResponse response = new ProductLikeNumResponse(productService.getLikeNum(id));
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_LIKE_NUM_SUCCESS, response));
    }


    //ok
    @ApiOperation(value = "회원 상품 좋아요 누르기")
    @PostMapping(value = "/products/{id}/like")
    public ResponseEntity<ResultResponse> likeProduct(@PathVariable Long id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long member_id = memberService.findOneByUsername(username).orElseThrow().getId();

        productService.likeProductAdd(member_id, id);
        //if member already like product? => need discuss
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_LIKE_SUCCESS, null));
    }

    //ok
    @ApiOperation(value = "회원이 좋아요 한 상품 전체 조회")
    @GetMapping(value = "/products/lists/mylike")
    public ResponseEntity<ResultResponse> getMemberLikeProudcts() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long member_id = memberService.findOneByUsername(username).orElseThrow().getId();
        List<ProductResponse> response = productService.getMemberLikeProducts(member_id);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_LIKE_PRODUCTS_GET_SUCCESS, response));
    }

    //ok
    @ApiOperation(value = "회원 상품 좋아요 취소")
    @DeleteMapping(value = "/products/{id}/likeundo")
    public ResponseEntity<ResultResponse> undoMemberLikeProudct(@PathVariable Long id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long member_id = memberService.findOneByUsername(username).orElseThrow().getId();
        productService.likeProductUndo(member_id, id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_LIKE_UNDO_SUCCESS, null));

    }

    //ok
    @DeleteMapping("/categories/{id}")
    @ApiOperation(value = "카테고리 삭제")
    public ResponseEntity<ResultResponse> deleteCategory(@PathVariable Long id) {
        productService.deleteCategory(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_DELETE_SUCCESS, null));
    }

    @GetMapping("/hashtagProducts")
    @ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
    @ApiOperation(value = "상품 가장 많은 해시태그 5개 조회")
    public ResponseEntity<ResultResponse> hashtagProduct() {
        List<HashtagProductResponse> response = productService.getHashtagProducts();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.HASHTAG_PRODUCTS_GET_SUCCESS, response));
    }

    @GetMapping("/recentProducts")
    @ApiOperation(value = "페이징된 최근 등록된 상품 전체 조회")
    public ResponseEntity<ResultResponse> recentProducts(@RequestParam(value = "pageNum") @Range(min = 0, message = "페이지 번호는 0 이상이어야 합니다") int pageNum) {
        List<ProductResponse> response = productService.getTotalRecentProducts(pageNum).getContent();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RECENT_PRODUCTS_GET_SUCCESS, response));
    }

    @GetMapping("/popularProducts")
    @ApiOperation(value = "페이징된 인기 상품 전체 조회")
    public ResponseEntity<ResultResponse> popularProducts(@RequestParam(value = "pageNum") @Range(min = 0, message = "페이지 번호는 0 이상이어야 합니다") int pageNum) {
        List<ProductResponse> response = productService.getTotalPopularProducts(pageNum).getContent();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.BEST_PRODUCTS_GET_SUCCESS, response));
    }

}
