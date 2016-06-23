package net.masadora.mall.business.domain.vendor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the mall_d_vendor database table.
 * 
 */
@Entity
@Cacheable
@Table(name="mall_d_vendor")
@NamedQuery(name="Vendor.findAll", query="SELECT v FROM Vendor v")
public class Vendor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	//bi-directional many-to-one association to ShipPolicy
	@OneToMany(mappedBy="vendor")
	private List<ShipPolicy> shipPolicies;

	public Vendor() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ShipPolicy> getShipPolicies() {
		return this.shipPolicies;
	}

	public void setShipPolicies(List<ShipPolicy> shipPolicies) {
		this.shipPolicies = shipPolicies;
	}

	public ShipPolicy addShipPolicy(ShipPolicy shipPolicy) {
		getShipPolicies().add(shipPolicy);
		shipPolicy.setVendor(this);

		return shipPolicy;
	}

	public ShipPolicy removeShipPolicy(ShipPolicy shipPolicy) {
		getShipPolicies().remove(shipPolicy);
		shipPolicy.setVendor(null);

		return shipPolicy;
	}

}