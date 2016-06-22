package net.masadora.mall.business.domain.common.category;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the mall_d_category database table.
 * 
 */
@Entity
@Cacheable
@EqualsAndHashCode
@ToString
@Table(name="mall_d_category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	//uni-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "ROOT_CATEGORY_ID")
	private Category rootCategory;


	public Category() {
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

	public Category getRootCategory() {
		return this.rootCategory;
	}

	public void setRootCategory(Category rootCategory) {
		this.rootCategory = rootCategory;
	}
}
