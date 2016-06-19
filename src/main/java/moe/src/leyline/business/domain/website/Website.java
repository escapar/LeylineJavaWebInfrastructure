package moe.src.leyline.business.domain.website;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;
import moe.src.leyline.business.domain.user.DomainUser;

/**
 * The persistent class for the website database table.
 * 
 */
@Entity
@Table(name="website")
@NamedQuery(name="Website.findAll", query="SELECT w FROM Website w")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode
public class Website implements moe.src.leyline.framework.domain.LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "created_at")
	private Long createdAt;

	private String description;

	@Column(nullable = false)
	private String domain;

	@Column(name = "modified_at")
	private Long modifiedAt;

	private String screenshot;

	private String title;

	private String verifyKey;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private DomainUser user;

	//bi-directional many-to-one association to WebsiteRelation
	@OneToMany(mappedBy = "master")
	private List<WebsiteRelation> friends;

	@OneToMany(mappedBy = "website",cascade = CascadeType.ALL)
	private List<WebsiteUserVerify> websiteUserVerifies;

	//bi-directional many-to-one association to WebsiteRelation
	@OneToMany(mappedBy = "servant")
	private List<WebsiteRelation> referencedBy;

	public Website() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Long getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getScreenshot() {
		return this.screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DomainUser getUser() {
		return this.user;
	}

	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(final String verifyKey) {
		this.verifyKey = verifyKey;
	}

	public void setUser(DomainUser user) {
		this.user = user;
	}

	public List<WebsiteRelation> getFriends() {
		return this.friends;
	}

	public void setFriends(List<WebsiteRelation> friends) {
		this.friends = friends;
	}

	public WebsiteRelation addFriend(WebsiteRelation friend) {
		getFriends().add(friend);
		friend.setMaster(this);

		return friend;
	}

	public WebsiteRelation removeFriend(WebsiteRelation friend) {
		getFriends().remove(friend);
		friend.setMaster(null);

		return friend;
	}

	public void addVerify(DomainUser u) {
		addWebsiteUserVerify(new WebsiteUserVerify(null, u, this));
	}

	public WebsiteUserVerify addWebsiteUserVerify(WebsiteUserVerify websiteUserVerify) {
		getWebsiteUserVerifies().add(websiteUserVerify);
		websiteUserVerify.setWebsite(this);

		return websiteUserVerify;
	}

	public WebsiteUserVerify removeWebsiteUserVerify(WebsiteUserVerify websiteUserVerify) {
		getWebsiteUserVerifies().remove(websiteUserVerify);
		websiteUserVerify.setWebsite(null);

		return websiteUserVerify;
	}

	public List<WebsiteUserVerify> getWebsiteUserVerifies() {
		return websiteUserVerifies;
	}

	public void setWebsiteUserVerifies(final List<WebsiteUserVerify> websiteUserVerifies) {
		this.websiteUserVerifies = websiteUserVerifies;
	}

	public List<WebsiteRelation> getReferencedBy() {
		return this.referencedBy;
	}

	public void setReferencedBy(List<WebsiteRelation> referencedBy) {
		this.referencedBy = referencedBy;
	}

	public WebsiteRelation addReferencedBy(WebsiteRelation referencedBy) {
		getReferencedBy().add(referencedBy);
		referencedBy.setServant(this);

		return referencedBy;
	}

	public WebsiteRelation removeReferencedBy(WebsiteRelation referencedBy) {
		getReferencedBy().remove(referencedBy);
		referencedBy.setServant(null);

		return referencedBy;
	}


}