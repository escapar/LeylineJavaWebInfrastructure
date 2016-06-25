package moe.src.leyline.business.domain.website;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;

/**
 * The persistent class for the website_relation database table.
 */
@Entity
@Table(name = "website_relation")
@NamedQuery(name = "WebsiteRelation.findAll", query = "SELECT w FROM WebsiteRelation w")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode
@ToString
public class WebsiteRelation implements moe.src.leyline.framework.domain.LeylineDO, Comparable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String title;

    //bi-directional many-to-one association to Website
    @ManyToOne
    @JoinColumn(name = "master_website_id", nullable = false)
    private Website master;

    //bi-directional many-to-one association to Website
    @ManyToOne
    @JoinColumn(name = "servant_website_id", nullable = false)
    private Website servant;

    private boolean approved;

    private Long createdAt;

    public WebsiteRelation() {
    }

    public Long getId() {
        return this.id;
    }

    public WebsiteRelation setId(Long id) {
        this.id = id;
        return this;
    }

    public Object getDescription() {
        return this.description;
    }

    public WebsiteRelation setDescription(String description) {
        this.description = description;
        return this;
    }

    public Object getTitle() {
        return this.title;
    }

    public WebsiteRelation setTitle(String title) {
        this.title = title;
        return this;
    }

    public Website getMaster() {
        return this.master;
    }

    public WebsiteRelation setMaster(Website master) {
        this.master = master;
        return this;
    }

    public Website getServant() {
        return this.servant;
    }

    public WebsiteRelation setServant(Website servant) {
        this.servant = servant;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public WebsiteRelation setApproved(final boolean approved) {
        this.approved = approved;
        return this;
    }

    public Long getCreatedAt() {
        if (createdAt == null) {
            createdAt = new DateTime().getMillis();
        }
        return createdAt;
    }

    public WebsiteRelation setCreatedAt(Long createdAt) {
        if (createdAt == null) {
            createdAt = new DateTime().getMillis();
        }
        this.createdAt = createdAt;
        return this;
    }

    @Override public int compareTo(final Object o) {
        if (o instanceof WebsiteRelation) {
            return (int) (((WebsiteRelation) o).getCreatedAt() - createdAt);
        }
        return 0;
    }
}