package net.masadora.mall.business.domain.common.property;

import net.masadora.mall.framework.domain.AppDO;

import javax.persistence.*;


/**
 * The persistent class for the mall_d_property_detail database table.
 * 
 */
@Entity
@Cacheable
@Table(name="mall_d_property_detail")
@NamedQuery(name="PropertyDetail.findAll", query="SELECT p FROM PropertyDetail p")
public class PropertyDetail implements AppDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String value;

	//uni-directional many-to-one association to Property
	@ManyToOne(cascade = CascadeType.MERGE)
	private Property property;

	private boolean display;

	public PropertyDetail() {
	}

	public Long getId() {
		return this.id;
	}

	public PropertyDetail setId(Long id) {
		this.id = id;
		return this;
	}

	public String getValue() {
		return this.value;
	}

	public PropertyDetail setValue(String value) {
		this.value = value;
		return this;
	}


	public Property getProperty() {
		return this.property;
	}

	public PropertyDetail setProperty(Property property) {
		this.property = property;
		return this;
	}

	public boolean getDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
}