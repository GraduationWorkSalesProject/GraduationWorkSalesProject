package GraduationWorkSalesProject.graduation.com.dto.seller;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerRequest {

	private Long id;

	private String sellerInformation;

	@NotEmpty
	private String sellerName;

	@NotEmpty
	private String sellerBank;

	@NotEmpty
	private String sellerAccount;

	@NotEmpty
	private Long memberId;
}
