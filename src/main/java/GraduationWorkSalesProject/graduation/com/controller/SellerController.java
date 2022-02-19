package GraduationWorkSalesProject.graduation.com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerResponse;
import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.service.SellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "판매자 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SellerController {

	private final SellerService sellerService;

	/**
	 * 판매자 정보 조회
	 *
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "판매자 정보 조회")
	@GetMapping(value = "/sellers/profile")
	public ResponseEntity<ResultResponse> getSellerInfo() {
		log.info("판매자 정보 조회");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final UserDetails userDetails = (UserDetails) principal;

		final SellerResponse sellerResponse = sellerService.getSellerInfo(userDetails.getUsername());
		return ResponseEntity.ok(ResultResponse.of(ResultCode.FIND_SELLER_INFORMATION_SUCCESS, sellerResponse));
	}

	/**
	 * 판매자가 판 상품 (좋아요 순)
	 *
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "판매자가 판 상품 (좋아요 순)")
	@ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
	@GetMapping(value = "/sellers/{memberId}/products/likes")
	public ResponseEntity<ResultResponse> getBestProduct(@PathVariable Long memberId) {
		log.info("판매자가 판 상품 (좋아요 순)");

		final List<ProductResponse> productList = sellerService.getPopularProduct(memberId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.FIND_SELLER_PRODUCT_SUCCESS, productList));
	}

	/**
	 * 판매자가 판 상품 (최신 순)
	 *
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "판매자가 판 상품 (최신 순)")
	@ApiImplicitParam(name = "Authorization", value = "불필요", required = false, example = " ")
	@GetMapping(value = "/sellers/{memberId}/products/latest")
	public ResponseEntity<ResultResponse> getLatestProduct(@PathVariable Long memberId) {
		log.info("판매자가 판 상품 (좋아요 순)");

		final List<ProductResponse> productList = sellerService.getLatestProduct(memberId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.FIND_SELLER_PRODUCT_SUCCESS, productList));
	}

}