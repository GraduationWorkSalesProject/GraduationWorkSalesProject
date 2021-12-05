package GraduationWorkSalesProject.graduation.com.dto.seller;

import javax.validation.constraints.NotEmpty;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "판매자 등록 데이터 모델")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerRegisterRequest {

	@ApiModelProperty(value = "판매자 소개글", example = "안녕하세요", required = false)
	private String sellerInformation;

	@ApiModelProperty(value = "판매자 이름", example = "떙떙떙", required = true)
	@NotEmpty(message = "판매자 이름을 입력해주세요.")
	private String sellerName;

	@ApiModelProperty(value = "은행이름", example = "기업", required = true)
	@NotEmpty(message = "은행 이름을 입력해주세요.")
	private String sellerBank;

	@ApiModelProperty(value = "은행 계좌 번호", example = "01000000000", required = true)
	@NotEmpty(message = "은행 계좌 번호를 입력해주세요.")
	private String sellerAccount;


	public Seller convert(Member member) {
		return Seller.builder()
				.sellerInformation(sellerInformation)
				.sellerName(sellerName)
				.sellerBank(sellerBank)
				.sellerAccount(sellerAccount)
				.member(member)
				.build();
	}
}
