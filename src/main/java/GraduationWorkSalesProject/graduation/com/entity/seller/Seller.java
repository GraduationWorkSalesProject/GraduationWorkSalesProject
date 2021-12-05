package GraduationWorkSalesProject.graduation.com.entity.seller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import GraduationWorkSalesProject.graduation.com.dto.seller.SellerResponse;
import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
@Entity
@AllArgsConstructor
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id", updatable = false)
    private Long id;

    @Column(name = "seller_information")
    private String sellerInformation;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_bank")
    private String sellerBank;

    @Column(name = "seller_account")
    private String sellerAccount;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    public SellerResponse convert() {
    	return SellerResponse.builder()
    			.id(id)
    			.sellerInformation(sellerInformation)
    			.sellerName(sellerName)
    			.sellerBank(sellerBank)
    			.sellerAccount(sellerAccount)
    			.memberId(member.getId())
    			.build();
	}


}
