package moe.src.leyline.business.domain.website;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.ToString;
import moe.src.leyline.business.domain.user.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * The persistent class for the website_user_verify database table.
 * 
 */
@Entity
@Table(name="website_user_verify")
@NamedQuery(name="WebsiteUserVerify.findAll", query="SELECT w FROM WebsiteUserVerify w")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode
@ToString
public class WebsiteUserVerify implements moe.src.leyline.framework.domain.LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	//uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	//uni-directional many-to-one association to Website
	@ManyToOne
	@JoinColumn(name="website_id", nullable=false)
	private Website website;

	public WebsiteUserVerify() {
	}

	public WebsiteUserVerify(Long id,User user, Website website) {
		this.id = id;
		this.user = user;
		this.website = website;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Website getWebsite() {
		return this.website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}