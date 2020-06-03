package softuni.workshop.data.entities;


import org.springframework.security.core.userdetails.UserDetails;
import softuni.workshop.data.entities.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String password;
    private String email;
    private Set<Role> authorities;

    private boolean active = true;

    public User() {
       this.authorities = new LinkedHashSet<>();
    }

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty
    public String getUsername() {
        return username;
    }

    @Column(name = "password", nullable = false)
    @NotEmpty
    public String getPassword() {
        return password;
    }

    @Column(name = "email", unique = true, nullable = false)
    @Email
    @NotEmpty
    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @ManyToMany(fetch = FetchType.EAGER,targetEntity = Role.class)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    public Set<Role> getAuthorities() {
        return this.authorities;
    }


    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return this.active;
    }


}
