package GraduationWorkSalesProject.graduation.com.controller;

import GraduationWorkSalesProject.graduation.com.dto.product.CategoriesResponse;
import GraduationWorkSalesProject.graduation.com.dto.product.ProductListResponse;
import GraduationWorkSalesProject.graduation.com.dto.product.ProductRegisterRequest;
import GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.entity.product.Category;
import GraduationWorkSalesProject.graduation.com.entity.product.CategoryProduct;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "상품 등록하기")
    @PostMapping(value = "/products", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> register(ProductRegisterRequest productRegisterRequest){
        Long product_id = productService.saveProduct(productRegisterRequest);

        Optional<Product> product = productService.getProduct(product_id);
        Optional<Category> category = productService.findCategory(productRegisterRequest.getCategory_id());
        CategoryProduct categoryProduct = null;
        categoryProduct.setCategory(category.get());
        categoryProduct.setProduct(product.get());
        category.get().addCategoryProduct(categoryProduct);
        productService.saveCategory(category.get());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_REGISTER_SUCCESS,null));
    }

    
    @ApiOperation(value = "특정 상품 불러오기")
    @GetMapping(value = "/products/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getProduct(@PathVariable Long id){
        Optional<Product> product =  productService.getProduct(id);
        ProductResponse response = new ProductResponse(product.get());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_GET_SUCCESS,response));
    }


    @ApiOperation(value = "전체 카테고리 불러오기")
    @GetMapping(value = "/categories", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getAllCategories(){
        List<Category> categories = productService.getCategories();
        CategoriesResponse response = new CategoriesResponse(categories);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PRODUCT_GET_SUCCESS,response));
    }

    @ApiOperation(value = "특정 카테고리 내 상품들 불러오기")
    @GetMapping(value = "/categories/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getCategoryProducts(@PathVariable Long id){
        List<Product> products = productService.getCategoryProducts(id);
        ProductListResponse response = new ProductListResponse(products);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CATEGORY_PRODUCTS_GET_SUCCESS,response));
    }
    
    @ApiOperation(value = "좋아요 많이 받은 상품 최대 10개 불러오기")
    @GetMapping(value = "/products/lists/like", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getBestProducts(){
        List<Product> products = productService.getBestProduct();
        ProductListResponse response = new ProductListResponse(products);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.BEST_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "최근 등록된 상품 최대 10개 불러오기")
    @GetMapping(value = "/products/lists/recent", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getRecentProducts(){
        List<Product> products = productService.getRecentProduct();
        ProductListResponse response = new ProductListResponse(products);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RECENT_PRODUCTS_GET_SUCCESS,response));
    }

    @ApiOperation(value = "상품 검색 결과")
    @GetMapping(value = "/products", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getSearchProducts(@RequestParam(required = false) String search){
        List<Product> products = productService.getSearchResultProduct(search);
        ProductListResponse response = new ProductListResponse(products);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_PRODUCTS_GET_SUCCESS,response));
    }



}
