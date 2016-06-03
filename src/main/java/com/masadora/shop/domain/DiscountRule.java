package com.masadora.shop.domain;

import moe.src.leyline.domain.DO;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the discount_rule database table.
 * 
 */
@Entity
@Table(name="discount_rule")
@NamedQuery(name="DiscountRule.findAll", query="SELECT d FROM DiscountRule d")
public class DiscountRule implements DO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int price;

	private int rate;

	//bi-directional one-to-one association to Coupon
	@OneToOne(mappedBy="discountRule")
	private Coupon coupon;

	public DiscountRule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public Coupon getCoupon() {
		return this.coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}