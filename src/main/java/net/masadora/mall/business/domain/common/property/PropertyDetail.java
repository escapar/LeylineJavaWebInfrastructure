package net.masadora.mall.business.domain.common.property;

import net.masadora.mall.business.domain.product.Product;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mall_d_property_detail database table.
 * 
 */
@Entity
@Table(name="mall_d_property_detail")
@NamedQuery(name="PropertyDetail.findAll", query="SELECT p FROM PropertyDetail p")
public class PropertyDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String value;

	//bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	//uni-directional many-to-one association to Property
	@ManyToOne
	private Property property;

	public PropertyDetail() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

}