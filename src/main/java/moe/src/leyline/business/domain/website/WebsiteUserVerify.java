package moe.src.leyline.business.domain.website;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import moe.src.leyline.business.domain.user.DomainUser;

/**
 * The persistent class for the website_user_verify database table.
 * 
 */
@Entity
@AllArgsConstructor
@Table(name="website_user_verify")
@NamedQuery(name="WebsiteUserVerify.findAll", query="SELECT w FROM WebsiteUserVerify w")
public class WebsiteUserVerify implements moe.src.leyline.framework.domain.LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	//uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private DomainUser user;

	//uni-directional many-to-one association to Website
	@ManyToOne
	@JoinColumn(name="website_id", nullable=false)
	private Website website;

	public WebsiteUserVerify() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DomainUser getUser() {
		return this.user;
	}

	public void setUser(DomainUser user) {
		this.user = user;
	}

	public Website getWebsite() {
		return this.website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}