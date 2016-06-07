package com.masadora.mall.business.domain.product;

import com.masadora.mall.business.domain.OrderDetail;
import com.masadora.mall.framework.domain.DO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the product database table.
 *
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")

@EqualsAndHashCode @Data public class Product implements DO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String name;

	private double price;

	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="product", fetch=FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	public Product() {
	}


}
