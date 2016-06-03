package com.masadora.shop.domain;

import moe.src.leyline.domain.DO;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the discount database table.
 * 
 */
@Entity
@NamedQuery(name="Discount.findAll", query="SELECT d FROM Discount d")
public class Discount implements DO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="coupon_id")
	private int couponId;

	@Column(name="discount_rule_id")
	private int discountRuleId;

	public Discount() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCouponId() {
		return this.couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public int getDiscountRuleId() {
		return this.discountRuleId;
	}

	public void setDiscountRuleId(int discountRuleId) {
		this.discountRuleId = discountRuleId;
	}

}