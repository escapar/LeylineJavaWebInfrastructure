package net.masadora.mall.business.domain.common.property;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.masadora.mall.business.domain.common.category.Category;
import net.masadora.mall.framework.domain.AppDO;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mall_d_property database table.
 * 
 */
@Entity
@Cacheable
//@EqualsAndHashCode(exclude={"category"})
@EqualsAndHashCode
@ToString
@Table(name="mall_d_property")
@NamedQuery(name="Property.findAll", query="SELECT p FROM Property p")
public class Property implements AppDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Category category;


    public Property() {
	}

	public Long getId() {
		return this.id;
	}

	public Property setId(Long id) {
		this.id = id;
        return this;
	}

	public String getName() {
		return this.name;
	}

	public Property setName(String name) {
		this.name = name;
        return this;
	}

	public Category getCategory() {
		return category;
	}

	public Property setCategory(Category category) {
		this.category = category;
        return this;
	}

}