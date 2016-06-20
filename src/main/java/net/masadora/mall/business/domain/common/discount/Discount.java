package net.masadora.mall.business.domain.common.discount;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the mall_d_discount database table.
 * 
 */
@Entity
@Table(name="mall_d_discount")
@NamedQuery(name="Discount.findAll", query="SELECT d FROM Discount d")
public class Discount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="COUPON_ID")
	private Long couponId;

	private String description;

	@Column(name="DISCOUNT_TYPE")
	private int discountType;

	private float value;

	public Discount() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscountType() {
		return this.discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}