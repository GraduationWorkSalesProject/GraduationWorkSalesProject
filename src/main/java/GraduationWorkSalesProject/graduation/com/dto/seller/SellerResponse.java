package GraduationWorkSalesProject.graduation.com.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class SellerResponse {
	private Long id;

	private String sellerInformation;

	private String sellerName;

	private String sellerBank;

	private String sellerAccount;

	private Long memberId;

}
