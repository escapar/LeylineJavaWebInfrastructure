package moe.src.leyline.business.domain.user;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.crypto.bcrypt.BCrypt;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;
import moe.src.leyline.framework.domain.user.LeylineUser;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name="User")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "DomainUser.findAll", query = "SELECT u FROM DomainUser u")
@EqualsAndHashCode
@ToString
public class DomainUser implements LeylineUser {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String password;

    private int role;

    public DomainUser() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUnHashedPassword(String password) {
        setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
