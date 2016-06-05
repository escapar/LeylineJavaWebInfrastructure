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
	private long id;

	private long price;

	private long rate;

	//bi-directional one-to-one association to Coupon
	@OneToOne(mappedBy="discountRule")
	private Coupon coupon;

	public DiscountRule() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPrice() {
		return this.price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getRate() {
		return this.rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	public Coupon getCoupon() {
		return this.coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}
