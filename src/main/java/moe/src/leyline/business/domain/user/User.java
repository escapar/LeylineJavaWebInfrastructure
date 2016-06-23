package moe.src.leyline.business.domain.user;

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;
import moe.src.leyline.business.infrastructure.common.AuthUtil;
import moe.src.leyline.framework.domain.user.LeylineUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.Collection;

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
public class User implements LeylineUser {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    private int role;

    @Transient
    private boolean isAuthenticated;

    public User() {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return null;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUnHashedPassword(String password) {
        setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    }


    public int getRole(){
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
}
