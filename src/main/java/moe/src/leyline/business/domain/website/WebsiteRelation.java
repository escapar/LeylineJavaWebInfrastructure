package moe.src.leyline.business.domain.website;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * The persistent class for the website_relation database table.
 * 
 */
@Entity
@Table(name="website_relation")
@NamedQuery(name="WebsiteRelation.findAll", query="SELECT w FROM WebsiteRelation w")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode
@ToString
public class WebsiteRelation implements moe.src.leyline.framework.domain.LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false)
	private String description;

	@Column(nullable=false)
	private String title;

	//bi-directional many-to-one association to Website
	@ManyToOne
	@JoinColumn(name="master_website_id", nullable=false)
	private Website master;

	//bi-directional many-to-one association to Website
	@ManyToOne
	@JoinColumn(name="servant_website_id", nullable=false)
	private Website servant;

	public WebsiteRelation() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Website getMaster() {
		return this.master;
	}

	public void setMaster(Website master) {
		this.master = master;
	}

	public Website getServant() {
		return this.servant;
	}

	public void setServant(Website servant) {
		this.servant = servant;
	}

}