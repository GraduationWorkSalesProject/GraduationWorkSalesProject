package GraduationWorkSalesProject.graduation.com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import GraduationWorkSalesProject.graduation.com.dto.result.ResultCode;
import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerResponse;
import GraduationWorkSalesProject.graduation.com.service.SellerService;
import io.swagger.annotations.Api;
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
		UserDetails userDetails = (UserDetails) principal;

		SellerResponse sellerResponse = sellerService.getSellerInfo(userDetails.getUsername());
		return ResponseEntity.ok(ResultResponse.of(ResultCode.FIND_SELLER_INFORMATION_SUCCESS, sellerResponse));
	}



}