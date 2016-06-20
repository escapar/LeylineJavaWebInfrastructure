package net.masadora.mall.business.domain.common.property;

import net.masadora.mall.business.domain.common.category.Category;
import net.masadora.mall.business.domain.product.Product;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mall_d_property database table.
 * 
 */
@Entity
@Table(name="mall_d_property")
@NamedQuery(name="Property.findAll", query="SELECT p FROM Property p")
public class Property implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	//bi-directional many-to-many association to Product
	@ManyToMany(mappedBy="mallDProperties")
	private List<Product> mallDProducts;


	public Property() {
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

	public List<Product> getMallDProducts() {
		return this.mallDProducts;
	}

	public void setMallDProducts(List<Product> mallDProducts) {
		this.mallDProducts = mallDProducts;
	}

}