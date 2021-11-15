package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.dto.product.*;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import GraduationWorkSalesProject.graduation.com.entity.product.Hashtag;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.repository.CategoryRepository;
import GraduationWorkSalesProject.graduation.com.service.MemberService;
import GraduationWorkSalesProject.graduation.com.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "상품 API")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @ApiOperation(value = "상품 등록하기")
    @PostMapping(value = "/products", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> register(@RequestBody ProductRegisterRequest productRegisterRequest){
        Product registerProduct = productRegisterRequest.convert();
        List<String> hashtags = productRegisterRequest.getHashtags();


        //if hashtag exist => add to product
        //if hashtag not exist => create hashtag and add to product
        Long product_id = productService.saveProduct(registerProduct);
        Optional<Category> category = productService.findCategory(productRegisterRequest.getCategoryId());
        category.orElseThrow().addProduct(registerProduct);
        productService.saveCategory(category.orElseThrow());

        for(String hashtag: hashtags){

            if(productService.getHashtagByHashtagName(hashtag)==null){
                HashtagRegisterRequest hashtagRegisterRequest = new HashtagRegisterRequest();
                hashtagRegisterRequest.setHashtagName(hashtag);
                Hashtag saveHashtag = hashtagRegisterRequest.covert();
                saveHashtag.addProduct(registerProduct);
                productService.saveHashtag(saveHashtag);
                //productService.saveHashtag(hashtagRegisterRequest.covert());
            }else{
                Hashtag registerHashtag = productService.getHashtagByHashtagName(hashtag);
                registerHashtag.addProduct(registerProduct);
                productService.saveHashtag(registerHashtag);
                //productService.saveHashtag(registerHashtag);
            }
        }


        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_REGISTER_SUCCESS,null));
    }

    @ApiOperation(value = "상품 삭제하기")
    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<ResultResponse> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_DELETE_SUCCESS,null));
    }


    @ApiOperation(value = "특정 상품 불러오기")
    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ResultResponse> getProduct(@PathVariable Long id){
        Optional<Product> product =  productService.getProduct(id);
        ProductResponse response = new ProductResponse(product.orElseThrow());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_GET_SUCCESS,response));
    }


    @ApiOperation(value = "전체 카테고리 불러오기")
    @GetMapping(value = "/categories")
    public ResponseEntity<ResultResponse> getAllCategories(){
        List<Category> categories = productService.getCategories();
        List<CategoryResponse> response = new ArrayList<>();
        for(Category category: categories){
            response.add(new CategoryResponse(category));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORIES_GET_SUCCESS,response));
    }

    @ApiOperation(value = "특정 카테고리 내 상품들 불러오기")
    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<ResultResponse> getCategoryProducts(@PathVariable Long id){
        List<Product> products = productService.getCategoryProducts(id);
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            response.add(new ProductResponse(product));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_PRODUCTS_GET_SUCCESS,response));
    }
    
    @ApiOperation(value = "좋아요 많이 받은 상품 최대 10개 불러오기")
    @GetMapping(value = "/products/lists/like")
    public ResponseEntity<ResultResponse> getBestProducts(){
        List<Product> products = productService.getBestProduct();
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            response.add(new ProductResponse(product));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.BEST_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "최근 등록된 상품 최대 10개 불러오기")
    @GetMapping(value = "/products/lists/recent")
    public ResponseEntity<ResultResponse> getRecentProducts(){
        List<Product> products = productService.getRecentProduct();
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            response.add(new ProductResponse(product));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RECENT_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "상품 검색 결과")
    @GetMapping(value = "/products")
    public ResponseEntity<ResultResponse> getSearchProducts(@RequestParam(required = false) String search){
        List<Product> products = productService.getSearchResultProduct(search);
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            response.add(new ProductResponse((product)));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "카테고리 등록")
    @PostMapping(value = "/categoriesRegister", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registerCategory(@RequestBody CategoryRegisterRequest categoryRegisterRequest){
        Category child_category = categoryRegisterRequest.convert();


        Category parent_category = categoryRepository.getOne(categoryRegisterRequest.getCategoryParentId());

        child_category.setCategoryParent(parent_category);
        productService.saveCategory(child_category);
        parent_category.addCategoryChild(child_category);
        productService.saveCategory(parent_category);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_REGISTER_SUCCESS,null));
    }


    @ApiOperation(value = "상품 해시태그 검색 결과")
    @GetMapping(value = "/hashtags")
    public ResponseEntity<ResultResponse> getSearchProductsByHashtag(@RequestParam(required = false) Long hashtag_id){
        List<Product> products = productService.getHashtagSearchResult(hashtag_id);
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            response.add(new ProductResponse(product));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "상품 좋아요 숫자 조회")
    @GetMapping(value = "/products/{id}/likes")
    public ResponseEntity<ResultResponse> getLikeNum(@PathVariable Long id){
        ProductLikeNumResponse response = new ProductLikeNumResponse(productService.getLikeNum(id));
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_LIKE_NUM_SUCCESS,response));
    }


    @ApiOperation(value = "회원 상품 좋아요 누르기")
    @GetMapping(value="/products/{id}/like", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> likeProduct(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        final String username = memberService.getUsernameFromAccessJwt(authorization);
        Long member_id = memberService.findOneByUsername(username).orElseThrow().getId();
        productService.LikeProductAdd(member_id,id);
        //if member already like product? => need discuss
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_LIKE_SUCCESS,null));
    }


    @ApiOperation(value = "회원이 좋아요 한 상품 전체 조회")
    @GetMapping(value = "/products/lists/mylike")
    public ResponseEntity<ResultResponse> getMemberLikeProudcts(@RequestHeader("Authorization") String authorization){
        final String username = memberService.getUsernameFromAccessJwt(authorization);
        Long member_id = memberService.findOneByUsername(username).orElseThrow().getId();
        List<Product> products = productService.getMemberLikeProducts(member_id);
        List<ProductResponse> response = new ArrayList<>();
        for(Product product: products){
            response.add(new ProductResponse(product));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MEMBER_LIKE_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "회원 상품 좋아요 취소")
    public ResponseEntity<ResultResponse> UndoMemberLikeProudct(@RequestHeader("Authorization") String authorization,Long product_id) {
        final String username = memberService.getUsernameFromAccessJwt(authorization);
        Long member_id = memberService.findOneByUsername(username).orElseThrow().getId();
        productService.LikeProductUndo(member_id,product_id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_LIKE_UNDO_SUCCESS,null));

    }

    @DeleteMapping("/categories/{id}")
    @ApiOperation(value = "카테고리 삭제")
    public ResponseEntity<ResultResponse> deleteCategory(@PathVariable Long id){
        productService.deleteCategory(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_DELETE_SUCCESS,null));
    }








}
