package net.masadora.mall.business.domain.product;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mall_d_product_image database table.
 * 
 */
@Entity
@Table(name="mall_d_product_image")
@NamedQuery(name="ProductImage.findAll", query="SELECT p FROM ProductImage p")
public class ProductImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String description;

	private String url;


	public ProductImage() {
	}

	public Long getId() {
		return this.id;
	}

	public ProductImage setId(Long id) {
		this.id = id;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public ProductImage setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getUrl() {
		return this.url;
	}

	public ProductImage setUrl(String url) {
		this.url = url;
		return this;
	}


}