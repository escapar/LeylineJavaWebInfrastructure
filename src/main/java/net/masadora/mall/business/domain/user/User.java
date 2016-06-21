package net.masadora.mall.business.domain.user;

import net.masadora.mall.business.infrastructure.common.AuthUtil;
import net.masadora.mall.framework.domain.user.LeylineUser;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "d_user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements LeylineUser{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="AVATAR_URI")
    private String avatarUri;

    @Column(name="BIRTH_DAY")
    private Long birthDay;

    @Column(name="DEFAULT_ADDRESS")
    private Long defaultAddress;

    private String email;

    @Column(name="EMAIL_VERIFIED")
    private String emailVerified;

    private Short gender;

    @Column(name="LAST_LOGIN")
    private Long lastLogin;

    private String name;

    private String password;

    @Column(name="PAYMENT_PASSWORD")
    private String paymentPassword;

    private String phone;

    @Column(name="REG_DATE")
    private Long regDate;

    @ManyToOne
    private Role role;

    @Column(name="SHOW_R18")
    private Boolean showR18;

    private Integer status;

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUri() {
        return this.avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public Long getBirthDay() {
        return this.birthDay;
    }

    public void setBirthDay(Long birthDay) {
        this.birthDay = birthDay;
    }

    public Long getDefaultAddress() {
        return this.defaultAddress;
    }

    public void setDefaultAddress(Long defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerified() {
        return this.emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Short getGender() {
        return this.gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public Long getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthUtil.getRole(role.getId());
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaymentPassword() {
        return this.paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Long regDate) {
        this.regDate = regDate;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getShowR18() {
        return this.showR18;
    }

    public void setShowR18(Boolean showR18) {
        this.showR18 = showR18;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void eraseCredentials() {

    }
}
