package GraduationWorkSalesProject.graduation.com.dto.seller;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellerDTO {

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
