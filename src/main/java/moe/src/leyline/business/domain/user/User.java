package moe.src.leyline.business.domain.user;

import java.util.Collection;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;
import moe.src.leyline.business.domain.commons.lang.Lang;
import moe.src.leyline.business.domain.commons.location.Location;
import moe.src.leyline.business.domain.commons.platform.Platform;
import moe.src.leyline.framework.domain.user.LeylineUser;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "User")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@EqualsAndHashCode
@ToString
public class User implements LeylineUser {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    private int role;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    private Lang lang;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    @Transient
    private boolean isAuthenticated;

    public User() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return this.id;
    }

    public User setId(final long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public User setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.password;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public User setUnHashedPassword(String password) {
        setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        return this;
    }

    public int getRole() {
        return role;
    }

    public User setRole(final int role) {
        this.role = role;
        return this;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return this;
    }

    @Override
    public Object getPrincipal() {
        return name;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        isAuthenticated = b;
    }

    @Override
    public void eraseCredentials() {

    }

    public Lang getLang() {
        return lang;
    }

    public User setLang(final Lang lang) {
        this.lang = lang;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public User setLocation(final Location location) {
        this.location = location;
        return this;
    }

    public Platform getPlatform() {
        return platform;
    }

    public User setPlatform(final Platform platform) {
        this.platform = platform;
        return this;
    }
}
