package moe.src.leyline.business.domain.commons.location;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import moe.src.leyline.framework.domain.LeylineCommons;
import moe.src.leyline.framework.interfaces.view.LeylineView;

/**
 * Created by bytenoob on 6/26/16.
 */
@Entity
@Table(name = "location")
@NamedQuery(name = "Location.findAll", query = "SELECT lo FROM Location lo")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements LeylineCommons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public Location setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location setName(final String name) {
        this.name = name;
        return this;
    }
}
