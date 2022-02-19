package GraduationWorkSalesProject.graduation.com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import GraduationWorkSalesProject.graduation.com.dto.product.ProductResponse;
import GraduationWorkSalesProject.graduation.com.dto.seller.SellerResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.product.CategoryProduct;
import GraduationWorkSalesProject.graduation.com.entity.product.Like;
import GraduationWorkSalesProject.graduation.com.entity.product.Product;
import GraduationWorkSalesProject.graduation.com.entity.product.ProductHashtag;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import GraduationWorkSalesProject.graduation.com.exception.CategoryNotExistException;
import GraduationWorkSalesProject.graduation.com.exception.SellerNotFoundException;
import GraduationWorkSalesProject.graduation.com.exception.UsernameNotFoundException;
import GraduationWorkSalesProject.graduation.com.repository.CategoryProductRepository;
import GraduationWorkSalesProject.graduation.com.repository.CategoryRepository;
import GraduationWorkSalesProject.graduation.com.repository.LikeRepository;
import GraduationWorkSalesProject.graduation.com.repository.MemberRepository;
import GraduationWorkSalesProject.graduation.com.repository.ProductRepository;
import GraduationWorkSalesProject.graduation.com.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {
	private final SellerRepository sellerRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final CategoryProductRepository categoryProductRepository;
	private final CategoryRepository categoryRepository;
	private final LikeRepository likeRepository;

	/**
	 * 회원 닉네임으로 판매자 정보 조회
	 *
	 * @param userName
	 * @return
	 */
	public SellerResponse getSellerInfo(String userName) {
		Member member = memberRepository.findByUsername(userName).orElseThrow(UsernameNotFoundException::new);
		Seller seller = sellerRepository.findByMemberId(member.getId()).orElseThrow(SellerNotFoundException::new);
		return seller.convert();
	}

	/**
	 * 판매자가 판 상품 (최신 순)
	 * 
	 * @param memberId
	 * @return List<ProductResponse>
	 */
	public List<ProductResponse> getLatestProduct(Long memberId) {
		sellerRepository.findByMemberId(memberId).orElseThrow(SellerNotFoundException::new);
		List<Product> products = productRepository.findByMemberIdOrderByRegisterDate(memberId);
		List<ProductResponse> response = new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			ProductResponse productResponse = new ProductResponse(products.get(i));
			Long categoryId = getCategoryIdByProduct(products.get(i).getId());
			productResponse.setCategoryId(categoryId);
			productResponse.setCategoryName(categoryRepository.findById(categoryId)
					.orElseThrow(CategoryNotExistException::new).getCategoryName());
			response.add(productResponse);
		}
		return response;
	}

	/**
	 * 판매자가 판 상품 (좋아요 순)
	 * 
	 * @param memberId
	 * @return List<ProductResponse>
	 */
	public List<ProductResponse> getPopularProduct(Long memberId) {
		sellerRepository.findByMemberId(memberId).orElseThrow(SellerNotFoundException::new);
		List<Product> products = productRepository.findByMemberIdOrderByLikeCountDESC(memberId);
		List<ProductResponse> response = new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			ProductResponse productResponse = new ProductResponse(products.get(i));
			Long categoryId = getCategoryIdByProduct(products.get(i).getId());
			productResponse.setCategoryId(categoryId);
			productResponse.setCategoryName(categoryRepository.findById(categoryId)
					.orElseThrow(CategoryNotExistException::new).getCategoryName());
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
