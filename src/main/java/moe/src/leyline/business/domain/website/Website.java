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
import org.joda.time.DateTime;

import lombok.Data;
import moe.src.leyline.business.domain.user.User;

/**
 * The persistent class for the website database table.
 */
@Entity
@Data
@Table(name = "website")
@NamedQuery(name = "Website.findAll", query = "SELECT w FROM Website w")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class Website implements moe.src.leyline.framework.domain.LeylineDO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "created_at")
    private Long createdAt = new DateTime().getMillis();

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
    private User user;

    //bi-directional many-to-one association to WebsiteRelation
    @OneToMany(mappedBy = "master", cascade = CascadeType.MERGE)
    private List<WebsiteRelation> friends;

    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL)
    private List<WebsiteUserVerify> websiteUserVerifies;

    //bi-directional many-to-one association to WebsiteRelation
    @OneToMany(mappedBy = "servant", cascade = CascadeType.REFRESH)
    private List<WebsiteRelation> referencedBy;

    public Website() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Website addVerify(User u) {
        addWebsiteUserVerify(new WebsiteUserVerify(null, u, this));
        return this;
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

    public Long getId() {
        return id;
    }

    public Website setId(final Long id) {
        this.id = id;
        return this;
    }

    public Long getCreatedAt() {
        if(createdAt == null){
            setCreatedAt(new DateTime().getMillis());
        }
        return createdAt;
    }

    public Website setCreatedAt(Long createdAt) {
        if(createdAt == null){
            createdAt = new DateTime().getMillis();
        }
        this.createdAt = createdAt;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Website setDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Website setDomain(final String domain) {
        this.domain = domain;
        return this;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public Website setModifiedAt(final Long modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public Website setScreenshot(final String screenshot) {
        this.screenshot = screenshot;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Website setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public Website setVerifyKey(final String verifyKey) {
        this.verifyKey = verifyKey;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Website setUser(final User user) {
        this.user = user;
        return this;
    }

    public List<WebsiteRelation> getFriends() {
        return friends;
    }

    public Website setFriends(final List<WebsiteRelation> friends) {
        this.friends = friends;
        return this;
    }

    public List<WebsiteUserVerify> getWebsiteUserVerifies() {
        return websiteUserVerifies;
    }

    public Website setWebsiteUserVerifies(final List<WebsiteUserVerify> websiteUserVerifies) {
        this.websiteUserVerifies = websiteUserVerifies;
        return this;
    }

    public List<WebsiteRelation> getReferencedBy() {
        return referencedBy;
    }

    public Website setReferencedBy(final List<WebsiteRelation> referencedBy) {
        this.referencedBy = referencedBy;
        return this;
    }

}