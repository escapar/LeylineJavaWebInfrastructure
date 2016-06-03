package com.masadora.shop.domain;

import moe.src.leyline.domain.DO;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the coupon database table.
 * 
 */
@Entity
@NamedQuery(name="Coupon.findAll", query="SELECT c FROM Coupon c")
public class Coupon implements DO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="discount_rule_id")
	private int discountRuleId;

	private int id;

	private String name;

	//bi-directional one-to-one association to DiscountRule
	@OneToOne
	@JoinColumn(name="discount_rule_id")
	private DiscountRule discountRule;

	public Coupon() {
	}

	public int getDiscountRuleId() {
		return this.discountRuleId;
	}

	public void setDiscountRuleId(int discountRuleId) {
		this.discountRuleId = discountRuleId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DiscountRule getDiscountRule() {
		return this.discountRule;
	}

	public void setDiscountRule(DiscountRule discountRule) {
		this.discountRule = discountRule;
	}

}