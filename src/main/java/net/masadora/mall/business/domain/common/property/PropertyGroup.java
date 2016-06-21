package net.masadora.mall.business.domain.common.property;

import net.masadora.mall.business.domain.common.category.Category;
import net.masadora.mall.framework.domain.LeylineDO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the mall_d_property database table.
 * 
 */
@Entity
@Cacheable
@Table(name="mall_d_property_group")
@NamedQuery(name="PropertyGroup.findAll", query="SELECT p FROM PropertyGroup p")
public class PropertyGroup implements LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	private Category category;

	@OneToMany(mappedBy = "group")
	private List<Property> properties;

	public PropertyGroup() {
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
}