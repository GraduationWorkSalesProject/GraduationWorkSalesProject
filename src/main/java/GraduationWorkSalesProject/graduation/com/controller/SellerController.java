package GraduationWorkSalesProject.graduation.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import GraduationWorkSalesProject.graduation.com.dto.result.ResultResponse;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerRequest;
import GraduationWorkSalesProject.graduation.com.service.SellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


@Api(tags = "판매자 API")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SellerController {

	private static final Logger log = LoggerFactory.getLogger(SellerController.class);
	private final SellerService SellerService;

	@ApiOperation(value = "판매자 정보 변경")
	@PostMapping(value = "/sellers/profile")
	public ResponseEntity<ResultResponse> updateSellerProfile(@ModelAttribute SellerRequest request) {
		log.info(request.getSellerAccount());
		return null;
	}

}