package moe.src.leyline.business.domain.commons.lang;

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
@Table(name = "lang")
@NamedQuery(name = "Lang.findAll", query = "SELECT l FROM Lang l")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lang implements LeylineCommons{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public Lang setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Lang setName(final String name) {
        this.name = name;
        return this;
    }
}
