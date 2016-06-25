package moe.src.leyline.business.domain.topic;

import java.util.List;

import javax.persistence.Cacheable;
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

import moe.src.leyline.business.domain.user.User;

/**
 * The persistent class for the topic database table.
 */
@Entity
@Table(name = "topic")
@NamedQuery(name = "Topic.findAll", query = "SELECT t FROM Topic t")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Topic implements moe.src.leyline.framework.domain.LeylineDO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private int status;

    @Column(nullable = false)
    private String title;

    private int type;

    //bi-directional many-to-one association to Topic
    @ManyToOne
    @JoinColumn(name = "root_id")
    private Topic topic;

    //bi-directional many-to-one association to Topic
    @OneToMany(mappedBy = "topic")
    private List<Topic> topics;

    //bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    public Topic() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public Topic setId(final Long id) {
        this.id = id;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Topic setStatus(final int status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Topic setTitle(final String title) {
        this.title = title;
        return this;
    }

    public int getType() {
        return type;
    }

    public Topic setType(final int type) {
        this.type = type;
        return this;
    }

    public Topic getTopic() {
        return topic;
    }

    public Topic setTopic(final Topic topic) {
        this.topic = topic;
        return this;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public Topic setTopics(final List<Topic> topics) {
        this.topics = topics;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Topic setUser(final User user) {
        this.user = user;
        return this;
    }
}