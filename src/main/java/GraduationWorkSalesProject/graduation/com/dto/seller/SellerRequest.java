package GraduationWorkSalesProject.graduation.com.dto.seller;

import javax.validation.constraints.NotEmpty;

import GraduationWorkSalesProject.graduation.com.entity.seller.Seller;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerRequest {

	private String sellerInformation;

	@NotEmpty
	private String sellerName;

	@NotEmpty
	private String sellerBank;

	@NotEmpty
	private String sellerAccount;

	@NotEmpty
	private Long memberId;


	public Seller convert() {
		return Seller.builder()
				.sellerInformation(sellerInformation)
				.sellerName(sellerName)
				.sellerBank(sellerBank)
				.sellerAccount(sellerAccount)
				.build();
	}
}
