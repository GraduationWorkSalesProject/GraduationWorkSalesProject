package GraduationWorkSalesProject.graduation.com.entity.seller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import GraduationWorkSalesProject.graduation.com.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
@Entity
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

    @Builder
    public Seller(String sellerInformation, String sellerName, String sellerBank, String sellerAccount, Member member) {
    	this.sellerInformation = sellerInformation;
    	this.sellerName = sellerName;
    	this.sellerBank = sellerBank;
    	this.sellerAccount = sellerAccount;
    	this.member = member;
    }



}
