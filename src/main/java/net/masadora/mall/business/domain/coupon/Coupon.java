package net.masadora.mall.business.domain.coupon;

import javax.persistence.*;
import java.io.Serializable;



/**
 * The persistent class for the mall_d_coupon database table.
 * 
 */
@Entity
@Cacheable
@Table(name="mall_d_coupon")
@NamedQuery(name="Coupon.findAll", query="SELECT c FROM Coupon c")
public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="CREATED_AT")
	private Long createdAt;

	@Column(name="EXPIRED_AT")
	private Long expiredAt;

	private String name;

	public Coupon() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getExpiredAt() {
		return this.expiredAt;
	}

	public void setExpiredAt(Long expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}