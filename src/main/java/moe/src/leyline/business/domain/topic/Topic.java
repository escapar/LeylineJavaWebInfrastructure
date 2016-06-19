package moe.src.leyline.business.domain.topic;

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
 * The persistent class for the topic database table.
 * 
 */
@Entity
@Table(name="topic")
@NamedQuery(name="Topic.findAll", query="SELECT t FROM Topic t")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode
@ToString
public class Topic implements moe.src.leyline.framework.domain.LeylineDO {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private String id;

	private int status;

	@Column(nullable=false)
	private String title;

	private int type;

	//bi-directional many-to-one association to Topic
	@ManyToOne
	@JoinColumn(name="root_id")
	private Topic topic;

	//bi-directional many-to-one association to Topic
	@OneToMany(mappedBy="topic")
	private List<Topic> topics;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="owner_id")
	private DomainUser user;

	public Topic() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public List<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Topic addTopic(Topic topic) {
		getTopics().add(topic);
		topic.setTopic(this);

		return topic;
	}

	public Topic removeTopic(Topic topic) {
		getTopics().remove(topic);
		topic.setTopic(null);

		return topic;
	}

	public DomainUser getUser() {
		return this.user;
	}

	public void setUser(DomainUser user) {
		this.user = user;
	}

}