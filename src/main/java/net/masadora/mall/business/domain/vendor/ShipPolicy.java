package net.masadora.mall.business.domain.vendor;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mall_d_ship_policy database table.
 * 
 */
@Entity
@Cacheable
@Table(name="mall_d_ship_policy")
@NamedQuery(name="ShipPolicy.findAll", query="SELECT s FROM ShipPolicy s")
public class ShipPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="AMT_SHIP")
	private float amtShip;

	@Column(name="AMT_TOTAL")
	private float amtTotal;

	@Column(name="POLICY_OR")
	private boolean policyOr;

	private int weight;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	private Vendor vendor;

	public ShipPolicy() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getAmtShip() {
		return this.amtShip;
	}

	public void setAmtShip(float amtShip) {
		this.amtShip = amtShip;
	}

	public float getAmtTotal() {
		return this.amtTotal;
	}

	public void setAmtTotal(float amtTotal) {
		this.amtTotal = amtTotal;
	}

	public boolean getPolicyOr() {
		return this.policyOr;
	}

	public void setPolicyOr(boolean policyOr) {
		this.policyOr = policyOr;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}