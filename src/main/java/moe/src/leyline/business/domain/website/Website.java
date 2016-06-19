package moe.src.leyline.business.domain.website;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.util.List;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

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
@ToString
public class Website implements moe.src.leyline.framework.domain.LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	@Column(name="created_at")
	private Long createdAt;

	private String description;

	@Column(nullable=false)
	private String domain;

	@Column(name="modified_at")
	private Long modifiedAt;

	private String screenshot;

	private String title;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="owner_id")
	private DomainUser user;

	//bi-directional many-to-one association to WebsiteRelation
	@OneToMany(mappedBy="master")
	private List<WebsiteRelation> friends;

	//bi-directional many-to-one association to WebsiteRelation
	@OneToMany(mappedBy="servant")
	private List<WebsiteRelation> referencedBy;

	public Website() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Object getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getDomain() {
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

	public Object getScreenshot() {
		return this.screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public Object getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DomainUser getUser() {
		return this.user;
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